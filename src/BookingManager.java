// BookingManager.java
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookingManager {
    private List<Ticket> bookedTickets;

    public BookingManager() {
        bookedTickets = new ArrayList<>();
    }

    public void bookTicket(String name, String destination, String seatInput) throws InvalidBookingException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidBookingException("Passenger name cannot be empty.");
        }
        if (destination == null || destination.trim().isEmpty()) {
            throw new InvalidBookingException("Destination must be selected.");
        }
        
        int seatNumber;
        try {
            seatNumber = Integer.parseInt(seatInput);
        } catch (NumberFormatException e) {
            throw new InvalidBookingException("Seat number must be a valid integer.");
        }

        if (seatNumber <= 0 || seatNumber > 40) {
            throw new InvalidBookingException("Seat number must be between 1 and 40.");
        }

        for (Ticket t : bookedTickets) {
            if (t.getSeatNumber() == seatNumber) {
                throw new InvalidBookingException("Seat " + seatNumber + " is already booked.");
            }
        }

        bookedTickets.add(new Ticket(name, destination, seatNumber));
    }

    public List<Ticket> getBookedTickets() {
        return bookedTickets;
    }

    public void saveBookingsToFile(String filename) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            for (Ticket ticket : bookedTickets) {
                writer.write(ticket.toString() + "\n");
            }
        }
    }
}