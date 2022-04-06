import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Restaurant extends User {
    private String name;
    private ObservableList<MenuItem> menu;
    private ArrayList<ArrayList<MenuItem>> orders;

    public Restaurant(String name, String username, String password) {
	super(username, password);
	this.name = name;
	this.menu = FXCollections.observableArrayList();
    }
    public String getName() {
	return name;
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
