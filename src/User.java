

public class User {
    private String username;
    private String password;

    public void setUsername(String newUsername) {
        username = newUsername;
    }
    public void setPassword(String newPassword) {
        password = newPassword;
    }
    public String getUsername() {
        return username;
    }
    private String getPassword() {
        return password;
    }
    public Boolean checkLoginCredentials(String usernameEntry, String passwordEntry) {
        Boolean matchingUsername = usernameEntry.equalsIgnoreCase(getUsername());
        Boolean correctPassword = passwordEntry.equals(getPassword());

        return (matchingUsername && correctPassword);
    }
}
