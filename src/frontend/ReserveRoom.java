package frontend;
import javax.swing.*;
import java.awt.*;
import java.util.Date;

import backend.UnauthorisedAccessException;
import global.users.User;
import global.*;

import org.jdatepicker.impl.*;
import java.util.Properties;

public class ReserveRoom extends JFrame{
    private String SIN;
    private JLabel roomIDlabel;
    private JLabel checkInLabel;
    private JLabel checkOutLabel;
    private JLabel errorMsg;
    private JLabel successMsg;
    private JTextField roomIDField;
    private JButton roomConfirmBtn;
    private User user;
    private UI ui;

    
    


    Color mainblue = new Color(28,49,94);
    Color nugreen = new Color(34, 124, 112);
    Color beige = new Color(229,225,194);
    Color lightgreen = new Color(136,164,123);

    public ReserveRoom(UI ui){
        this.ui=ui;
        user= User.makeUser("EMPLOYEE");
        setTitle("Make a Reservation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(mainblue);

        JPanel overallPanel = new JPanel(new FlowLayout());
        overallPanel.setBackground(lightgreen);

        roomIDlabel = new JLabel("Enter the desired Room ID");
        roomIDlabel.setForeground(Color.WHITE);
        roomIDField = new JTextField();

        
        checkInLabel = new JLabel("Check in *");
        checkInLabel.setForeground(nugreen);
        Properties p = new Properties();
        p.put("text.today","Today");
        p.put("text.month","Month");
        p.put("text.year","Year");
        UtilDateModel model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model,p);
        JDatePickerImpl checkindatePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        

        checkOutLabel = new JLabel("Check out *");
        checkOutLabel.setForeground(nugreen);   
        UtilDateModel model2 = new UtilDateModel();
        JDatePanelImpl datePanel2 = new JDatePanelImpl(model2, p);
        JDatePickerImpl checkoutdatePicker = new JDatePickerImpl(datePanel2, new DateLabelFormatter());

        roomConfirmBtn = new JButton("Confirm!");
        roomConfirmBtn.setBackground(nugreen);
        roomConfirmBtn.setForeground(Color.WHITE);

        JPanel mainPanel = new JPanel(new GridLayout(6,2));
        mainPanel.setBackground(lightgreen);

        mainPanel.add(checkInLabel);
        mainPanel.add(checkindatePicker);
        mainPanel.add(checkOutLabel);
        mainPanel.add(checkoutdatePicker); 
        mainPanel.add(roomIDlabel);
        mainPanel.add(roomIDField);
        mainPanel.add(roomConfirmBtn);
        overallPanel.add(mainPanel);

        errorMsg = new JLabel("Sorry, this reservation cannot be made!");
        errorMsg.setForeground(Color.RED);
        mainPanel.add(errorMsg);
        errorMsg.setVisible(false);

        successMsg = new JLabel("Reservation made successfully!");
        successMsg.setForeground(nugreen);
        mainPanel.add(successMsg);
        successMsg.setVisible(false);

        add(overallPanel);

        pack();
        setVisible(true);

        roomConfirmBtn.addActionListener(e ->{
            //TODO: method that takes in the value from the field and the current user's SIN 
            //makes a reservation
            //find a way to save current SIN in order to use it here? maybe an alternate constructor that
            //initializes a variable for the SIN
            Date checkinDatepick = (Date) checkindatePicker.getModel().getValue();
            Date checkoutDatepick = (Date) checkoutdatePicker.getModel().getValue();
            String roomID="";

            if (makeReservation(SIN, roomID, checkinDatepick, checkoutDatepick)){
                //a check to attempt to make the reservation
                successMsg.setVisible(true);
            }else{
                errorMsg.setVisible(true);
            }
        });

    }

    public boolean makeReservation(String SIN,String roomID, Date checkin, Date checkout){
        UI ui=new UI();
        try {
            ui.createReservation(user,SIN, roomID,checkin.toString(), checkout.toString());
        } catch (UnauthorisedAccessException e) {
            System.out.println("unauthorised acces, line 120, ReserveRoom");
            e.printStackTrace();
        }
        return true;
    }
}
