public class User {
    private String username;
    private String password;

    public User(String username, String password) {
	this.username = username;
	this.password = password;
    }
    public void setUsername(String newUsername) {
        username = newUsername;
    }
    public void setPassword(String newPassword) {
        password = newPassword;
    }
    public String getUsername() {
        return username;
    }
    public Boolean checkLoginCredentials(String usernameEntry, String passwordEntry) {
        Boolean matchingUsername = usernameEntry.equalsIgnoreCase(username);
        Boolean correctPassword = passwordEntry.equals(password);

        return (matchingUsername && correctPassword);
    }
}
