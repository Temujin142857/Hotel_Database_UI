package backend;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;
import global.*;
import global.users.User;
import org.apache.commons.lang3.StringUtils;

//is it better to make one connection that stays open the entire time database is valid
//or open a new on every time a command needs to be sent? that seems secure but slow


public class Database {
    private final String[] sections= new String[]{"Constructors:"};
    private final String[] zones=new    String[]{"placeholder"};
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
            System.out.println("if you're reading this we're screwed cause idk why this would throw and error");
            e.printStackTrace();
        }
    }


    //Views:
    //
    //CREATE OR REPLACE VIEW "DBproject".numOfRoomsByZone AS
    //    SELECT count("Hotel".nombre_chambres) AS count
    //    FROM "DBproject"."Hotel"
    //    WHERE "Hotel".adresse::text ~~ '%cityname%'::text;
    //
    //CREATE OR REPLACE VIEW "DBproject".capacityOfHotel AS
    //SELECT capacité
    //FROM "DBproject"."Chambre"
    //WHERE ID_hotel=GIVEN_ID_hotel

    //view one
    public HashMap<String,Integer> numOfRoomsByZone(){
        //access the relavent view
        return null;
    }

    public HashMap<String, Integer> capcityOFRoomsInHotel(String hotel){
        HashMap<String,Integer> result=new HashMap<>();
        try {
            st.execute("CREATE OR REPLACE VIEW \"DBproject\".capacityOfHotel AS SELECT capacité FROM \"DBproject\".\"Chambre\" WHERE ID_hotel="+hotel+";");
            ResultSet rt=st.executeQuery("SELECT * FROM \"DBproject\".capacityOfHotel");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
                           String chain, String category, String hotelRooms, String priceUpper, String priceLower, String toSort)
    {
        //SELECT * FROM Chambre where Chambre.ID = Select chambre.id From Reservation where
        String sort = " ORDER BY ";
        String condition= "WHERE ";
        if(!dateStart.equals("idc")&&!dateEnd.equals("idc")){if(!condition.equals("WHERE ")){condition+="AND ";}condition+="(ID_Chambre = SELECT ID_Chambre FROM Reservation WHERE NOT EXISTS (SELECT ID_Chambre FROM Reservation WHERE ((date_end < '"+dateEnd+"' AND date_end>'"+dateStart+"')OR (date_start<'"+dateEnd+"' AND date_start>'"+dateStart+"'))";}
        if(!capacity.equals("idc")){if(!condition.equals("WHERE ")){condition+="AND ";}condition+="capacity='"+capacity+"'";}
        if(!superficie.equals("idc")){if(!condition.equals("WHERE ")){condition+="AND ";}condition+="superficie='"+superficie+"'";}
        if(!chain.equals("idc")){if(!condition.equals("WHERE ")){condition+="AND ";}condition+="(ID_hotel=SELECT ID_hotel FROM hotel WHERE nom_chain='"+chain+"')";}
        if(!category.equals("idc")){if(!condition.equals("WHERE ")){condition+="AND ";}condition+="(ID_hotel=SELECT ID_hotel FROM hotel WHERE category='"+category+"')";}
        if(!hotelRooms.equals("idc")){if(!condition.equals("WHERE ")){condition+="AND ";}condition+="(ID_hotel=SELECT ID_hotel FROM hotel WHERE nombre_chambre='"+hotelRooms+"')";}
        if(!priceUpper.equals("idc")){if(!condition.equals("WHERE ")){condition+="AND ";}condition+="prix<='"+priceUpper+"'";}
        if(!priceLower.equals("idc")){if(!condition.equals("WHERE ")){condition+="AND ";}condition+="prix>='"+priceLower+"'";}
        else if(condition.equals("WHERE ")){condition="";}

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
            result = st.executeQuery("SELECT Max(ID_reservation) AS current_ID FROM Reservation;");
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
        String line;
        String nLine;
        try {
            while (scanner.hasNext()) {
                line = scanner.nextLine();

                if (line.equals("//"+section)) {
                    read = true;
                }
                if (read) {
                    i++;

                    while (line.contains("//")||StringUtils.isAllBlank(line)){
                        line=scanner.nextLine();
                    }

                    while (!line.contains(";")) {
                        line += "\n" + scanner.nextLine();

                    }
                    if (line.contains("BEGIN")) {
                        while (true) {
                            nLine = scanner.nextLine();
                            line += "\n" + nLine;
                            if (nLine.equals("END;")) {
                                break;
                            }
                        }
                    }
                    if (line.contains("$")) {
                        while (StringUtils.countMatches(line, '$') != 4) {
                            nLine = scanner.nextLine();
                            line += "\n" + nLine;
                        }
                    }
                    if(line.contains("break;")){
                        read=false;
                        continue;
                    }
                    try {
                        st.executeUpdate(line);
                    } catch (SQLException e) {
                        System.out.println("invalid command, at command#" + i);
                        System.out.println("issue:\n"+line+"\n");
                        e.printStackTrace();
                    }
                }
            }
        }
        catch (NoSuchElementException e){
            System.out.println("you're missing a ;, END;, or $BODY$ somewhere");
        }
    }


}
