package tk.artiquno.warehouse.management.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.artiquno.warehouse.management.entities.Item;
import tk.artiquno.warehouse.management.entities.Order;
import tk.artiquno.warehouse.management.entities.OrderedItem;
import tk.artiquno.warehouse.management.exceptions.BadRequestException;
import tk.artiquno.warehouse.management.exceptions.ConflictException;
import tk.artiquno.warehouse.management.swagger.dto.DeliveryDTO;
import tk.artiquno.warehouse.management.swagger.dto.DeliveryScheduleResponse;
import tk.artiquno.warehouse.management.swagger.dto.OrderStatus;

import java.time.DayOfWeek;
import java.util.List;
import java.util.stream.Stream;

@Service
public class DeliveriesServiceImpl implements DeliveriesService {
    // TODO: Set this as a configurable value?
    private static final int TRUCK_CAPACITY = 10;

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private ItemsService itemsService;

    @Override
    public DeliveryScheduleResponse scheduleDelivery(DeliveryDTO deliveryDTO) {
        // No work on sundays!
        if(deliveryDTO.getDeliveryDate().getDayOfWeek().equals(DayOfWeek.SUNDAY))
        {
            throw new BadRequestException("Give the truck drivers a resting day for pity's sake!");
        }

        List<Order> ordersForDelivery = ordersService.findAllOrdersForDelivery();

        // No deliveries to make
        if(ordersForDelivery.isEmpty())
        {
            DeliveryScheduleResponse response = new DeliveryScheduleResponse();
            response.setResult("There are no orders to be delivered. Truck drivers can relax.");
            return response;
        }

        List<OrderedItem> orderedItems = ordersForDelivery.stream()
                .flatMap(order -> order.getItems().stream())
                .toList();

        // Check if we can deliver all the items
        final int itemCapacity = deliveryDTO.getTrucks().size() * TRUCK_CAPACITY;
        final int totalItems = orderedItems.stream()
                .reduce(0, (total, orderedItem) -> total + orderedItem.getRequestedQuantity(), Integer::sum);


        // Check if we have enough trucks
        if(totalItems > itemCapacity)
        {
            // Do we have to deliver everything in one day?
        }

        // Tell the trucks about the delivery maybe?

        // Mark all the affected orders as UNDER_DELIVERY
        ordersForDelivery.forEach(order -> ordersService.updateOrderStatus(order.getId(), OrderStatus.UNDER_DELIVERY));

        // Update item stock quantities in the db
        orderedItems.stream()
                .peek(this::updateItemsInStock)
                .flatMap(orderedItem -> Stream.of(orderedItem.getItem()))
                .forEach(itemsService::updateItem);

        // Act like the delivery was saved somewhere

        DeliveryScheduleResponse response = new DeliveryScheduleResponse();
        response.setResult("Delivery was scheduled! Truck drivers are complaining successfully!");
        return response;
    }

    /**
     * Update's the item's quantity in stock and checks
     * that it doesn't go negative. This does not update the
     * item in the database! Do that yourself!
     * @param orderedItem The item (wrapper) to be updated
     *
     * @throws ConflictException if there are not enough items in stock
     */
    private void updateItemsInStock(OrderedItem orderedItem) {
        Item item = orderedItem.getItem();
        final int oldQuantity = item.getQuantityInStock();
        item.setQuantityInStock(oldQuantity - orderedItem.getRequestedQuantity());
        if(item.getQuantityInStock() < 0)
        {
            // TODO: Throw a specific exception, OutOfStockException or something
            throw new ConflictException("We don't have enough of " + item.getName() + " in stock. " +
                    "Requested quantity/Current quantity: " + orderedItem.getRequestedQuantity() + "/" + oldQuantity);
        }
    }
}
