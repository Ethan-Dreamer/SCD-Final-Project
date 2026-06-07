// InvalidBookingException.java
public class InvalidBookingException extends Exception {
	private static final long serialVersionUID = 1L; 
    public InvalidBookingException(String message) {
        super(message);
    }
}