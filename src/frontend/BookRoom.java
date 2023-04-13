package frontend;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

import org.jdatepicker.impl.*;
import java.util.Properties;
import global.UI;

public class BookRoom extends JFrame{
    private String SIN;
    private JLabel roomIDlabel;
    private JLabel checkInLabel;
    private JLabel checkOutLabel;
    private JLabel errorMsg;
    private JLabel successMsg;
    private JLabel userSINLabel;
    private JLabel creditCardNumberLabel;
    private JTextField creditCardNumberField;
    private JLabel CVVLabel;
    private JTextField CVVField;
    private JLabel expiryLabel;
    private JTextField expiryField;
    private JTextField userSINField;
    private JTextField roomIDField;
    private JButton roomConfirmBtn;
    private UI ui;
    
    


    Color mainblue = new Color(28,49,94);
    Color nugreen = new Color(34, 124, 112);
    Color beige = new Color(229,225,194);
    Color lightgreen = new Color(136,164,123);

    public BookRoom(UI ui){
        this.ui=ui;
        setTitle("Renting.com | Create a Booking (Employee)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(mainblue);

        JPanel overallPanel = new JPanel();
        overallPanel.setLayout(new BoxLayout(overallPanel,BoxLayout.Y_AXIS));
        overallPanel.setBackground(mainblue);

        roomIDlabel = new JLabel("Enter the desired Room ID");
        roomIDlabel.setForeground(Color.WHITE);
        roomIDField = new JTextField();

        
        checkInLabel = new JLabel("Check in *");
        checkInLabel.setForeground(Color.WHITE);
        Properties p = new Properties();
        p.put("text.today","Today");
        p.put("text.month","Month");
        p.put("text.year","Year");
        UtilDateModel model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model,p);
        JDatePickerImpl checkindatePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        

        checkOutLabel = new JLabel("Check out *");
        checkOutLabel.setForeground(Color.WHITE);   
        UtilDateModel model2 = new UtilDateModel();
        JDatePanelImpl datePanel2 = new JDatePanelImpl(model2, p);
        JDatePickerImpl checkoutdatePicker = new JDatePickerImpl(datePanel2, new DateLabelFormatter());

        userSINLabel = new JLabel("Enter customer SIN");
        userSINLabel.setForeground(Color.WHITE);
        userSINField = new JTextField();

        creditCardNumberLabel = new JLabel("Enter credit card number:");
        creditCardNumberLabel.setForeground(lightgreen);
        creditCardNumberField = new JTextField();


        CVVLabel = new JLabel("CVV:");
        CVVLabel.setForeground(lightgreen);
        CVVField = new JTextField();


        //i sincerely doubt it matters that much that we need to validate this,
        //so it's staying a text field
        expiryLabel = new JLabel("Expiry:");
        expiryLabel.setForeground(lightgreen);
        expiryField = new JTextField();


        roomConfirmBtn = new JButton("Confirm!");
        roomConfirmBtn.setBackground(nugreen);
        roomConfirmBtn.setForeground(Color.WHITE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(0,2));
        mainPanel.setBackground(mainblue);

        mainPanel.add(checkInLabel);
        mainPanel.add(checkindatePicker);
        mainPanel.add(checkOutLabel);
        mainPanel.add(checkoutdatePicker); 
        mainPanel.add(roomIDlabel);
        mainPanel.add(roomIDField);
        mainPanel.add(userSINLabel);
        mainPanel.add(userSINField);
        mainPanel.add(creditCardNumberLabel);
        mainPanel.add(creditCardNumberField);
        mainPanel.add(CVVLabel);
        mainPanel.add(CVVField);
        mainPanel.add(expiryLabel);
        mainPanel.add(expiryField);


        mainPanel.add(roomConfirmBtn);
        overallPanel.add(mainPanel);

        errorMsg = new JLabel("Sorry, this booking cannot be made!");
        errorMsg.setForeground(Color.RED);
        mainPanel.add(errorMsg);
        errorMsg.setVisible(false);

        successMsg = new JLabel("Booking made successfully!");
        successMsg.setForeground(nugreen);
        mainPanel.add(successMsg);
        successMsg.setVisible(false);

        add(overallPanel);

        pack();
        setVisible(true);

        roomConfirmBtn.addActionListener(e ->{
            //TODO: essentially identical to ReserveRoom just for booking instead
            Date checkinDatepick = (Date) checkindatePicker.getModel().getValue();
            Date checkoutDatepick = (Date) checkoutdatePicker.getModel().getValue();
            if (makeBooking(SIN, checkinDatepick, checkoutDatepick)){
                //a check to attempt to make the reservation
                successMsg.setVisible(true);
            }else{
                errorMsg.setVisible(true);
            }
        });

    }

    public boolean makeBooking(String SIN, Date checkin, Date checkout){
        //TODO:
        //make said reservation and return a boolean if successful
        return true;
    }
}

