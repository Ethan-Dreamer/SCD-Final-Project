// BookingManagerTest.java
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
        assertDoesNotThrow(() -> manager.bookTicket("Alice", "Boston", "12"));
        assertEquals(1, manager.getBookedTickets().size());
        assertEquals(12, manager.getBookedTickets().get(0).getSeatNumber());
    }

    @Test
    public void testEmptyNameThrowsException() {
        Exception exception = assertThrows(InvalidBookingException.class, () -> {
            manager.bookTicket("", "New York", "5");
        });
        assertEquals("Passenger name cannot be empty.", exception.getMessage());
    }

    @Test
    public void testDuplicateSeatThrowsException() {
        assertDoesNotThrow(() -> manager.bookTicket("Alice", "Boston", "15"));
        
        Exception exception = assertThrows(InvalidBookingException.class, () -> {
            manager.bookTicket("Bob", "Chicago", "15");
        });
        assertEquals("Seat 15 is already booked.", exception.getMessage());
    }
    
    @Test
    public void testInvalidSeatFormatThrowsException() {
        Exception exception = assertThrows(InvalidBookingException.class, () -> {
            manager.bookTicket("Charlie", "Washington D.C.", "ABC");
        });
        assertEquals("Seat number must be a valid integer.", exception.getMessage());
    }
}