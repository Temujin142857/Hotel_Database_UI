package global;
import backend.Database;
import backend.InvalidUsernameOrPasswordException;
import backend.UnauthorisedAccessException;
import backend.InvalidIDException;
import global.users.Admin;
import global.users.Client;
import global.users.Employee;
import global.users.User;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;

//this is where the backend will communicate with the GUI
public class UI {
    private final ArrayList<String> possibleConditions=new ArrayList<>(Arrays.asList("dateStart", "dateEnd", "capacity", "superficie", "chain", "category", "hotelRooms", "priceUpper", "priceLower"));
    private final String databaseName="Hotels";
    private Database database;
    private User user;
    private int reservationID=-1;
    private int rentalID=-1;


    //in the login screen call this function to initialise the database
    public boolean login(String user, String password){
        try {
            database=new Database(databaseName,user,password);
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    /**
     * in login use this function, I just put the passwords and usernames in the function
     * but I could make them final variables if we want
     *
     */
    public User setUser(String username, String password)throws InvalidUsernameOrPasswordException {
        if(username.equals("admin")&&password.equals("Civ mondays")){user= new Admin();return user;}
        else if(username.equals("employee")&&password.equals(("work"))){user= new Employee();return user;}
        else if(username.equals("client")&&password.equals("open")){user= new Client();return user;}
        throw new InvalidUsernameOrPasswordException();
    }

    public boolean isClient(String sin) throws SQLException {
        return database.isClient(sin);
    }


    /**
     * takes a hashmap whose keys are the columns you wish to include
     * and the values are the associated values
     * returns the reservation ID of the newly created ID
     * not accessible to clients
     * @param user
     * @param info
     * @return
     * @throws UnauthorisedAccessException
     */
    public int createRentalFromScratch(User user, HashMap<String,String> info) throws UnauthorisedAccessException {
        int ID=getNextRentalID();
        String[] tempC=info.keySet().toArray(new String[0]);
        String[] tempV= info.values().toArray(new String[0]);
        String[] columns=new String[tempC.length];
        String[] values=new String[tempC.length];
        columns[0]="ID_location";
        values[0]=String.valueOf(ID);
        for (int i = 0; i < columns.length; i++) {
            columns[i+1]=tempC[i];
            values[i+1]=tempV[i];
        }
        database.insertInfo(user,columns,values, "rental");
        return ID;
    }

    /**
     * Overloads CreateRentalFromScratch, using a pre-existing reservationID instead
     * @param user
     * @param reservationID
     * @return
     * @throws UnauthorisedAccessException
     */
    public int createRentalFromReservation(User user, int reservationID, String paymentInfo) throws UnauthorisedAccessException, InvalidIDException {
        HashMap<String,String> reservation=database.getReservation(user, reservationID);
        //add the payment info to the hashmap
        return createRentalFromScratch(user, reservation);
    }

    private int getNextRentalID(){
        if (rentalID==-1){rentalID=database.getCurrentRentalID();}
        rentalID++;
        return rentalID;
    }

    /**
     * takes a hashmap whose keys are the columns you wish to include
     * and the values are the associated values
     * returns the reservation ID of the newly created ID
     * accessible by everyone
     * @param user
     * @param info
     * @return
     * @throws UnauthorisedAccessException
     */
    public int createReservation(User user, HashMap<String,String> info) throws UnauthorisedAccessException {
        int ID=getNextReservationID();
        String[] tempC=info.keySet().toArray(new String[0]);
        String[] tempV= info.values().toArray(new String[0]);
        String[] columns=new String[tempC.length];
        String[] values=new String[tempC.length];
        columns[0]="ID_reservation";
        values[0]=String.valueOf(ID);
        for (int i = 0; i < columns.length; i++) {
            columns[i+1]=tempC[i];
            values[i+1]=tempV[i];
        }
        database.insertInfo(user,columns,values, "reservation");
        return reservationID;
    }

    public int createReservation(User user, String roomID, String clientID,String dateStart, String dateEnd) throws UnauthorisedAccessException {
        Room room=database.getRoom(roomID);
        HashMap values=room.getValues();
        values.put("date_start",dateStart);
        values.put("date_end",dateEnd);
        values.put("NAS_client",clientID);
        return createReservation(user,values);
    }


    private int getNextReservationID(){
        if (reservationID==-1){reservationID=database.getCurrentReservationID();}
        reservationID++;
        return reservationID;
    }

    /**
     * will return a list of rooms to be displayed, filtered by the conditions and sorted by the sortBy
     * still needs to check that the conditions are valid
     * sortby is checked for validity
     * can be used by clients employees and admin
     * @param conditions
     * @param sortBy
     * @return
     */
    private Room[] makeListOfRooms(HashMap<String,String> conditions, String sortBy){
        HashMap<String,String> fullConditions=new HashMap<>(){};
        String toSort="idc";
        fullConditions.putAll(conditions);
        if(!conditions.isEmpty()){
            for (String condition:possibleConditions) {
                if(!fullConditions.containsKey(condition)){fullConditions.put(condition,"idc");}
            }
        }
        if(possibleConditions.contains(sortBy)){toSort=sortBy;}
        return database.getRooms(user,fullConditions,toSort);
    }

    /**
     * kind of an overload method for makeListOfRooms
     * takes a hashmop of String to any type, since some values in Room are int and such
     * sortBy is the column you want the list to be sorted by, any value that isn't actually a column is converted to a don't care
     * so you can just put no, or idc, or anything if you don't want it sorted
     * I can overload it to take any parameters you might need
     * @param conditions
     * @param sortBy
     * @return
     */
    public Room[] getListOfRooms(HashMap<String, Type> conditions, String sortBy){
        HashMap<String,String> stringMap=new HashMap<>();
        if(!conditions.isEmpty()){
            for (String condition:conditions.keySet()) {
                stringMap.put(condition,String.valueOf(conditions.get(condition)));
            }
        }
        return makeListOfRooms(stringMap,sortBy);
    }


}
