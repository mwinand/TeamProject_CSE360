import java.util.ArrayList;

enum Category {
    Appetizer,
    Main,
    Desert,
}

public class MenuItem {
    private String food; //Item name
    private double price; //Item Price
    private String imagePath; //Item image
    private ArrayList<String> ingredientList; //List of Ingredients in an item
    private double timeToCook; //Time to cook item on the menu
    private Category category;

    public void addIngredient(String ing) { //Ingredients to add
        if (!(ingredientList.contains(ing))) ingredientList.add(ing);
    }
    public Boolean removeIngredient(String ing) { //Ingredients to remove
        int loc;
        Boolean removed = false;
        loc = ingredientList.indexOf(ing);
        if (loc != -1) {
            if (ingredientList.remove(loc) == ing) removed = true;
        }
        return removed;
    }
    public ArrayList<String> getIngredientList() {
        return ingredientList;
    }
    public String getFood() {
        return food;
    }
    public double getPrice() {
        return price;
    }
    public String getImagePath() {
        return imagePath;
    }
    public double getTimeToCook() {
        return timeToCook;
    }
    public Category getCategory() {
        return category;
    }
    public String printCategory() {
        return getCategory().name();
    }
    public void setFood(String newFood, String category) {
        food = newFood;
        this.category = Category.valueOf(category);
    }
    public void setPrice(double newPrice) {
        price = newPrice;
    }
    public void setImagePath(String path) {
        imagePath = path;
    }
    public void setTimeToCook(double newTime) {
        timeToCook = newTime;
    }
}
