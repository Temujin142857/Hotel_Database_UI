import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

//is it better to make one connection that stays open the entire time database is valid
//or open a new on every time a command needs to be sent? that seems secure but slow


public class Database {
    private final String[] sections= new String[]{"Constructors:","Commands to run to update Database:"};
    private Connection conn;//protecting it from garbage collection, maybe unnecisary
    private Statement st;
    private GuardDog goodBoy;

    public Database(String databaseName, String user, String password){
        goodBoy=new GuardDog();
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
            System.out.println("if you're reading this we're screw cause idk why this would throw and error");
            e.printStackTrace();
        }
    }

    /**
     * voir les chambres disponibles en donnant des critères
     * différents, multiples et combinés afin de choisir la chambre qui l’intéresse et de la
     * réserver ou de la louer. Ces critères doivent être : les dates (début, fin) de réservation
     * ou de location, la capacité des chambres, la superficie, la chaîne hôtelière, la catégorie
     * de l’hôtel, le nombre total de chambres dans l’hôtel, le prix des chambres. L’utilisateur
     * doit être en mesure de voir les choix disponibles lorsqu’il modifie la valeur de l’un de
     * ces critères
     * make a tree of possible requests, seems like it would mostly be and's
     * figure out how to find availibility on an interval of dates
     * @param USER user credentials, used to control access
     * @return
     */
    public Room[] getRooms(User USER, String dateStart, String dateEnd, String capacity, String superficie,
                           String chain, String category, String hotelRooms, String priceUpper, String priceLower) throws Exception
    {
        //if not authorised throw exception, use try catch in ui to catch these errors and keep the program going
        String condition= "WHERE ";
        if(dateStart!="idc"){condition+="";}
        if(dateEnd!="idc"){condition+="";}
        if(capacity!="idc"){condition+="AND capacity='"+capacity+"'";}
        if(superficie!="idc"){condition+="AND superficie='"+superficie+"'";}
        if(chain!="idc"){condition+="(SELECT nom_chain FROM hotel WHERE hotel.ID_hotel=ID_hotel AND nom_chain ='"+chain+"')";}
        if(category!="idc"){condition+="AND (SELECT category FROM hotel WHERE hotel.ID_hotel=ID_hotel AND category ='"+category+"')";}
        if(hotelRooms!="idc"){condition+="AND (SELECT hotelRooms FROM hotel WHERE hotel.ID_hotel=ID_hotel AND hotelRooms='"+hotelRooms+"')";}
        if(priceUpper!="idc"){condition+="AND prix<='"+priceUpper+"'";}
        if(priceLower!="idc"){condition+="AND prix>='"+priceLower+"'";}
        if(condition.equals("WHERE ")){condition="";}
        String request="SELECT * FROM chambre "+condition;
        return executeRequestRooms(request);
    }

    /**
     *  L’interface utilisateur doit permettre l’insertion/suppression/mise à jour
     * de toutes les informations relatives aux clients, aux employés, aux hôtels et aux
     * chambres.
     * make a tree of possible requests
     * @param USER user credentials, used to control access
     * @return
     */
    public boolean updateInfo(User USER, String newInfo, String table,String Condition){
        //check user security
        String withCondition="WHERE "+Condition;
        if(Condition.equals("no")){withCondition="";}
        executeCommand("UPDATE "+table+ "SET"+newInfo+withCondition);
        return false;
    }



    public boolean insertInfo(User USER, String newInfo, String table){
        //check user allowances
        executeCommand("INSERT INTO "+table+ "VALUES ("+newInfo+")");
        return false;
    }



    private void executeCommand(String command){
        //something something security checks
        //something something execute command
    }

    private Room[] executeRequestRooms(String request){
        //something something security checks
        ArrayList<Room> rooms=new ArrayList<>();
        try {
            ResultSet rs = st.executeQuery(request);
            while (rs.next()) {
                rooms.add(new Room(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getBoolean(7),rs.getString(8)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rooms.toArray(new Room[]{});
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
