package global;
import backend.Database;
import backend.InvalidUsernameOrPasswordException;
import backend.UnauthorisedAccessException;
import global.users.Admin;
import global.users.Client;
import global.users.Employee;
import global.users.User;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;

//this is where the backend with comomunicate with the GUI
public class UI {
    private final ArrayList<String> possibleConditions=new ArrayList<>(Arrays.asList("dateStart", "dateEnd", "capacity", "superficie", "chain", "category", "hotelRooms", "priceUpper", "priceLower"));
    private final String databaseName="Hotels";
    private Database database;
    private User user;

    //in the login screen call this function
    public boolean login(String user, String password){
        try {
            database=new Database(databaseName,user,password);
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public User setUser(String username, String password)throws InvalidUsernameOrPasswordException {
        if(username.equals("admin")&&password.equals("Civ mondays")){user= new Admin();return user;}
        else if(username.equals("employee")&&password.equals(("work"))){user= new Employee();return user;}
        else if(username.equals("client")&&password.equals("open")){user= new Client();return user;}
        throw new InvalidUsernameOrPasswordException();
    }

    /**
     * kind of an overloaded method for makeListOfRooms
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

    public boolean insertData(){
        return true;
    }



    /**
     * will return a list of rooms to be displayed, filtered by the conditions and sorted by the sortBy
     * still needs to check that the conditions are valid
     * sortby is checked for validity
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


}
