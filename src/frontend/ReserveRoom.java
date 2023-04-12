package frontend;
import javax.swing.*;
import java.awt.*;

public class ReserveRoom extends JFrame{
    private JLabel roomIDlabel;
    private JTextField roomIDField;
    private JButton roomConfirmBtn;

    Color mainblue = new Color(28,49,94);
    Color nugreen = new Color(34, 124, 112);
    Color beige = new Color(229,225,194);
    Color lightgreen = new Color(136,164,123);

    public ReserveRoom(){
        setTitle("Make a Reservation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(mainblue);

        JPanel overallPanel = new JPanel(new FlowLayout());
        overallPanel.setBackground(lightgreen);

        roomIDlabel = new JLabel("Enter the desired Room ID");
        roomIDlabel.setForeground(Color.WHITE);
        roomIDField = new JTextField();

        /* TODO: add this in
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
        inputPanel.add(checkoutdatePicker); */

        roomConfirmBtn = new JButton("Confirm!");
        roomConfirmBtn.setBackground(nugreen);
        roomConfirmBtn.setForeground(Color.WHITE);

        JPanel mainPanel = new JPanel(new GridLayout(3,2));
        mainPanel.setBackground(lightgreen);
        mainPanel.add(roomIDlabel);
        mainPanel.add(roomIDField);
        mainPanel.add(roomConfirmBtn);
        overallPanel.add(mainPanel);
        add(overallPanel);

        pack();
        setVisible(true);

        roomConfirmBtn.addActionListener(e ->{
            //TODO: method that takes in the value from the field and the current user's SIN 
            //makes a reservation
        });

    }
}
