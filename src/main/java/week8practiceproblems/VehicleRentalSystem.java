// Implement: Vehicle Rental System

import java.util.ArrayList;
import java.util.List;

public class VehicleRentalSystem {

    public static void main(String[] args) {
        RentalAgency agency = new RentalAgency();

        Sedan sedanA = new Sedan("Sedan A", "Toyota Camry", 50.0);
        SUV suvB = new SUV("SUV B", "Honda CR-V", 80.0);

        agency.addVehicle(sedanA);
        agency.addVehicle(suvB);

        Customer customer1 = new Customer("C1", "Customer 1");
        Customer customer2 = new Customer("C2", "Customer 2");
        Customer customer3 = new Customer("C3", "Customer 3");

        // Customer 1 rents Sedan A for 3 days
        Rental rental1 = agency.rentVehicle(customer1, sedanA, 3);

        // Customer 2 attempts to rent Sedan A for 2 days (while it's rented by Customer 1)
        Rental rental2 = agency.rentVehicle(customer2, sedanA, 2);

        // Customer 1 returns Sedan A
        if (rental1 != null) {
            agency.returnVehicle(rental1);
        }

        // Customer 3 rents SUV B for 5 days
        Rental rental3 = agency.rentVehicle(customer3, suvB, 5);
    }
}

abstract class Vehicle {
    private String id;
    private String model;
    private double dailyRate;
    private boolean isAvailable;

    public Vehicle(String id, String model, double dailyRate) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Vehicle ID cannot be blank");
        }
        this.id = id;
        this.model = model;
        this.dailyRate = dailyRate;
        this.isAvailable = true;
    }

    public String getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public double getDailyRate() {
        return dailyRate;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    // Abstract method allowing each vehicle category to apply distinct charge calculation rules
    public abstract double calculateRentalCharge(int days);
}

class Sedan extends Vehicle {
    public Sedan(String id, String model, double dailyRate) {
        super(id, model, dailyRate);
    }

    @Override
    public double calculateRentalCharge(int days) {
        if (days <= 0) {
            throw new IllegalArgumentException("Rental days must be positive");
        }
        return getDailyRate() * days;
    }
}

class SUV extends Vehicle {
    public SUV(String id, String model, double dailyRate) {
        super(id, model, dailyRate);
    }

    @Override
    public double calculateRentalCharge(int days) {
        if (days <= 0) {
            throw new IllegalArgumentException("Rental days must be positive");
        }
        // SUVs may include standard rate calculation
        return getDailyRate() * days;
    }
}

class Truck extends Vehicle {
    private double cargoFee;

    public Truck(String id, String model, double dailyRate, double cargoFee) {
        super(id, model, dailyRate);
        this.cargoFee = cargoFee;
    }

    @Override
    public double calculateRentalCharge(int days) {
        if (days <= 0) {
            throw new IllegalArgumentException("Rental days must be positive");
        }
        return (getDailyRate() * days) + cargoFee;
    }
}

class Customer {
    private String id;
    private String name;

    public Customer(String id, String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be blank");
        }
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

class Rental {
    private Customer customer;
    private Vehicle vehicle;
    private int durationDays;
    private double totalCharge;
    private boolean active;

    public Rental(Customer customer, Vehicle vehicle, int durationDays) {
        this.customer = customer;
        this.vehicle = vehicle;
        this.durationDays = durationDays;
        this.totalCharge = vehicle.calculateRentalCharge(durationDays);
        this.active = true;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public int getDurationDays() {
        return durationDays;
    }

    public double getTotalCharge() {
        return totalCharge;
    }

    public boolean isActive() {
        return active;
    }

    public void completeRental() {
        this.active = false;
        this.vehicle.setAvailable(true);
    }
}

class RentalAgency {
    private List<Vehicle> vehicles = new ArrayList<>();
    private List<Rental> rentals = new ArrayList<>();

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    public Rental rentVehicle(Customer customer, Vehicle vehicle, int days) {
        if (!vehicle.isAvailable()) {
            System.out.println(vehicle.getId() + " is currently unavailable.");
            return null;
        }

        vehicle.setAvailable(false);
        Rental rental = new Rental(customer, vehicle, days);
        rentals.add(rental);
        System.out.printf("%s rented successfully by %s. Rental charge: $%.1f%n",
                vehicle.getId(), customer.getName(), rental.getTotalCharge());
        return rental;
    }

    public void returnVehicle(Rental rental) {
        if (rental != null && rental.isActive()) {
            rental.completeRental();
            System.out.printf("%s returned by %s.%n",
                    rental.getVehicle().getId(), rental.getCustomer().getName());
        }
    }
}
