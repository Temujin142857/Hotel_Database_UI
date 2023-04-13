package frontend;
import javax.swing.*;
import java.awt.*;
import global.users.User;
import global.UI;


public class ConvertReservation extends JFrame {
    private JLabel reservationIDLabel;
    private JTextField reservationIDField;
    private JLabel creditCardNumberLabel;
    private JTextField creditCardNumberField;
    private JLabel CVVLabel;
    private JTextField CVVField;
    private JLabel expiryLabel;
    private JTextField expiryField;
    private User user;
    private UI ui;


    Color mainblue = new Color(28,49,94);
    Color nugreen = new Color(34, 124, 112);
    Color beige = new Color(229,225,194);
    Color lightgreen = new Color(136,164,123);

    public ConvertReservation(UI ui){
        this.ui=ui;
        user =User.makeUser("EMPLOYEE");
        setTitle("Convert Reservation to Booking Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(mainblue);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        mainPanel.setBackground(mainblue);

        
        JPanel cvvExpiryPanel = new JPanel(new GridLayout(5,2));
        cvvExpiryPanel.setBackground(mainblue);

        reservationIDLabel = new JLabel("Enter reservation ID:");
        reservationIDLabel.setForeground(lightgreen);
        reservationIDField = new JTextField();
        cvvExpiryPanel.add(reservationIDLabel);
        cvvExpiryPanel.add(reservationIDField);

        creditCardNumberLabel = new JLabel("Enter credit card number:");
        creditCardNumberLabel.setForeground(lightgreen);
        creditCardNumberField = new JTextField();
        cvvExpiryPanel.add(creditCardNumberLabel);
        cvvExpiryPanel.add(creditCardNumberField);

        CVVLabel = new JLabel("CVV:");
        CVVLabel.setForeground(lightgreen);
        CVVField = new JTextField();
        cvvExpiryPanel.add(CVVLabel);
        cvvExpiryPanel.add(CVVField);

        //i sincerely doubt it matters that much that we need to validate this,
        //so it's staying a text field
        expiryLabel = new JLabel("Expiry:");
        expiryLabel.setForeground(lightgreen);
        expiryField = new JTextField();
        cvvExpiryPanel.add(expiryLabel);
        cvvExpiryPanel.add(expiryField);

        JButton booktoReservButton = new JButton("Confirm booking");
        booktoReservButton.setBackground(nugreen);
        booktoReservButton.setForeground(Color.white);
        cvvExpiryPanel.add(booktoReservButton);


        mainPanel.add(cvvExpiryPanel);
        add(mainPanel);

        pack();
        setVisible(true);



    }
    
}
