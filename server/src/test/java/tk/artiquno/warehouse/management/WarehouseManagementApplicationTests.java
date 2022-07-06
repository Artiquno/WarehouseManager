package tk.artiquno.warehouse.management;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.internal.util.collections.Iterables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tk.artiquno.warehouse.management.entities.Truck;
import tk.artiquno.warehouse.management.repositories.TrucksRepo;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class WarehouseManagementApplicationTests {

	@Autowired
	private TrucksRepo trucksRepo;

	private Truck testingTruck;

	@BeforeAll
	public void addDataToDb() {
		testingTruck = new Truck();
		testingTruck.setLicensePlate("THANSR");
		testingTruck.setChassis("5GZCZ43D13S812715");
		trucksRepo.save(testingTruck);
	}

	@Test
	void contextLoads() {
		// TODO: Use H2 for tests
		Iterable<Truck> result = trucksRepo.findAll();
		Assertions.assertEquals(Iterables.firstOf(result), testingTruck);
	}
}
