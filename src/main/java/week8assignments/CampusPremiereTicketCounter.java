import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class CampusPremiereTicketCounter {

    public static void main(String[] args) {
        Show show7PM = new Show("7 PM Show");

        Customer asha = new Customer("Asha");
        Customer ravi = new Customer("Ravi");
        Customer neha = new Customer("Neha");

        Seat a1 = new RegularSeat("A1");
        Seat a2 = new RegularSeat("A2");
        Seat f5 = new PremiumSeat("F5");
        Seat r1 = new ReclinerSeat("R1");

        Booking ashaBooking = show7PM.bookSeats(asha, Arrays.asList(a1, a2, f5));
        show7PM.bookSeats(ravi, Arrays.asList(a2));
        show7PM.bookSeats(ravi, Arrays.asList(r1));

        if (ashaBooking != null) {
            show7PM.cancelBooking(ashaBooking, true);
        }

        show7PM.bookSeats(neha, Arrays.asList(a2));
    }
}

class Customer {
    private String name;

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

abstract class Seat {
    private String seatNumber;

    public Seat(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public abstract double getPrice();
}

class RegularSeat extends Seat {
    public RegularSeat(String seatNumber) {
        super(seatNumber);
    }

    @Override
    public double getPrice() {
        return 150.00;
    }
}

class PremiumSeat extends Seat {
    public PremiumSeat(String seatNumber) {
        super(seatNumber);
    }

    @Override
    public double getPrice() {
        return 250.00;
    }
}

class ReclinerSeat extends Seat {
    public ReclinerSeat(String seatNumber) {
        super(seatNumber);
    }

    @Override
    public double getPrice() {
        return 400.00;
    }
}

class Booking {
    private Customer customer;
    private Show show;
    private List<Seat> seats;
    private double totalAmount;
    private boolean active;

    public Booking(Customer customer, Show show, List<Seat> seats) {
        this.customer = customer;
        this.show = show;
        this.seats = new ArrayList<>(seats);
        this.active = true;
        this.totalAmount = 0.0;
        for (Seat s : seats) {
            this.totalAmount += s.getPrice();
        }
    }

    public Customer getCustomer() {
        return customer;
    }

    public Show getShow() {
        return show;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public boolean isActive() {
        return active;
    }

    public void cancel() {
        this.active = false;
    }
}

class Show {
    private String title;
    private Set<String> bookedSeatNumbers;

    public Show(String title) {
        this.title = title;
        this.bookedSeatNumbers = new LinkedHashSet<>();
    }

    public String getTitle() {
        return title;
    }

    public Booking bookSeats(Customer customer, List<Seat> seats) {
        if (seats == null || seats.isEmpty() || seats.size() > 6) {
            return null;
        }

        for (Seat seat : seats) {
            if (bookedSeatNumbers.contains(seat.getSeatNumber())) {
                System.out.printf("Seat %s is already booked for this show.%n", seat.getSeatNumber());
                return null;
            }
        }

        for (Seat seat : seats) {
            bookedSeatNumbers.add(seat.getSeatNumber());
        }

        Booking booking = new Booking(customer, this, seats);

        StringBuilder seatNames = new StringBuilder();
        for (int i = 0; i < seats.size(); i++) {
            seatNames.append(seats.get(i).getSeatNumber());
            if (i < seats.size() - 1) {
                seatNames.append(", ");
            }
        }

        System.out.printf("Booking confirmed for %s: %s. Total: ₹%.2f.%n",
                customer.getName(), seatNames.toString(), booking.getTotalAmount());

        return booking;
    }

    public void cancelBooking(Booking booking, boolean beforeShowStarts) {
        if (booking == null || !booking.isActive()) {
            return;
        }

        if (beforeShowStarts) {
            booking.cancel();
            StringBuilder seatNames = new StringBuilder();
            List<Seat> seats = booking.getSeats();
            for (int i = 0; i < seats.size(); i++) {
                String seatNum = seats.get(i).getSeatNumber();
                bookedSeatNumbers.remove(seatNum);
                seatNames.append(seatNum);
                if (i < seats.size() - 1) {
                    seatNames.append(", ");
                }
            }
            System.out.printf("%s's booking cancelled. Seats %s released.%n",
                    booking.getCustomer().getName(), seatNames.toString());
        }
    }
}
