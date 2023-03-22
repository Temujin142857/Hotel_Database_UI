import java.io.*;
import java.sql.*;
import java.util.Scanner;

//is it better to make on connection that stays open the entire time database is valid
//or open a new on every time a command needs to be sent? that seems secure but slow
public class Database {
    private final String[] sections= new String[]{"Constructors:","Commands to run to update Database:"};
    private String databaseName;
    private String user;
    private String password;
    private Connection conn;
    private Statement st;

    public Database(String databaseName, String user, String password){
        this.databaseName=databaseName;
        this.user=user;
        this.password=password;
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(databaseName, user, password);
        } catch (SQLException e) {
            System.out.println("connection failed");
            e.printStackTrace();
        }
        try {
            st = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void executeCommand(String command){
        //something something security checks
        //something something execute command
    }

    private String executeRequest(String command){
        //something something security checks
        //something something send request
        //return result
        return null;
    }


    public void autoUpdate(){
        for (String section: sections) {
            try {
                runSectionOfCommands(section);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Runs a section of code from the .txt file
     * @param section
     * @throws FileNotFoundException
     */
    private void runSectionOfCommands(String section) throws FileNotFoundException {
        boolean read=false;
        File commandFile= new File("Database_Commands.txt");
        Scanner scanner=new Scanner(commandFile);
        int i=0;
        while(scanner.hasNext()) {
            if (scanner.nextLine().equals(section)) {
                read = true;
            } else if (scanner.nextLine().equals("end")) {
                read = false;
            }
            if (read) {
                i++;
                try {
                    st.executeUpdate(scanner.nextLine());
                } catch (SQLException e) {
                    System.out.println("invalid command, at command#" + i);
                    e.printStackTrace();
                }
            }
        }
    }


}
