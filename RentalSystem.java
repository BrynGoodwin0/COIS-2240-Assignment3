import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;
import java.io.FileWriter;

public class RentalSystem {
	
	private static RentalSystem instance;
    private List<Vehicle> vehicles = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private RentalHistory rentalHistory = new RentalHistory();
    
    private RentalSystem() {};
    
    public static RentalSystem getInstance() {
    	if (instance == null) {
    		instance = new RentalSystem();
    	}
    	return instance;
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
        saveVehicle(vehicle);
    }
    
    public void saveVehicle(Vehicle vehicle) {
    	try {
    		FileWriter file = new FileWriter("vehicles.txt", true);
    		file.append(vehicle.getLicensePlate() + "," +
    					vehicle.getMake() + "," +
    					vehicle.getModel() + "," +
    					vehicle.getYear() + "," +
    					vehicle.getStatus() + ",");
    		if(vehicle instanceof Car) {
    			file.append(((Car) vehicle).getNumSeats() + "");
    			if (vehicle instanceof SportCar) {
    				file.append("," + ((SportCar) vehicle).getHorsepower() + "," + 
    								((SportCar) vehicle).getHasTurbo() + ",SportCar\n");
    			} else { file.append(",Car\n");}
    		} else if (vehicle instanceof Minibus) {
    			file.append(((Minibus) vehicle).getIsAccessible() + ",Minibus\n");
    		} else if (vehicle instanceof PickupTruck) {
    			file.append(((PickupTruck) vehicle).getCargoSize() +"," +
    						((PickupTruck) vehicle).hasTrailer() + ",PickupTruck\n");
    		}
    		file.close();
    	} catch (Exception e) {}
    	
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
        saveCustomer(customer);
    }
    
    public void saveCustomer(Customer customer) {
    	try {
    		FileWriter file = new FileWriter("customers.txt", true);
    		file.append(customer.getCustomerId() + "," + customer.getCustomerName() + "\n");
    		file.close();
    	} catch (Exception e) {}
    }

    public void rentVehicle(Vehicle vehicle, Customer customer, LocalDate date, double amount) {
        if (vehicle.getStatus() == Vehicle.VehicleStatus.Available) {
            vehicle.setStatus(Vehicle.VehicleStatus.Rented);
            RentalRecord record = new RentalRecord(vehicle, customer, date, amount, "RENT");
            rentalHistory.addRecord(record);
            saveRecord(record);
            System.out.println("Vehicle rented to " + customer.getCustomerName());
        }
        else {
            System.out.println("Vehicle is not available for renting.");
        }
    }

    public void returnVehicle(Vehicle vehicle, Customer customer, LocalDate date, double extraFees) {
        if (vehicle.getStatus() == Vehicle.VehicleStatus.Rented) {
            vehicle.setStatus(Vehicle.VehicleStatus.Available);
            RentalRecord record = new RentalRecord(vehicle, customer, date, extraFees, "RETURN");
            rentalHistory.addRecord(record);
            saveRecord(record);
            System.out.println("Vehicle returned by " + customer.getCustomerName());
        }
        else {
            System.out.println("Vehicle is not rented.");
        }
    }
    
    public void saveRecord(RentalRecord record) {
    	try {
    		FileWriter file = new FileWriter("rental_records.txt", true);
    		file.append(record.getVehicle().getLicensePlate() + "," +
    					record.getCustomer().getCustomerId() + "," + 
    					record.getRecordDate().toString() + "," +
    					record.getTotalAmount() + "," +
    					record.getRecordType() + "\n");
    		file.close();
    	} catch (Exception e) {}
    }

    public void displayVehicles(Vehicle.VehicleStatus status) {
        // Display appropriate title based on status
        if (status == null) {
            System.out.println("\n=== All Vehicles ===");
        } else {
            System.out.println("\n=== " + status + " Vehicles ===");
        }
        
        // Header with proper column widths
        System.out.printf("|%-16s | %-12s | %-12s | %-12s | %-6s | %-18s |%n", 
            " Type", "Plate", "Make", "Model", "Year", "Status");
        System.out.println("|--------------------------------------------------------------------------------------------|");
    	  
        boolean found = false;
        for (Vehicle vehicle : vehicles) {
            if (status == null || vehicle.getStatus() == status) {
                found = true;
                String vehicleType;
                if (vehicle instanceof Car) {
                    vehicleType = "Car";
                } else if (vehicle instanceof Minibus) {
                    vehicleType = "Minibus";
                } else if (vehicle instanceof PickupTruck) {
                    vehicleType = "Pickup Truck";
                } else {
                    vehicleType = "Unknown";
                }
                System.out.printf("| %-15s | %-12s | %-12s | %-12s | %-6d | %-18s |%n", 
                    vehicleType, vehicle.getLicensePlate(), vehicle.getMake(), vehicle.getModel(), vehicle.getYear(), vehicle.getStatus().toString());
            }
        }
        if (!found) {
            if (status == null) {
                System.out.println("  No Vehicles found.");
            } else {
                System.out.println("  No vehicles with Status: " + status);
            }
        }
        System.out.println();
    }

    public void displayAllCustomers() {
        for (Customer c : customers) {
            System.out.println("  " + c.toString());
        }
    }
    
    public void displayRentalHistory() {
        if (rentalHistory.getRentalHistory().isEmpty()) {
            System.out.println("  No rental history found.");
        } else {
            // Header with proper column widths
            System.out.printf("|%-10s | %-12s | %-20s | %-12s | %-12s |%n", 
                " Type", "Plate", "Customer", "Date", "Amount");
            System.out.println("|-------------------------------------------------------------------------------|");
            
            for (RentalRecord record : rentalHistory.getRentalHistory()) {                
                System.out.printf("| %-9s | %-12s | %-20s | %-12s | $%-11.2f |%n", 
                    record.getRecordType(), 
                    record.getVehicle().getLicensePlate(),
                    record.getCustomer().getCustomerName(),
                    record.getRecordDate().toString(),
                    record.getTotalAmount()
                );
            }
            System.out.println();
        }
    }
    
    public Vehicle findVehicleByPlate(String plate) {
        for (Vehicle v : vehicles) {
            if (v.getLicensePlate().equalsIgnoreCase(plate)) {
                return v;
            }
        }
        return null;
    }
    
    public Customer findCustomerById(int id) {
        for (Customer c : customers)
            if (c.getCustomerId() == id)
                return c;
        return null;
    }
}