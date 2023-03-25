package frontend;

import backend.Database;
import backend.UnauthorisedAccessException;
import global.users.Admin;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            Database base=new Database("DBproject","postgres","Escher#142857");
            base.autoUpdate(new Admin());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (UnauthorisedAccessException e) {
            e.printStackTrace();
        }

    }
}
