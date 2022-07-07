package tk.artiquno.warehouse.management.services;

import tk.artiquno.warehouse.management.swagger.dto.DeliveryDTO;
import tk.artiquno.warehouse.management.swagger.dto.DeliveryScheduleResponse;

public interface DeliveriesService {
    DeliveryScheduleResponse scheduleDelivery(DeliveryDTO deliveryDTO);
}
