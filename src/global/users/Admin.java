package global.users;

public class Admin implements User{
    private final String accessLevel="ADMIN";

    @Override
    public String getAccessLevel() {
        return accessLevel;
    }
}
