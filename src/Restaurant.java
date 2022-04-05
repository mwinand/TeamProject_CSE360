import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Restaurant {
    private String name;
    private String username;
    private String password;
    private ObservableList<MenuItem> menu;

    public Restaurant(String name, String username, String password) {
	this.name = name;
	this.username = username;
	this.password = password;
	this.menu = FXCollections.observableArrayList();
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
    public String getName() {
	return name;
    }
    public Boolean checkLoginCredentials(String usernameEntry, String passwordEntry) {
        Boolean matchingUsername = usernameEntry.equalsIgnoreCase(getUsername());
        Boolean correctPassword = passwordEntry.equals(password);

        return (matchingUsername && correctPassword);
    }
    public void addToMenu(MenuItem item) {
        if (!(menu.contains(item))) menu.add(item);
    }
    public Boolean removeFromMenu(MenuItem item) {
        int loc;
        Boolean removed = false;
        loc = menu.indexOf(item);
        if (loc != -1) {
            if (menu.remove(loc) == item) removed = true;
        }
        return removed;
    }
    public ObservableList<MenuItem> getMenu() {
	return menu;
    }
}
