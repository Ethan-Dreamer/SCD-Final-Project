// BookingGUI.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class BookingGUI extends JFrame {
	private static final long serialVersionUID = 1L; 
    private BookingManager bookingManager;
    private JTextField nameField;
    private JComboBox<String> destinationBox;
    private JTextField seatField;
    private DefaultListModel<String> listModel;

    public BookingGUI() {
        bookingManager = new BookingManager();
        setTitle("Bus Ticket Booking Management");
        setSize(450, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        inputPanel.add(new JLabel("Passenger Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);
        
        inputPanel.add(new JLabel("Destination:"));
        String[] destinations = {"New York", "Boston", "Washington D.C.", "Chicago"};
        destinationBox = new JComboBox<>(destinations);
        inputPanel.add(destinationBox);

        inputPanel.add(new JLabel("Seat Number (1-40):"));
        seatField = new JTextField();
        inputPanel.add(seatField);

        JButton bookButton = new JButton("Book Ticket");
        JButton saveButton = new JButton("Save Bookings");
        inputPanel.add(bookButton);
        inputPanel.add(saveButton);

        add(inputPanel, BorderLayout.NORTH);

        listModel = new DefaultListModel<>();
        JList<String> ticketList = new JList<>(listModel);
        ticketList.setBorder(BorderFactory.createTitledBorder("Booked Tickets"));
        add(new JScrollPane(ticketList), BorderLayout.CENTER);

        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = nameField.getText();
                    String destination = (String) destinationBox.getSelectedItem();
                    String seat = seatField.getText();
                    
                    bookingManager.bookTicket(name, destination, seat);
                    listModel.addElement("Seat " + seat + ": " + name + " to " + destination);
                    
                    nameField.setText("");
                    seatField.setText("");
                } catch (InvalidBookingException ex) {
                    JOptionPane.showMessageDialog(BookingGUI.this, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(BookingGUI.this, "An unexpected error occurred: " + ex.getMessage(), "Runtime Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    bookingManager.saveBookingsToFile("bookings.txt");
                    JOptionPane.showMessageDialog(BookingGUI.this, "Bookings saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(BookingGUI.this, "Failed to save file: " + ex.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BookingGUI().setVisible(true));
    }
}