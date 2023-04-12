package frontend;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

import org.jdatepicker.impl.*;
import java.util.Properties;

public class BookRoom extends JFrame{
    private String SIN;
    private JLabel roomIDlabel;
    private JLabel checkInLabel;
    private JLabel checkOutLabel;
    private JLabel errorMsg;
    private JLabel successMsg;
    private JLabel userSINLabel;
    private JTextField userSINField;
    private JTextField roomIDField;
    private JButton roomConfirmBtn;
    
    


    Color mainblue = new Color(28,49,94);
    Color nugreen = new Color(34, 124, 112);
    Color beige = new Color(229,225,194);
    Color lightgreen = new Color(136,164,123);

    public BookRoom(){
        setTitle("Renting.com | Create a Booking (Employee)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(mainblue);

        JPanel overallPanel = new JPanel(new FlowLayout());
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


        roomConfirmBtn = new JButton("Confirm!");
        roomConfirmBtn.setBackground(nugreen);
        roomConfirmBtn.setForeground(Color.WHITE);

        JPanel mainPanel = new JPanel(new GridLayout(6,2));
        mainPanel.setBackground(mainblue);

        mainPanel.add(checkInLabel);
        mainPanel.add(checkindatePicker);
        mainPanel.add(checkOutLabel);
        mainPanel.add(checkoutdatePicker); 
        mainPanel.add(roomIDlabel);
        mainPanel.add(roomIDField);
        mainPanel.add(userSINLabel);
        mainPanel.add(userSINField);
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

