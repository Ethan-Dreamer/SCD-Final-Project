public class Bus {
    private String busId;
    private int maxCapacity;
    private int bookedSeats;

    public Bus(String busId, int maxCapacity, int bookedSeats) {
        this.busId = busId;
        this.maxCapacity = maxCapacity;
        this.bookedSeats = bookedSeats;
    }

    public String getBusId() { 
        return busId; 
    }
    
    public boolean isFull() { 
        return bookedSeats >= maxCapacity; 
    }
    
    public void addBooking() { 
        bookedSeats++; 
    }

    @Override
    public String toString() {
        if (isFull()) {
            return "Bus " + busId + " - FULL";
        }
        return "Bus " + busId + " (" + bookedSeats + "/" + maxCapacity + " booked)";
    }
}