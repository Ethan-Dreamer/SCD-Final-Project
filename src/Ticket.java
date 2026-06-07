// Ticket.java
public class Ticket {
    private String passengerName;
    private String destination;
    private int seatNumber;

    public Ticket(String passengerName, String destination, int seatNumber) {
        this.passengerName = passengerName;
        this.destination = destination;
        this.seatNumber = seatNumber;
    }

    public String getPassengerName() { return passengerName; }
    public String getDestination() { return destination; }
    public int getSeatNumber() { return seatNumber; }

    @Override
    public String toString() {
        return "Seat " + seatNumber + ": " + passengerName + " to " + destination;
    }
}
