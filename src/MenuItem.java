import java.util.ArrayList;

enum Category {
    Appetizer,
    Main,
    Dessert,
}

public class MenuItem {
    private String food; //Item name
    private double price; //Item Price
    private String imagePath; //Item image
    private ArrayList<String> ingredientList; //List of Ingredients in an item
    private double timeToCook; //Time to cook item on the menu
    private Category category;

    public MenuItem
	(String food, double price, String imagePath, String[] ingredientList,
	 double timeToCook, String category) {
	this.food = food;
	this.price = price;
	this.imagePath = imagePath;
	this.ingredientList = new ArrayList<String>();
	for(String ing : ingredientList) {
	    this.ingredientList.add(ing);
	}
	this.timeToCook = timeToCook;
	if(category.equals("Appetizer"))
	    this.category = Category.Appetizer;
	else if(category.equals("Dessert"))
	    this.category = Category.Dessert;
	else
	    this.category = Category.Main;
    }

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
    public String[] getIngredientList() {
	    String[] arr = new String[ingredientList.size()];
        arr = ingredientList.toArray(arr);
	    return arr;
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
