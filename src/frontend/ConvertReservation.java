package frontend;
import javax.swing.*;
import java.awt.*;


public class ConvertReservation extends JFrame {
    private JLabel reservationIDLabel;
    private JTextField reservationIDField;
    private JLabel creditCardNumberLabel;
    private JTextField creditCardNumberField;
    private JLabel CVVLabel;
    private JTextField CVVField;

    Color mainblue = new Color(28,49,94);
    Color nugreen = new Color(34, 124, 112);
    Color beige = new Color(229,225,194);
    Color lightgreen = new Color(136,164,123);

    public ConvertReservation(){
        setTitle("Convert Reservation to Booking Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(mainblue);

        JPanel mainPanel = new JPanel(new FlowLayout());

        JPanel cardInfoPanel = new JPanel();
        JPanel cvvExpiryPanel = new JPanel(new GridLayout(1,2));

        reservationIDLabel = new JLabel("Enter reservation ID:");
        reservationIDField = new JTextField();
        mainPanel.add(reservationIDLabel);
        mainPanel.add(reservationIDField);

        creditCardNumberLabel = new JLabel("Enter credit card number:");
        


    }
    
}
