package frontend;
import javax.swing.*;
import java.awt.*;
import global.*;

public class RentingComLogin extends JFrame {
    private JLabel label;
    private JLabel sinLabel;
    private JTextField sinField;
    private JButton loginButton;
    private JLabel invalidLabel;
    private UI ui;

    Color mainblue = new Color(28,49,94);
    Color nugreen = new Color(34, 124, 112);
    Color beige = new Color(229,225,194);
    Color lightgreen = new Color(136,164,123);

    public RentingComLogin() {        // Set up the window
        ui=new UI();
        setTitle("Renting.com|Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(mainblue);

        // Create components
        label = new JLabel("Renting.com");
        label.setFont(new Font("Arial", Font.ITALIC, 32));
        label.setForeground(Color.WHITE);
        sinLabel = new JLabel("ENTER SIN");
        sinLabel.setFont(new Font("Arial", Font.BOLD, 25));
        sinLabel.setForeground(lightgreen);
        sinLabel.setHorizontalAlignment(JLabel.CENTER);
        sinField = new JTextField(10);
        sinField.setBackground(beige);
        sinField.setForeground(nugreen);
        sinField.setFont(new Font("Arial", Font.PLAIN, 16));
        loginButton = new JButton("LOGIN");
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(nugreen);
        loginButton.setFont(new Font("Arial", Font.ITALIC, 20));
        invalidLabel = new JLabel("Invalid SIN!");
        invalidLabel.setFont(new Font("Arial", Font.BOLD, 20));
        invalidLabel.setHorizontalAlignment(JLabel.CENTER);
        invalidLabel.setForeground(Color.RED);
        invalidLabel.setVisible(false);

        // Add components to the form
        JPanel formPanel = new JPanel(new GridLayout(3, 2));
        formPanel.setBackground(mainblue);
        formPanel.add(sinLabel);
        formPanel.add(sinField);
        formPanel.add(new JLabel());
        formPanel.add(loginButton);

        add(label, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(invalidLabel, BorderLayout.SOUTH);

        // add the actionlistener to the login button
        loginButton.addActionListener(e -> {
            String sin = sinField.getText();
            if (isValidSin(sin)) {
                // TODO: Implement login logic here
                //this should check what the user type is,
                String usertype = "";
                //and based on said type will open a specific page
                switch (usertype){
                    case "user":
                        new UserPage();
                        this.dispose();
                    case "employee":
                        new EmployeePage();
                        this.dispose();
                }

            } else {
                invalidLabel.setVisible(true);
            }
        });

        // Set window size and show it
        setSize(400, 200);
        setVisible(true);
    }

    private boolean isValidSin(String sin) {
        return ui.userExists(sin);
    }

    public static void main(String[] args) {
        RentingComLogin login = new RentingComLogin();
    }
}