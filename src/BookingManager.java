import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookingManager {
    private List<Ticket> bookedTickets;
    private List<Bus> fleet;

    public BookingManager() {
        bookedTickets = new ArrayList<>();
        fleet = new ArrayList<>();
        
        fleet.add(new Bus("101", 40, 5));
        fleet.add(new Bus("102", 40, 40));
        fleet.add(new Bus("103", 40, 12));
        fleet.add(new Bus("104", 40, 40));
        fleet.add(new Bus("105", 40, 39));
        fleet.add(new Bus("106", 40, 0));
        fleet.add(new Bus("107", 40, 25));
        fleet.add(new Bus("108", 40, 40));
        fleet.add(new Bus("109", 40, 2));
        fleet.add(new Bus("110", 40, 19));
    }

    public List<Bus> getFleet() {
        return fleet;
    }

    public void bookTicket(String name, Bus selectedBus, String departure, String destination, String seatInput) throws InvalidBookingException, BusUnavailableException {
        if (selectedBus == null) {
            throw new InvalidBookingException("A bus must be selected.");
        }
        if (selectedBus.isFull()) {
            throw new BusUnavailableException("Bus " + selectedBus.getBusId() + " is completely full.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidBookingException("Passenger name cannot be empty.");
        }
        if (departure == null || departure.trim().isEmpty() || destination == null || destination.trim().isEmpty()) {
            throw new InvalidBookingException("Locations must be selected.");
        }
        if (departure.equals(destination)) {
            throw new InvalidBookingException("Departure and Destination cannot be the same.");
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
            if (t.getBusId().equals(selectedBus.getBusId()) && t.getSeatNumber() == seatNumber) {
                throw new InvalidBookingException("Seat " + seatNumber + " on Bus " + selectedBus.getBusId() + " is already booked.");
            }
        }

        selectedBus.addBooking();
        bookedTickets.add(new Ticket(name, selectedBus.getBusId(), departure, destination, seatNumber));
    }

    public List<Ticket> getBookedTickets() {
        return bookedTickets;
    }

    public void saveBookingsToFile(String filename) throws IOException, InvalidBookingException {
        if (bookedTickets.isEmpty()) {
            throw new InvalidBookingException("No tickets have been booked yet.");
        }
        
        try (FileWriter writer = new FileWriter(filename)) {
            for (Ticket ticket : bookedTickets) {
                writer.write(ticket.toString() + "\n");
            }
        }
    }
}