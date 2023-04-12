package frontend;

import javax.swing.*;
import org.jdatepicker.impl.*;
import java.awt.*;
import java.util.Properties;

public class UserPage extends JFrame {
    private JLabel checkInLabel;
    private JLabel checkOutLabel;
    private JLabel capacityLabel;
    private JLabel classLabel;
    private JLabel chainLabel;
    private JLabel minPriceLabel;
    private JLabel maxPriceLabel;
    private JLabel filterbyLabel;
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

        checkInLabel = new JLabel("Check in *");
        checkInLabel.setForeground(nugreen);
        inputPanel.add(checkInLabel);
        //The following utilizes a library added in for ease of date picking,
        //this is just the necessary procedure for it
        // here's a reference: https://www.codejava.net/java-se/swing/how-to-use-jdatepicker-to-display-calendar-component
        Properties p = new Properties();
        p.put("text.today","Today");
        p.put("text.month","Month");
        p.put("text.year","Year");
        UtilDateModel model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model,p);
        JDatePickerImpl checkindatePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        inputPanel.add(checkindatePicker);


        checkOutLabel = new JLabel("Check out *");
        checkOutLabel.setForeground(nugreen);   
        inputPanel.add(checkOutLabel);
        UtilDateModel model2 = new UtilDateModel();
        JDatePanelImpl datePanel2 = new JDatePanelImpl(model2, p);
        JDatePickerImpl checkoutdatePicker = new JDatePickerImpl(datePanel2, new DateLabelFormatter());
        inputPanel.add(checkoutdatePicker);

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

        JPanel reservePanel = new JPanel();
        JButton reserveBtn = new JButton("Make a reservation");
        reserveBtn.setBackground(nugreen);
        reserveBtn.setForeground(Color.WHITE);
        reserveBtn.setHorizontalAlignment(JButton.LEFT);
        reservePanel.setBackground(beige);
        reservePanel.add(reserveBtn);
        mainPanel.add(reservePanel);
        

        
        


        
        add(mainPanel);
        pack();

        //actionlistener method for the search!
        checkAvailabilityButton.addActionListener(e -> {
            //here's how you can get a date in SQL format from a datePicker:
            java.sql.Date checkinDatepick = (java.sql.Date) checkindatePicker.getModel().getValue();
            java.sql.Date checkoutDatepick = (java.sql.Date) checkoutdatePicker.getModel().getValue();
            

        });

        //and another listener for the reservations button
        reserveBtn.addActionListener(e ->{
            new ReserveRoom();
        });

        setVisible(true);
    }


    
    public static void main(String[] args) {
        new UserPage();
    }
}