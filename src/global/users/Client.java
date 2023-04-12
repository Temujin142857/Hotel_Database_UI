package global.users;

//should be able to insert a customer payment
public class Client implements User {
    private final String accessLevel="CLIENT";
    private String SIN;

    @Override
    public String getAccessLevel() {
        return accessLevel;
    }
}
