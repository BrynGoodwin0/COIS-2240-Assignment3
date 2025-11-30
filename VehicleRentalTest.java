import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class VehicleRentalTest {
	
	@Test
	void testLicensePlate() {
		Car v = new Car("Test","Testing",2000,5);
		assertThrows(Exception.class, () -> {v.setLicensePlate("AAA1000");});
		
		Car m = new Car("Test","Testing",2000,5);
		assertThrows(Exception.class, ()-> {m.setLicensePlate("");});
		
		Car n = new Car("Test","Testing",2000,5);
		assertThrows(Exception.class, ()-> {n.setLicensePlate(null);});
		
		Car q = new Car("Test","Testing",2000,5);
		assertThrows(Exception.class, ()-> {q.setLicensePlate("ZZZ99");});
		
		Car d = new Car("Test","Testing",2000,5);
		String test = "AAA100";
		d.setLicensePlate(test);
		assertEquals(test, d.getLicensePlate());
		
		Car r = new Car("Test","Testing",2000,5);
		test = "ABC567";
		r.setLicensePlate(test);
		assertEquals(test, r.getLicensePlate());
		
		Car w = new Car("Test","Testing",2000,5);
		test = "ZZZ999";
		w.setLicensePlate(test);
		assertEquals(test, w.getLicensePlate());
	}

	
	@Test
	void testRentAndReturnVehicle() {
		Car vehicle = new Car("test", "testing", 2000, 5);
		Customer customer = new Customer(1, "tester");
		
		assertEquals(vehicle.getStatus(), Vehicle.VehicleStatus.Available);
		
		RentalSystem system = RentalSystem.getInstance();
		
		Boolean rented = system.rentVehicle(vehicle, customer, null, 0);
		assertTrue(rented);
		assertEquals(vehicle.getStatus(), Vehicle.VehicleStatus.Rented);
		
		assertFalse(system.rentVehicle(vehicle, customer, null, 0));
		
		assertTrue(system.returnVehicle(vehicle, customer, null, 0));
		assertEquals(vehicle.getStatus(), Vehicle.VehicleStatus.Available);
		
		assertFalse(system.returnVehicle(vehicle, customer, null, 0));
	}
}
