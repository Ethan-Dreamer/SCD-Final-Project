import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BookingManagerTest {
    private BookingManager manager;

    @BeforeEach
    public void setUp() {
        manager = new BookingManager();
    }

    @Test
    public void testBookTicketSuccessfully() {
        Bus availableBus = manager.getFleet().get(0);
        assertDoesNotThrow(() -> manager.bookTicket("Alice", availableBus, "New York", "Boston", "12"));
        assertEquals(1, manager.getBookedTickets().size());
        assertEquals("101", manager.getBookedTickets().get(0).getBusId());
    }

    @Test
    public void testBusUnavailableExceptionThrown() {
        Bus fullBus = manager.getFleet().get(1);
        Exception exception = assertThrows(BusUnavailableException.class, () -> {
            manager.bookTicket("Bob", fullBus, "New York", "Boston", "1");
        });
        assertEquals("Bus 102 is completely full.", exception.getMessage());
    }

    @Test
    public void testSameDepartureAndDestinationThrowsException() {
        Bus availableBus = manager.getFleet().get(0);
        Exception exception = assertThrows(InvalidBookingException.class, () -> {
            manager.bookTicket("David", availableBus, "Boston", "Boston", "8");
        });
        assertEquals("Departure and Destination cannot be the same.", exception.getMessage());
    }

    @Test
    public void testSaveEmptyBookingsThrowsException() {
        Exception exception = assertThrows(InvalidBookingException.class, () -> {
            manager.saveBookingsToFile("test.txt");
        });
        assertEquals("No tickets have been booked yet.", exception.getMessage());
    }
}