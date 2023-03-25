package backend;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import global.*;
import global.users.User;

//is it better to make one connection that stays open the entire time database is valid
//or open a new on every time a command needs to be sent? that seems secure but slow


public class Database {
    private final String[] sections= new String[]{"Constructors:","Commands to run to update backend.Database:"};
    private Connection conn;//protecting it from garbage collection, maybe unnecisary
    private Statement st;
    private GuardDog goodBoy;

    public Database(String databaseName, String user, String password) throws SQLException {
        goodBoy=new GuardDog();
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String url="jdbc:postgresql://localhost:5432/"+databaseName;
        conn = DriverManager.getConnection(url, user, password);

        try {
            st = conn.createStatement();
        } catch (SQLException e) {
            System.out.println("if you're reading this we're screw cause idk why this would throw and error");
            e.printStackTrace();
        }
    }

    //code for views,
    //CREATE VIEW view_name AS SELECT column1, column2, ... FROM table_name WHERE condition;
    //first view is number of rooms availible per zone, define zone using address maybe
    //CREATE VIEW roomsPerZone AS SELECT SUM(nombres_chambres) FROM hotel WHERE address LIKE %SpecifiedZone%
    //specified zone is either a city name, country name, ect. basically any string that would be in address
    //second view is the capacity per room, given a hotel
    //CREATE VIEW capacityPerRoom AS SELECT * FROM room WHERE ID_hotel=givenID_hotel ORDER BY capacity
    //returing a table of only capacity would be pretty useless, so I'll just organise it by capacity

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
                           String chain, String category, String hotelRooms, String priceUpper, String priceLower, String toSort)
    {
        String sort = "ORDER BY ";
        String condition= "WHERE ";
        if(!dateStart.equals("idc")){condition+="";}
        if(!dateEnd.equals("idc")){condition+="";}
        if(!capacity.equals("idc")){condition+="AND capacity='"+capacity+"'";}
        if(!superficie.equals("idc")){condition+="AND superficie='"+superficie+"'";}
        if(!chain.equals("idc")){condition+="(SELECT nom_chain FROM hotel WHERE hotel.ID_hotel=ID_hotel AND nom_chain ='"+chain+"')";}
        if(!category.equals("idc")){condition+="AND (SELECT category FROM hotel WHERE hotel.ID_hotel=ID_hotel AND category ='"+category+"')";}
        if(!hotelRooms.equals("idc")){condition+="AND (SELECT hotelRooms FROM hotel WHERE hotel.ID_hotel=ID_hotel AND hotelRooms='"+hotelRooms+"')";}
        if(!priceUpper.equals("idc")){condition+="AND prix<='"+priceUpper+"'";}
        if(!priceLower.equals("idc")){condition+="AND prix>='"+priceLower+"'";}
        if(condition.equals("WHERE ")){condition="";}

        if(toSort.equals("idc")){sort="";}
        else {sort+=toSort;}

        String request="SELECT * FROM chambre "+condition+sort+";";
        return executeRequestRooms(request);
    }

    public Room[] getRooms(User USER, HashMap<String,String> c,String toSort){
        return getRooms(USER,c.get("dateStart"),c.get("dateEnd"),c.get("capacity"),c.get("superficie"),c.get("chain"),c.get("category"),c.get("hotelRooms"),c.get("priceUpper"),c.get("priceLower"),toSort);
    }

    /**
     *  L’interface utilisateur doit permettre l’insertion/suppression/mise à jour
     * de toutes les informations relatives aux clients, aux employés, aux hôtels et aux
     * chambres.
     * make a tree of possible requests
     * @param USER user credentials, used to control access
     * @return
     */
    public void updateInfo(User USER, String newInfo, String table, String Condition) throws UnauthorisedAccessException {
        if(!USER.getAccessLevel().equals("EMPLOYEE")){
            throw new UnauthorisedAccessException("Users with access level "+USER.getAccessLevel()+" cannot insert info");
        }
        String withCondition="WHERE "+Condition;
        if(Condition.equals("no")){withCondition="";}
        executeCommand("UPDATE "+table+ "SET"+newInfo+withCondition+";");
    }



    public void insertInfo(User USER, String[] values, String[] columns, String table) throws UnauthorisedAccessException {
        if(USER.getAccessLevel().equals("CLIENT")&&!table.equals("reservation")){
            throw new UnauthorisedAccessException("Users with access level "+USER.getAccessLevel()+" cannot insert info");
        }
        String sqlColumns=" (";
        String sqlValues="(";
        for (int i = 0; i < columns.length; i++) {
            sqlColumns+=columns[i];
            sqlValues+="'"+values[i]+"'";
            if (i!=columns.length-1){sqlColumns+=", ";sqlValues+=", ";}

        }
        sqlColumns+=")";
        sqlValues+=")";
        executeCommand("INSERT INTO "+table+sqlColumns+ " VALUES "+sqlValues+";");

    }

    public int getCurrentRentalID(){
        ResultSet result= null;
        try {
            result = st.executeQuery("SELECT Max(ID_location) FROM Location AS current_ID;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        while (true){
            try {
                if (!result.next()) break;
                return result.getInt(1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return -2;
    }

    public int getCurrentReservationID(){
        ResultSet result= null;
        try {
            result = st.executeQuery("SELECT Max(ID_reservation) FROM Reservation AS current_ID;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        while (true){
            try {
                if (!result.next()) break;
                return result.getInt(1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public HashMap<String,String> getReservation(User user, int reservationID)throws InvalidIDException{
        HashMap<String,String> reservation=new HashMap<>();
        ResultSet result=null;
        try {
            result= st.executeQuery("SELECT * FROM Reservation WHERE ID_reservation='"+reservationID+"';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            int i=1;
            ResultSetMetaData resultData=result.getMetaData();
            int columnNum=resultData.getColumnCount();

            if(result.next()){
                while(i<columnNum) {
                    reservation.put(resultData.getColumnName(i), String.valueOf(result.getObject(i + 1)));
                    //this might have an error, since I'm not sure what string value of will return here
                    //in theory each object is an int or string, but it might not realise that and use a special
                    //object method giving me an address or random string
                    //also not sure how safe get columnName is
                    i++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservation;
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



    public void autoUpdate(User USER) throws UnauthorisedAccessException {
        if(!USER.getAccessLevel().equals("ADMIN")){
            throw new UnauthorisedAccessException("Users with access level "+USER.getAccessLevel()+" cannot insert info");
        }
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
        File commandFile= new File("Hotel_Database_UI/SRC/backend/Database_Commands.txt");
        Scanner scanner=new Scanner(commandFile);
        int i=0;
        while(scanner.hasNext()) {
            String line= scanner.nextLine();
            if (line.equals(section)) {
                read = true;
            } else if (line.equals("break")) {
                read = false;
            }
            if (read) {
                i++;
                try {
                    st.executeUpdate(line);
                } catch (SQLException e) {
                    System.out.println("invalid command, at command#" + i);
                    e.printStackTrace();
                }
            }
        }
    }


}
