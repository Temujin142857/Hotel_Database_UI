package frontend;

import javax.swing.*;
import java.awt.*;
import java.util.InputMismatchException;

public class UserPage extends JFrame {
    private JLabel checkInLabel;
    private JLabel checkOutLabel;
    private JLabel capacityLabel;
    private JLabel classLabel;
    private JLabel chainLabel;
    private JLabel minPriceLabel;
    private JLabel maxPriceLabel;
    private JLabel filterbyLabel;
    private JTextField checkInField;
    private JTextField checkOutField;
    private JTextField minPriceField;
    private JTextField maxPriceField;
    private JComboBox<String> chainComboBox;
    private JComboBox<String> adultsComboBox;
    private JComboBox<String> classComboBox;
    private JComboBox<String> filterComboBox;
    private JButton checkAvailabilityButton;

    Color mainblue = new Color(28,49,94);
    Color nugreen = new Color(34, 124, 112);
    Color beige = new Color(229,225,194);
    Color lightgreen = new Color(136,164,123);
    
    public UserPage() {
        setTitle("Renting.com | Not just a website~! Actually not a website at all, even!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // create the header section
        JPanel header = new JPanel();
        header.setLayout(new BorderLayout());
        header.setBackground(beige);
        
        JPanel flex = new JPanel();
        flex.setLayout(new FlowLayout(FlowLayout.LEFT));
        flex.setBackground(beige);
        
        JLabel logo = new JLabel("Welcome!");
        logo.setFont(new Font("Arial",Font.PLAIN,25));
        logo.setForeground(mainblue);
        flex.add(logo);
        
        header.add(flex, BorderLayout.WEST);
        
        JPanel navbar = new JPanel();
        navbar.setLayout(new FlowLayout(FlowLayout.LEFT));
        JButton reservationBtn = new JButton("View your reservations");
        reservationBtn.setBackground(nugreen);
        reservationBtn.setForeground(Color.WHITE);
        navbar.setBackground(beige);
        navbar.add(reservationBtn);
        header.add(navbar, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(beige);

        // Availability Section
        JPanel availabilityPanel = new JPanel();
        availabilityPanel.setLayout(new GridLayout(1, 2));
        mainPanel.add(availabilityPanel);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(8, 1, 5, 5));
        inputPanel.setBackground(beige);
        availabilityPanel.add(inputPanel);

        //TODO: have it pick a date
        checkInLabel = new JLabel("Check in *");
        checkInLabel.setForeground(nugreen);
        checkInField = new JTextField();
        inputPanel.add(checkInLabel);
        inputPanel.add(checkInField);

        checkOutLabel = new JLabel("Check out *");
        checkOutLabel.setForeground(nugreen);
        checkOutField = new JTextField();
        checkOutField.setForeground(nugreen);
        inputPanel.add(checkOutLabel);
        inputPanel.add(checkOutField);

        capacityLabel = new JLabel("Capacity *");
        capacityLabel.setForeground(nugreen);
        String[] capacityOptions = {"single", "double"};
        adultsComboBox = new JComboBox<>(capacityOptions);
        inputPanel.add(capacityLabel);
        inputPanel.add(adultsComboBox);

        chainLabel = new JLabel("Chain *");
        chainLabel.setForeground(nugreen);
        String[] chains = {"","Marriott", "Hilton", "InterContinental", "Choice", "Wyndham"};
        chainComboBox = new JComboBox<>(chains);
        inputPanel.add(chainLabel);
        inputPanel.add(chainComboBox);

        classLabel = new JLabel("Class *");
        classLabel.setForeground(nugreen);
        String[] classes = {"1", "2", "3", "4", "5"};
        classComboBox = new JComboBox<>(classes);
        classComboBox.setForeground(mainblue);
        inputPanel.add(classLabel);
        inputPanel.add(classComboBox);
        
        minPriceLabel = new JLabel("Min. Price *");
        minPriceLabel.setForeground(nugreen);
        minPriceField = new JTextField();
        minPriceField.setForeground(mainblue);
        inputPanel.add(minPriceLabel);
        inputPanel.add(minPriceField);

        maxPriceLabel = new JLabel("Max. Price *");
        maxPriceLabel.setForeground(nugreen);
        maxPriceField = new JTextField();
        maxPriceField.setForeground(mainblue);
        inputPanel.add(maxPriceLabel);
        inputPanel.add(maxPriceField);

        checkAvailabilityButton = new JButton("Check Availability");
        checkAvailabilityButton.setForeground(lightgreen);
        checkAvailabilityButton.setBackground(mainblue);
        inputPanel.add(checkAvailabilityButton);

        JPanel filterPanel = new JPanel();
        filterbyLabel = new JLabel("Filter by:");
        filterbyLabel.setForeground(nugreen);
        filterbyLabel.setHorizontalAlignment(JLabel.RIGHT);
        String[] filters = {"","Price-Low to High", "Price-High to Low"};
        filterComboBox = new JComboBox<>(filters);
        filterComboBox.setForeground(nugreen);
        filterPanel.add(filterbyLabel);
        filterPanel.add(filterComboBox);
        filterPanel.setBackground(beige);
        availabilityPanel.add(filterPanel);
        
        add(mainPanel);
        pack();
        setVisible(true);
    }
    
    public static void main(String[] args) {
        new UserPage();
    }
}