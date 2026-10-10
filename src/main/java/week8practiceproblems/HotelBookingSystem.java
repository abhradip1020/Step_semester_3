// Implement: Hotel Booking System

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class HotelBookingSystem {

    public static void main(String[] args) {
        Hotel hotel = new Hotel();

        StandardRoom room101 = new StandardRoom("101", "Standard Room 101", 100.0);
        DeluxeRoom room201 = new DeluxeRoom("201", "Deluxe Room 201", 150.0);

        hotel.addRoom(room101);
        hotel.addRoom(room201);

        Customer customerA = new Customer("C-A", "Customer A");
        Customer customerB = new Customer("C-B", "Customer B");
        Customer customerC = new Customer("C-C", "Customer C");

        LocalDate jan1 = LocalDate.of(2026, 1, 1);
        LocalDate jan5 = LocalDate.of(2026, 1, 5);
        LocalDate jan3 = LocalDate.of(2026, 1, 3);
        LocalDate jan7 = LocalDate.of(2026, 1, 7);
        LocalDate feb10 = LocalDate.of(2026, 2, 10);
        LocalDate feb12 = LocalDate.of(2026, 2, 12);

        // Customer A checks availability for Standard Room 101 from Jan 1 to Jan 5
        hotel.checkAvailability(room101, jan1, jan5, "Jan 1 to Jan 5");

        // Customer A reserves Standard Room 101 from Jan 1 to Jan 5
        Reservation resA = hotel.bookRoom(customerA, room101, jan1, jan5, "Jan 1-5");

        // Customer B attempts to reserve Standard Room 101 from Jan 3 to Jan 7
        Reservation resB = hotel.bookRoom(customerB, room101, jan3, jan7, "Jan 3 to Jan 7");

        // Customer A cancels reservation for Standard Room 101 for Jan 1-5 (before deadline)
        if (resA != null) {
            hotel.cancelReservation(resA, LocalDate.of(2025, 12, 28));
        }

        // Customer C reserves Deluxe Room 201 from Feb 10 to Feb 12
        Reservation resC = hotel.bookRoom(customerC, room201, feb10, feb12, "Feb 10-12");
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

abstract class Room {
    private String roomNumber;
    private String name;
    private double baseRate;

    public Room(String roomNumber, String name, double baseRate) {
        this.roomNumber = roomNumber;
        this.name = name;
        this.baseRate = baseRate;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getName() {
        return name;
    }

    public double getBaseRate() {
        return baseRate;
    }

    // Abstract method to calculate price based on concrete room type and duration
    public abstract double calculatePrice(long nights);
}

class StandardRoom extends Room {
    public StandardRoom(String roomNumber, String name, double baseRate) {
        super(roomNumber, name, baseRate);
    }

    @Override
    public double calculatePrice(long nights) {
        return getBaseRate() * nights;
    }
}

class DeluxeRoom extends Room {
    public DeluxeRoom(String roomNumber, String name, double baseRate) {
        super(roomNumber, name, baseRate);
    }

    @Override
    public double calculatePrice(long nights) {
        return getBaseRate() * nights;
    }
}

class Suite extends Room {
    private double serviceCharge;

    public Suite(String roomNumber, String name, double baseRate, double serviceCharge) {
        super(roomNumber, name, baseRate);
        this.serviceCharge = serviceCharge;
    }

    @Override
    public double calculatePrice(long nights) {
        return (getBaseRate() * nights) + serviceCharge;
    }
}

class Reservation {
    private Customer customer;
    private Room room;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private String dateRangeLabel;
    private double totalPrice;
    private boolean active;
    private LocalDate cancellationDeadline;

    public Reservation(Customer customer, Room room, LocalDate checkIn, LocalDate checkOut, String dateRangeLabel) {
        this.customer = customer;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.dateRangeLabel = dateRangeLabel;
        long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
        this.totalPrice = room.calculatePrice(nights);
        this.active = true;
        // Deadline is 24 hours (1 day) prior to checkIn
        this.cancellationDeadline = checkIn.minusDays(1);
    }

    public Customer getCustomer() {
        return customer;
    }

    public Room getRoom() {
        return room;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public String getDateRangeLabel() {
        return dateRangeLabel;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public boolean isActive() {
        return active;
    }

    public boolean canCancel(LocalDate currentDate) {
        return active && !currentDate.isAfter(cancellationDeadline);
    }

    public boolean cancel(LocalDate currentDate) {
        if (canCancel(currentDate)) {
            this.active = false;
            return true;
        }
        return false;
    }

    public boolean overlaps(LocalDate start, LocalDate end) {
        if (!active) {
            return false;
        }
        return checkIn.isBefore(end) && start.isBefore(checkOut);
    }
}

class Hotel {
    private List<Room> rooms = new ArrayList<>();
    private List<Reservation> reservations = new ArrayList<>();

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public boolean isRoomAvailable(Room room, LocalDate start, LocalDate end) {
        for (Reservation res : reservations) {
            if (res.getRoom().getRoomNumber().equals(room.getRoomNumber()) && res.overlaps(start, end)) {
                return false;
            }
        }
        return true;
    }

    public void checkAvailability(Room room, LocalDate start, LocalDate end, String label) {
        if (isRoomAvailable(room, start, end)) {
            System.out.printf("%s is available from %s.%n", room.getName(), label);
        } else {
            System.out.printf("%s is not available from %s.%n", room.getName(), label);
        }
    }

    public Reservation bookRoom(Customer customer, Room room, LocalDate start, LocalDate end, String label) {
        if (!isRoomAvailable(room, start, end)) {
            System.out.printf("%s is not available from %s.%n", room.getName(), label);
            return null;
        }

        Reservation reservation = new Reservation(customer, room, start, end, label);
        reservations.add(reservation);
        System.out.printf("Reservation confirmed for %s, %s (%s). Price: $%.1f%n",
                customer.getName(), room.getName(), label, reservation.getTotalPrice());
        return reservation;
    }

    public void cancelReservation(Reservation reservation, LocalDate cancellationDate) {
        if (reservation != null && reservation.cancel(cancellationDate)) {
            System.out.printf("Reservation for %s, %s (%s) cancelled successfully.%n",
                    reservation.getCustomer().getName(),
                    reservation.getRoom().getName(),
                    reservation.getDateRangeLabel());
        } else {
            System.out.println("Cancellation failed or past deadline.");
        }
    }
}
