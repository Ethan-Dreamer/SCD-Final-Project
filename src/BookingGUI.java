import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class BookingGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    
    private BookingManager bookingManager;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    
    private JComboBox<Bus> busDropdown;
    private Bus activeBus;
    
    private JTextField nameField;
    private JComboBox<String> fromBox;
    private JComboBox<String> destinationBox;
    private JTextField seatField;
    private DefaultListModel<String> listModel;
    private JLabel currentBusLabel;

    public BookingGUI() {
        bookingManager = new BookingManager();
        setTitle("Bus Ticket Booking Management");
        setSize(550, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(createWelcomeScreen(), "Welcome");
        mainPanel.add(createBusSelectionScreen(), "BusSelection");
        mainPanel.add(createBookingScreen(), "Booking");

        add(mainPanel);
    }

    private JPanel createWelcomeScreen() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Welcome to the Bus Booking System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        
        JButton startButton = new JButton("Select a Bus");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        
        panel.add(titleLabel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        startButton.addActionListener(e -> cardLayout.show(mainPanel, "BusSelection"));
        return panel;
    }

    private JPanel createBusSelectionScreen() {
        JPanel panel = new JPanel(new BorderLayout());
        
        JPanel centerPanel = new JPanel(new FlowLayout());
        centerPanel.add(new JLabel("Choose your ride: "));
        
        Bus[] fleetArray = bookingManager.getFleet().toArray(new Bus[0]);
        busDropdown = new JComboBox<>(fleetArray);
        centerPanel.add(busDropdown);
        
        JButton confirmBusButton = new JButton("Confirm Bus");
        centerPanel.add(confirmBusButton);

        panel.add(centerPanel, BorderLayout.CENTER);
        
        JButton backButton = new JButton("Back");
        panel.add(backButton, BorderLayout.SOUTH);

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Welcome"));
        
        confirmBusButton.addActionListener(e -> {
            Bus selected = (Bus) busDropdown.getSelectedItem();
            if (selected.isFull()) {
                JOptionPane.showMessageDialog(this, "This bus is fully booked. Choose another.", "Bus Full", JOptionPane.WARNING_MESSAGE);
            } else {
                activeBus = selected;
                currentBusLabel.setText("Booking for: Bus " + activeBus.getBusId());
                cardLayout.show(mainPanel, "Booking");
            }
        });

        return panel;
    }
   
    private JPanel createBookingScreen() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel headerPanel = new JPanel();
        currentBusLabel = new JLabel("Booking for: ");
        currentBusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        headerPanel.add(currentBusLabel);
        panel.add(headerPanel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        
        inputPanel.add(new JLabel("Passenger Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);
        
        String[] locations = {"New York", "Boston", "Washington D.C.", "Chicago", "Miami"};
        inputPanel.add(new JLabel("From (Departure):"));
        fromBox = new JComboBox<>(locations);
        inputPanel.add(fromBox);
        
        inputPanel.add(new JLabel("To (Destination):"));
        destinationBox = new JComboBox<>(locations);
        destinationBox.setSelectedIndex(1);
        inputPanel.add(destinationBox);

        inputPanel.add(new JLabel("Seat Number (1-40):"));
        seatField = new JTextField();
        inputPanel.add(seatField);

        JButton bookButton = new JButton("Book Ticket");
        JButton saveButton = new JButton("Save Bookings");
        inputPanel.add(bookButton);
        inputPanel.add(saveButton);

        JPanel centerWrapper = new JPanel(new BorderLayout());
        centerWrapper.add(inputPanel, BorderLayout.NORTH);

        listModel = new DefaultListModel<>();
        JList<String> ticketList = new JList<>(listModel);
        centerWrapper.add(new JScrollPane(ticketList), BorderLayout.CENTER);
        
        panel.add(centerWrapper, BorderLayout.CENTER);

        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Change Bus");
        navPanel.add(backButton);
        panel.add(navPanel, BorderLayout.SOUTH);

        backButton.addActionListener(e -> {
            busDropdown.repaint(); 
            cardLayout.show(mainPanel, "BusSelection");
        });

        bookButton.addActionListener(e -> {
            try {
                bookingManager.bookTicket(
                    nameField.getText(), 
                    activeBus, 
                    (String) fromBox.getSelectedItem(), 
                    (String) destinationBox.getSelectedItem(), 
                    seatField.getText()
                );
                
                listModel.addElement(nameField.getText() + " | Bus " + activeBus.getBusId() + " | Seat " + seatField.getText());
                nameField.setText("");
                seatField.setText("");
                
                if (activeBus.isFull()) {
                    JOptionPane.showMessageDialog(this, "This was the last seat! Bus is now full.", "Update", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (InvalidBookingException | BusUnavailableException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Runtime error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        saveButton.addActionListener(e -> {
            try {
                bookingManager.saveBookingsToFile("bookings.txt");
                JOptionPane.showMessageDialog(this, "Bookings saved!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BookingGUI().setVisible(true));
    }
}