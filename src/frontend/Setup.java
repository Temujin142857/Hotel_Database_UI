package frontend;
import java.util.Scanner;
import global.UI;

public class Setup {
    public static void main(String[] args) {
        UI ui=new UI();
        while(true){
        System.out.println("please input the database name");
        // Using Scanner for Getting Input from User
        Scanner in = new Scanner(System.in);
        String databseName = in.nextLine();
        System.out.println("please input the username for the database");
        String databseUsername = in.nextLine();
        System.out.println("please input the password to the database");
        String databsePassword = in.nextLine();
        System.out.println("the program will now login to the database, this may take a few minutes, please be patient");

        if(ui.login(databseName,databseUsername,databsePassword)){
            System.out.println("login to database succesful");
            break;
        }
        else{
            System.out.println("seems like there was an error either your password/username/databaseName are invalid or");
            System.out.println("if the problem persists, it's possible your url to access postgres is different than the standard student one");
            System.out.println("this can be adjusted in the database class, it's a final variable at the top of the class");
            System.out.println("the classpath is fine though, so that's a win");
            System.out.println();
         }
        }
        new RentingComLogin(ui);
    }
}
