package global.users;

public class Employee implements User {
    private final String accessLevel="EMPLOYEE";

    @Override
    public String getAccessLevel() {
        return accessLevel;
    }
}
