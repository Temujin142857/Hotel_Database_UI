package global.users;

//should be able to insert a customer payment
public class Client implements User {
    private final String accessLevel="CLIENT";

    @Override
    public String getAccessLevel() {
        return accessLevel;
    }
}
