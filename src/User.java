

public class User {
    private String username;
    private String password;

    public void setUsername();
    public void setPassword();
    public String getUsername() {
        return username;
    }
    private String getPassword() {
        return password;
    }
    private Boolean checkLoginCredentials(String usernameEntry, String passwordEntry) {
        Boolean matchingUsername = usernameEntry.equalsIgnoreCase(getUsername());
        Boolean correctPassword = passwordEntry.equals(getPassword());

        return (matchingUsername && correctPassword);
    }
}
