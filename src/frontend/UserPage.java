package frontend;

import javax.swing.*;
import java.awt.*;

public class UserPage extends JFrame {
    private JLabel checkInLabel;
    private JLabel checkOutLabel;
    private JLabel capacityLabel;
    private JLabel classLabel;
    private JLabel chainLabel;
    private JTextField checkInField;
    private JTextField checkOutField;
    private JComboBox<String> chainComboBox;
    private JComboBox<String> adultsComboBox;
    private JComboBox<String> classComboBox;
    private JButton checkAvailabilityButton;

    Color mainblue = new Color(28,49,94);
    Color nugreen = new Color(34, 124, 112);
    Color beige = new Color(229,225,194);
    Color lightgreen = new Color(136,164,123);
    
    public UserPage() {
        setTitle("Renting.com | Not just a website~! Actually not a website at all, even!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        
        // create the header section
        JPanel header = new JPanel();
        header.setLayout(new BorderLayout());
        
        JPanel flex = new JPanel();
        flex.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        JLabel logo = new JLabel("Hotels");
        logo.setFont(new Font("Arial",Font.PLAIN,25));
        logo.setForeground(mainblue);
        
        flex.add(logo);
        
        header.add(flex, BorderLayout.WEST);
        
        
        JPanel navbar = new JPanel();
        navbar.setLayout(new FlowLayout(FlowLayout.LEFT));
        JButton reservationBtn = new JButton("Your reservations");

        
        navbar.add(reservationBtn);

        
        header.add(navbar, BorderLayout.CENTER);
        
        add(header, BorderLayout.NORTH);


        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Availability Section
        JPanel availabilityPanel = new JPanel();
        availabilityPanel.setLayout(new GridLayout(1, 2));
        mainPanel.add(availabilityPanel);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 1, 5, 5));
        availabilityPanel.add(inputPanel);

        checkInLabel = new JLabel("Check in *");
        checkInField = new JTextField();
        inputPanel.add(checkInLabel);
        inputPanel.add(checkInField);

        checkOutLabel = new JLabel("Check out *");
        checkOutField = new JTextField();
        inputPanel.add(checkOutLabel);
        inputPanel.add(checkOutField);

        capacityLabel = new JLabel("Capacity *");
        String[] capacityOptions = {"single", "double"};
        adultsComboBox = new JComboBox<>(capacityOptions);
        inputPanel.add(capacityLabel);
        inputPanel.add(adultsComboBox);

        chainLabel = new JLabel("Chain *");
        String[] chains = {"1", "2", "3", "4", "5"};
        chainComboBox = new JComboBox<>(chains);
        inputPanel.add(chainLabel);
        inputPanel.add(chainComboBox);

        classLabel = new JLabel("Class *");
        String[] classes = {"1", "2", "3", "4", "5"};
        classComboBox = new JComboBox<>(classes);
        inputPanel.add(classLabel);
        inputPanel.add(classComboBox);

        JPanel buttonPanel = new JPanel();
        checkAvailabilityButton = new JButton("Check Availability");
        buttonPanel.add(checkAvailabilityButton);
        availabilityPanel.add(buttonPanel);

        add(mainPanel);
        pack();
        setVisible(true);
    }
    
    public static void main(String[] args) {
        new UserPage();
    }
}