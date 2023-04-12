package frontend;

import javax.swing.*;
import org.jdatepicker.impl.*;
import global.users.User;
import java.awt.*;
import java.util.Properties;

public class EmployeePage extends JFrame{

    Color mainblue = new Color(28,49,94);
    Color nugreen = new Color(34, 124, 112);
    Color beige = new Color(229,225,194);
    Color lightgreen = new Color(136,164,123);

    public EmployeePage(){
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


        pack();
        setVisible(true);

    }
    public static void main(String[] args) {
        new EmployeePage();
    }
    
}
