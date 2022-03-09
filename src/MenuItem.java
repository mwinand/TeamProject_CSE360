
private enum Category {
    Appetizer,
    Main,
    Desert,
}

public class MenuItem {
    private String food; //Item name
    private double price; //Item Price
    private String imagePath; //Item image
    private String[] ingredientList; //List of Ingredients in an item
    private double timeToCook; //Time to cook item on the menu
    private Category category;

    public void addIngredient(String ing); //Ingredients to add
    public bool removeIngredient(String ing); //Ingredients to remove
    public String[] getIngredientList();
    public String getFood();
    public double getPrice();
    public String getImagePath();
    public double getTimeToCook();
    public String getCategory();
    public void setFood(String newFood, String category);
    public void setPrice(double newPrice);
    public void setImagePath(String path);
    public void setTimeToCook(double newTime);
}
