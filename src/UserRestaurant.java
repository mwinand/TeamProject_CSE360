import java.util.ArrayList;

public class UserRestaurant extends User {
    private String username;
    private String password;
    private ArrayList<MenuItem> menu;

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
    public void addItemToMenu(MenuItem item) {
        if (!(menu.contains(item))) menu.add(item);
    }
    public Boolean removeItemFromMenu(MenuItem item) {
        int loc;
        Boolean removed = false;
        loc = menu.indexOf(item);
        if (loc != -1) {
            if (menu.remove(loc) == item) removed = true;
        }
        return removed;
    }
}
