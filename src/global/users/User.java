package global.users;

public interface User {
    String getAccessLevel();
    static User makeUser(String type){
        if(type.equals("CLIENT"))return new Client();
        if(type.equals("EMPLOYEE"))return new Employee();
        else if(type.equals("ADMIN"))return new Admin();
        return null;
    }

}
