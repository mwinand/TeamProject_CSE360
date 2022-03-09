

public class MenuItem {
    //Keep in mind some of these might be used as classes not variables
    public string food; //Item name
    public double price; //Item Price
    public string imagePath; //Item image
    public string IngredientsList; //List of Ingredients in an item
    public double TimeToCook; //Time to cook item on the menu
}

public class AddItem { //Add duplicate Items to order
    public int AddItemsNumber; //Number of duplicate Items to add to order
}

public class Categories { // Type of food

}

public class removeIngredients { //List of ingredients to remove

}

public class ViewAllItems { // Items on cart(order)
    public double TotalPrice; // Total Price of the order
}

public class CheckPaymentInfo { //Check if the user has payment info saved
    public string UseSavedPaymentInfo; //User has as credit card saved for payment
    public string InvalidPaymentInfo; //Invalid payment info message
    
}

public class AddNewPayment { //Add new payment info prompt
    

}

public class IsOrderEmpty { //Check if order is empty
    public string EmptyOrder; //Empty order message

}

public class CancelOrder { //Cancel Order prompt

}
public class SearchBar { //User types what they are looking for
    public bool itemNotFound; //Is item avaliable
    public string itemNotFoundMessage; //Item not found message
}

public class itemDuplicate { //Is item a duplicate(restaurant)

}

public class RemoveItem { //Remove Items to order
    public int RemoveItemsNumber; //Number of duplicate Items to remove from order

}