import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class Database {
    private final String[] sections= new String[]{"Constructors:","Commands to run to update Database:"};
    private String databaseName;
    private String user;
    private String password;

    public Database(String databaseName, String user, String password){
        this.databaseName=databaseName;
        this.user=user;
        this.password=password;
    }

    public void autoUpdate(){
        for (String section: sections) {
            try {
                runSectionOfCommands(section);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void runSectionOfCommands(String section) throws ClassNotFoundException, SQLException, FileNotFoundException {
        boolean read=false;
        File commandFile= new File("Database_Commands.txt");
        Scanner scanner=new Scanner(commandFile);
        Class.forName("org.postgresql.Driver");
        Connection conn = DriverManager.getConnection(databaseName, user, password);
        Statement st = conn.createStatement();
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
