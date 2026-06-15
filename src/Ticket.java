public class Ticket {
    private String passengerName;
    private String busId;
    private String departure;
    private String destination;
    private int seatNumber;

    public Ticket(String passengerName, String busId, String departure, String destination, int seatNumber) {
        this.passengerName = passengerName;
        this.busId = busId;
        this.departure = departure;
        this.destination = destination;
        this.seatNumber = seatNumber;
    }

    public String getPassengerName() { return passengerName; }
    public String getBusId() { return busId; }
    public String getDeparture() { return departure; }
    public String getDestination() { return destination; }
    public int getSeatNumber() { return seatNumber; }

    @Override
    public String toString() {
        return "Bus " + busId + " | Seat " + seatNumber + ": " + passengerName + " | " + departure + " -> " + destination;
    }
}