package frontend;

import javax.swing.*;
import org.jdatepicker.impl.*;
import java.awt.*;
import java.util.Properties;
import global.UI;

public class EmployeePage extends JFrame{
    private JLabel checkInLabel;
    private JLabel checkOutLabel;
    private JLabel capacityLabel;
    private JLabel classLabel;
    private JLabel chainLabel;
    private JLabel minPriceLabel;
    private JLabel maxPriceLabel;
    private JTextField minPriceField;
    private JTextField maxPriceField;
    private JComboBox<String> chainComboBox;
    private JComboBox<String> adultsComboBox;
    private JComboBox<String> classComboBox;
    private JButton checkAvailabilityButton;
    private JButton bookRoomButton;
    private UI ui;

    Color mainblue = new Color(28,49,94);
    Color nugreen = new Color(34, 124, 112);
    Color beige = new Color(229,225,194);
    Color lightgreen = new Color(136,164,123);

    public EmployeePage(UI ui){
        this.ui=ui;
        setTitle("Renting.com | (Employee Window)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(beige);

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

        JButton reservConvertBtn = new JButton("Convert Reservation to Booking");
        reservConvertBtn.setBackground(nugreen);
        reservConvertBtn.setForeground(Color.WHITE);
        flex.add(reservConvertBtn);


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

        bookRoomButton = new JButton("Book room now");
        bookRoomButton.setForeground(Color.WHITE);
        bookRoomButton.setBackground(mainblue);
        inputPanel.add(bookRoomButton);



        add(mainPanel);
        pack();

        bookRoomButton.addActionListener(e ->{
            new BookRoom(ui);
        });

        reservConvertBtn.addActionListener(e ->{
            new ConvertReservation(ui);
        });



        setVisible(true);
    }
    /**public static void main(String[] args) {
        new EmployeePage(ui);
    }*/

    public void run(){
        new EmployeePage(ui);
    }
    
}
