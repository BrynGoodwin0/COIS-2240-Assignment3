import java.util.regex.Pattern;

public abstract class Vehicle {
    private String licensePlate;
    private String make;
    private String model;
    private int year;
    private VehicleStatus status;

    public enum VehicleStatus { Available, Held, Rented, UnderMaintenance, OutOfService }
    
    public Vehicle(String make, String model, int year) {
    	this.make = capitalize(make);
    	this.model = capitalize(model);
    	
        this.year = year;
        this.status = VehicleStatus.Available;
        this.licensePlate = null;
    }

    public Vehicle() {
        this(null, null, 0);
    }
    
    private String capitalize(String input) {
    	if (input == null || input.isEmpty()) {
    		return null;
    	} else {
    		return input.substring(0,1).toUpperCase() + input.substring(1).toLowerCase();
    	}
    }
    
    private Boolean isValidPlate(String plate) {
    	if (plate == null || plate.isEmpty()) {
    		return false;
    	}
    	//regex to match valid plates
    	return Pattern.matches("^[a-z|A-Z]{3}\\d{3}\\Z", plate);
    }

    public void setLicensePlate(String plate) {
    	if (isValidPlate(plate)) {
    		this.licensePlate = plate == null ? null : plate.toUpperCase();
    	} else {
    		throw new IllegalArgumentException("A license plate must consist of three numbers followed by three letters");
    	}
    }

    public void setStatus(VehicleStatus status) {
    	this.status = status;
    }

    public String getLicensePlate() { return licensePlate; }

    public String getMake() { return make; }

    public String getModel() { return model;}

    public int getYear() { return year; }

    public VehicleStatus getStatus() { return status; }

    public String getInfo() {
        return "| " + licensePlate + " | " + make + " | " + model + " | " + year + " | " + status + " |";
    }

}
