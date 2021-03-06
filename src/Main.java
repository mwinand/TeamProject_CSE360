import java.util.ArrayList;
import java.util.Arrays;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.util.Callback;
import javafx.beans.value.ObservableValue;
import javafx.beans.property.SimpleStringProperty;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;

public class Main extends Application {

    Restaurant restaurant = new Restaurant("RestaurantName", "username", "password");
    ScrollPane orderScrollPane = new ScrollPane();
    Label totalCost = new Label("$");
    Label timeToCook = new Label("");
    Label customersAhead = new Label("Customers Ahead: 0");
    Customer customer = new Customer();

    public static void main(String[] args) {
	launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
	// Preparation
	BorderPane mainLayout = new BorderPane();
	BorderPane orderLayout = new BorderPane();
	ScrollPane scrollPane = new ScrollPane();
	VBox titleLayout = new VBox(10);
	VBox menuLayout = new VBox(20);
	VBox searchLayout = new VBox(40);
	VBox orderList = new VBox(20);
	VBox paymentLayout = new VBox(20);
	VBox restaurantLayout = new VBox(20);
	VBox newItemLayout = new VBox(20);
	VBox loginLayout = new VBox(20);
	Stage window = primaryStage;
	Scene mainScene, searchScene, orderScene, paymentScene, restaurantScene,
			newItemScene, loginScene;
	Button searchButton, toMainFromSearchButton, orderButton, toMainFromOrderButton,
	    checkoutButton, payButton, loginButton;
	Label title, status, paymentStatus;
	TextField paymentInfo, nameField, securityField, monthField, yearField;
	Label creditCardLabel, nameLabel, securityLabel, expirationLabel;

	ArrayList<Customer> savedUsers = new ArrayList<Customer>();
	ArrayList<Button> menuButtons = new ArrayList<Button>();

	// Get information from saved file
	String buffer;
	try {
	    BufferedReader bf = new BufferedReader(new FileReader("users.csv"));
	    while((buffer = bf.readLine()) != null) {
		String[] info = buffer.split(",");
		ArrayList<MenuItem> order = new ArrayList<MenuItem>();
		for (int i = 2; i < info.length; i++) {
		    String[] itemInfo = info[i].split(":");
		    order.add(new MenuItem(itemInfo[0], Double.parseDouble(itemInfo[1]), itemInfo[2], Arrays.copyOfRange(itemInfo, 5, itemInfo.length), Double.parseDouble(itemInfo[3]), itemInfo[4]));
		}
		savedUsers.add(new Customer(info[0], info[1], order));
	    }
	    bf.close();
	}
	catch (IOException e) {
	    e.printStackTrace();
	}

	// food, price, path, ingredients, timecook, category
	restaurant.addToMenu
	    (new MenuItem("Hamburger", 10.99, "hamburger.jpg", new String[] {"Brioche Bun", "Angus Beef", "American Cheese"}, 10.0, "Main"));
	restaurant.addToMenu
	    (new MenuItem("Hotdog", 7.99, "hotdog.jpg", new String[] {"Bun", "Sausage"}, 5.0, "Main"));
	restaurant.addToMenu
	    (new MenuItem("Empanada", 8.29, "empanada.jpg", new String[] {"Corn", "Meat"}, 4.0, "Appetizer"));
	restaurant.addToMenu
	    (new MenuItem("Blueberry Pie", 5.69, "blueberryPie.jpg", new String[] {"Pie Crust", "Blueberries"}, 1.0, "Dessert"));
	restaurant.addToMenu
	    (new MenuItem("Tacos", 6.99, "tacos.jpg", new String[] {"Hard Corn Shell", "Carne Asada", "Cheese"}, 4.0, "Main"));
	restaurant.addToMenu
	    (new MenuItem("Dumplings", 4.39, "dumplings.jpg", new String[] {"Dumpling"}, 3.0, "Appetizer"));

	mainScene = new Scene(mainLayout, 600, 600);
	searchScene = new Scene(searchLayout, 600, 600);
	orderScene = new Scene(orderLayout, 600, 600);
	paymentScene = new Scene(paymentLayout, 600, 600);
	restaurantScene = new Scene(restaurantLayout, 600, 600);
	newItemScene = new Scene(newItemLayout, 600, 600);
	loginScene = new Scene(loginLayout, 600, 600);

	// Main Scene

	// Title
	searchButton = new Button("Search");
	searchButton.setOnAction(e -> window.setScene(searchScene));
	title = new Label(restaurant.getName());
	title.setMinHeight(80);
	title.setFont(new Font("VictorMono Nerd Font", 30));
	status = new Label("");
	status.setMinHeight(10);
	status.setTextFill(Color.web("#ff0000"));
	titleLayout.getChildren().addAll(title, searchButton, status);
	titleLayout.setAlignment(Pos.CENTER);
	mainLayout.setTop(titleLayout);

	// Actual Menu

	// Menu Items
	menuLayout.setAlignment(Pos.CENTER);
	for(int i = 0; i < restaurant.getMenu().size(); i++) {
	    MenuItem menuItem = restaurant.getMenu().get(i);
	    // Layout prep
	    GridPane row = new GridPane();
	    // Column 0 and 4 serve as margins, Column 1 contains images
	    // Column 2 contains item info, Column 3 contains buttons
	    ColumnConstraints column0 = new ColumnConstraints();
	    column0.setPercentWidth(15);
	    ColumnConstraints column1 = new ColumnConstraints();
	    column1.setPercentWidth(30);
	    ColumnConstraints column2 = new ColumnConstraints();
	    column2.setPercentWidth(30);
	    ColumnConstraints column3 = new ColumnConstraints();
	    column3.setPercentWidth(10);
	    ColumnConstraints column4 = new ColumnConstraints();
	    column4.setPercentWidth(15);
	    row.getColumnConstraints()
		.addAll(column0, column1, column2, column3, column4);
	    VBox itemInfo = new VBox();
	    itemInfo.setAlignment(Pos.CENTER);
	    StackPane imageContainer = new StackPane();

	    // Item prep
	    Label itemName = new Label(menuItem.getFood());
	    String description = "";
	    for(String ing : menuItem.getIngredientList()) {
		description += ing;
		description += ", ";
	    }
	    description = description.substring(0, description.length() - 2);
	    Label itemIng = new Label(description);
	    menuButtons.add(new Button("Add"));
	    menuButtons.get(i).setOnAction(e -> {
		    status.setText("Added item to the order");
		    customer.addToOrder(menuItem);
		});
	    StackPane buttonContainer = new StackPane();
	    buttonContainer.getChildren().add(menuButtons.get(i));

	    // Add Items to Layouts
	    itemInfo.getChildren().addAll(itemName, itemIng);
	    imageContainer.getChildren().add(menuItem.getImageView());
	    GridPane.setConstraints(imageContainer, 1, 0);
	    GridPane.setConstraints(itemInfo, 2, 0);
	    GridPane.setConstraints(buttonContainer, 3, 0);
	    row.getChildren()
		.addAll(imageContainer, itemInfo, buttonContainer);
	    menuLayout.getChildren().add(row);
	}

	scrollPane.setContent(menuLayout);
	scrollPane.setFitToHeight(true);
	scrollPane.setFitToWidth(true);
	mainLayout.setCenter(scrollPane);
	orderButton = new Button("Go to Order");
	orderButton.setOnAction(e -> {
		window.setScene(orderScene);
		orderScrollPane.setContent(orderList(customer));
	    });
	VBox menuBottomLayout = new VBox(20);
	Button restaurantButton = new Button("Restaurant Mode");
	restaurantButton.setOnAction(e -> window.setScene(restaurantScene));
	loginButton = new Button("Log in");
	loginButton.setOnAction(e -> window.setScene(loginScene));
	menuBottomLayout.getChildren().addAll(orderButton, loginButton, restaurantButton);
	menuBottomLayout.setAlignment(Pos.CENTER);
	mainLayout.setBottom(menuBottomLayout);
	mainLayout.setMargin(menuBottomLayout, new Insets(40));

	// Search Scene

	TextField filterField = new TextField();
	TableView<MenuItem> table = new TableView<MenuItem>(restaurant.getMenu());
	TableColumn<MenuItem, String> nameColumn = new TableColumn<MenuItem, String>("Name");
	nameColumn.setCellValueFactory(new Callback<CellDataFeatures<MenuItem, String>, ObservableValue<String>>() {
		public ObservableValue<String> call(CellDataFeatures<MenuItem, String> p) {
		    return new SimpleStringProperty(p.getValue().getFood());
		}
	    });
	TableColumn<MenuItem, String> ingColumn = new TableColumn<MenuItem, String>("Ingredients");
	ingColumn.setCellValueFactory(new Callback<CellDataFeatures<MenuItem, String>, ObservableValue<String>>() {
		public ObservableValue<String> call(CellDataFeatures<MenuItem, String> p) {
		    String description = "";
		    for(String ing : p.getValue().getIngredientList()) {
			description += ing;
			description += ", ";
		    }
		    description = description.substring(0, description.length() - 2);
		    return new SimpleStringProperty(description);
		}
	    });
	table.getColumns().setAll(nameColumn, ingColumn);

	FilteredList<MenuItem> filteredData =
	    new FilteredList<MenuItem>(restaurant.getMenu(), p -> true);

	filterField.textProperty().addListener((observable, oldValue, newValue) -> {
		filteredData.setPredicate(myObject -> {
			if(newValue == null || newValue.isEmpty())
			    return true;
			String lowerCaseFilter = newValue.toLowerCase();
			if(String.valueOf(myObject.getFood()).toLowerCase().contains(lowerCaseFilter))
			    return true;
			for(String ing : myObject.getIngredientList()) {
			    if(String.valueOf(ing).toLowerCase().contains(lowerCaseFilter))
			       return true;
			}
			return false;
		    });
	    });

	SortedList<MenuItem> sortedData = new SortedList<MenuItem>(filteredData);
	sortedData.comparatorProperty().bind(table.comparatorProperty());
	table.setItems(sortedData);

	toMainFromSearchButton = new Button("Return to Menu");
	toMainFromSearchButton.setOnAction(e -> window.setScene(mainScene));
	searchLayout.setAlignment(Pos.CENTER);
	searchLayout.getChildren().addAll(filterField, table, toMainFromSearchButton);
	searchLayout.setMargin(filterField, new Insets(0, 80, 0, 80));
	searchLayout.setMargin(table, new Insets(0, 40, 0, 40));

	// Order Scene

	orderScrollPane.setContent(orderList);
	orderScrollPane.setFitToHeight(true);
	orderScrollPane.setFitToWidth(true);
	orderLayout.setCenter(orderScrollPane);
	orderLayout.setMargin(orderScrollPane, new Insets(40, 40, 20, 40));

	HBox orderButtonRow = new HBox(20);
	orderButtonRow.setAlignment(Pos.CENTER);
	toMainFromOrderButton = new Button("Return to Menu");
	toMainFromOrderButton.setOnAction(e -> window.setScene(mainScene));
	checkoutButton = new Button("Checkout");
	checkoutButton.setOnAction(e -> {
		if(customer.getOrderItems().length != 0)
		    window.setScene(paymentScene);
		else
		    totalCost.setText("Cannot checkout, cart is empty.");
	    });
	orderButtonRow.getChildren().addAll(toMainFromOrderButton, checkoutButton);
	VBox orderBottomLayout = new VBox(20);
	orderBottomLayout.setAlignment(Pos.CENTER);
	orderBottomLayout.getChildren().addAll(totalCost, timeToCook, customersAhead, orderButtonRow);
	orderBottomLayout.setMargin(orderButtonRow, new Insets(0, 0, 20, 0));
	orderLayout.setBottom(orderBottomLayout);
	orderLayout.setMargin(orderBottomLayout, new Insets(20));

	// Payment Scene

	paymentStatus = new Label("");
	payButton = new Button("Finish Order");
	GridPane paymentGrid = new GridPane();
	creditCardLabel = new Label("Credit card number:");
	nameLabel = new Label("Name on card:");
	securityLabel = new Label("Security number:");
	expirationLabel = new Label("Expiration Date:");
	paymentInfo = new TextField();
	nameField = new TextField();
	securityField = new TextField();
	monthField = new TextField();
	yearField = new TextField();
	paymentInfo.textProperty().addListener((observable, oldValue, newValue) -> {
		if(!newValue.matches("\\d*")) {
		    paymentInfo.setText(newValue.replaceAll("[^\\d]", ""));
		}
		if(paymentInfo.getText().length() > 16) {
		    String s = paymentInfo.getText().substring(0, 16);
		    paymentInfo.setText(s);
		}
	    });
	securityField.textProperty().addListener((observable, oldValue, newValue) -> {
		if(!newValue.matches("\\d*")) {
		    securityField.setText(newValue.replaceAll("[^\\d]", ""));
		}
		if(securityField.getText().length() > 3) {
		    String s = securityField.getText().substring(0, 3);
		    securityField.setText(s);
		}
	    });
	monthField.textProperty().addListener((observable, oldValue, newValue) -> {
		if(!newValue.matches("\\d*")) {
		    monthField.setText(newValue.replaceAll("[^\\d]", ""));
		}
		if(monthField.getText().length() > 2) {
		    String s = monthField.getText().substring(0, 2);
		    monthField.setText(s);
		}
	    });
	yearField.textProperty().addListener((observable, oldValue, newValue) -> {
		if(!newValue.matches("\\d*")) {
		    yearField.setText(newValue.replaceAll("[^\\d]", ""));
		}
		if(yearField.getText().length() > 4) {
		    String s = yearField.getText().substring(0, 4);
		    yearField.setText(s);
		}
	    });
	payButton.setOnAction(e -> {
		if(paymentInfo.getText().length() == 16
		   && nameField.getText().length() != 0
		   && securityField.getText().length() == 3
		   && monthField.getText().length() == 2
		   && yearField.getText().length() == 4) {
		    paymentStatus.setText("");
		    paymentInfo.setText("");
		    nameField.setText("");
		    securityField.setText("");
		    monthField.setText("");
		    yearField.setText("");
		    status.setText("Order successful!");
		    customer.clearOrder();
		    window.setScene(mainScene);
		}
		else
		    paymentStatus.setText("Payment fields not filled out correctly.");
	    });
	HBox expirationRow = new HBox(10);
	expirationRow.getChildren().addAll(monthField, yearField);
	GridPane.setConstraints(creditCardLabel, 0, 0);
	GridPane.setConstraints(paymentInfo, 1, 0);
	GridPane.setConstraints(nameLabel, 0, 1);
	GridPane.setConstraints(nameField, 1, 1);
	GridPane.setConstraints(securityLabel, 0, 2);
	GridPane.setConstraints(securityField, 1, 2);
	GridPane.setConstraints(expirationLabel, 0, 3);
	GridPane.setConstraints(expirationRow, 1, 3);
	paymentGrid.getChildren().addAll(creditCardLabel, paymentInfo, nameLabel, nameField, securityLabel, securityField, expirationLabel, expirationRow);
	paymentLayout.getChildren().addAll(paymentStatus, paymentGrid, payButton);
	paymentGrid.setAlignment(Pos.CENTER);
	paymentLayout.setAlignment(Pos.CENTER);

	// Restaurant Settings Scene
	Label resStatus = new Label("");
	Label resNameLabel = new Label("Restaurant Name");
	TextField resNameField = new TextField();
	Label resUsernameLabel = new Label("Restaurant Username");
	TextField resUsernameField = new TextField();
	Label resPasswordLabel = new Label("Restaurant Password");
	TextField resPasswordField = new TextField();

	Button newItemButton = new Button("New Menu Item");
	newItemButton.setOnAction(e -> window.setScene(newItemScene));
	Button toMenuFromRestaurant = new Button("Save and Return");
	toMenuFromRestaurant.setOnAction(e -> window.setScene(mainScene));

	GridPane restaurantGrid = new GridPane();
	GridPane.setConstraints(resNameLabel, 0, 0);
	GridPane.setConstraints(resNameField, 1, 0);
	GridPane.setConstraints(resUsernameLabel, 0, 1);
	GridPane.setConstraints(resUsernameField, 1, 1);
	GridPane.setConstraints(resPasswordLabel, 0, 2);
	GridPane.setConstraints(resPasswordField, 1, 2);
	restaurantGrid.getChildren().addAll(resNameLabel, resNameField, resUsernameLabel,
		       resUsernameField, resPasswordLabel, resPasswordField);
	restaurantGrid.setAlignment(Pos.CENTER);

	restaurantLayout.getChildren().addAll(resStatus, restaurantGrid, newItemButton, toMenuFromRestaurant);
	restaurantLayout.setAlignment(Pos.CENTER);

	// Add Menu Item Scene
	Label newItemTitle = new Label("New Menu Item");

	GridPane newItemGrid = new GridPane();
	Label newName = new Label("Name");
	TextField newNameField = new TextField();
	Label newPrice = new Label("Price");
	TextField newPriceField = new TextField();
	Label newPath = new Label("Image Path");
	TextField newPathField = new TextField();
	Label newIngredientList = new Label("Ingredient List (Separated by commas)");
	TextField newIngredientListField = new TextField();
	Label newTimeToCook = new Label("Time to Cook");
	TextField newTimeToCookField = new TextField();
	Label newCategory = new Label("Category (Appetizer, Main, Dessert)");
	TextField newCategoryField = new TextField();
	newPriceField.textProperty().addListener((ob, ov, nv) -> {
		if(!nv.matches("\\d*")) {
		    paymentInfo.setText(nv.replaceAll("[^\\d]", ""));
		}
	    });
	newTimeToCookField.textProperty().addListener((ob, ov, nv) -> {
		if(!nv.matches("\\d*")) {
		    paymentInfo.setText(nv.replaceAll("[^\\d]", ""));
		}
	    });
	GridPane.setConstraints(newName, 0, 0);
	GridPane.setConstraints(newNameField, 1, 0);
	GridPane.setConstraints(newPrice, 0, 1);
	GridPane.setConstraints(newPriceField, 1, 1);
	GridPane.setConstraints(newPath, 0, 2);
	GridPane.setConstraints(newPathField, 1, 2);
	GridPane.setConstraints(newIngredientList, 0, 3);
	GridPane.setConstraints(newIngredientListField, 1, 3);
	GridPane.setConstraints(newTimeToCook, 0, 4);
	GridPane.setConstraints(newTimeToCookField, 1, 4);
	GridPane.setConstraints(newCategory, 0, 5);
	GridPane.setConstraints(newCategoryField, 1, 5);
	newItemGrid.getChildren().addAll(newName, newNameField, newPrice, newPriceField, newPath, newPathField, newIngredientList, newIngredientListField, newTimeToCook, newTimeToCookField, newCategory, newCategoryField);
	newItemGrid.setAlignment(Pos.CENTER);

	HBox newItemButtonLayout = new HBox(40);
	Button newItemCancel = new Button("Cancel");
	newItemCancel.setOnAction(e -> window.setScene(restaurantScene));
	Button newItemAdd = new Button("Add");
	newItemAdd.setOnAction(e -> {
		if(newNameField.getText().length() != 0 &&
		   newPriceField.getText().length() != 0 &&
		   newPathField.getText().length() != 0 &&
		   newIngredientListField.getText().length() != 0 &&
		   newTimeToCookField.getText().length() != 0 &&
		   newCategoryField.getText().length() != 0) {
		    MenuItem newItem = new MenuItem
			(newNameField.getText(),
			 Double.parseDouble(newPriceField.getText()),
			 newPathField.getText(),
			 newIngredientListField.getText().split(","),
			 Double.parseDouble(newTimeToCookField.getText()),
			 newCategoryField.getText());
		    restaurant.addToMenu(newItem);
		    newNameField.clear();
		    newPriceField.clear();
		    newPathField.clear();
		    newIngredientListField.clear();
		    newTimeToCookField.clear();
		    newCategoryField.clear();
		    resStatus.setText("Added new item to menu successfully.");
		    window.setScene(restaurantScene);
		}
	    });
	newItemButtonLayout.getChildren().addAll(newItemCancel, newItemAdd);
	newItemButtonLayout.setAlignment(Pos.CENTER);

	newItemLayout.getChildren().addAll(newItemTitle, newItemGrid, newItemButtonLayout);
	newItemLayout.setAlignment(Pos.CENTER);

	// User Login Scene
	Label loginStatus = new Label("");
	GridPane loginGrid = new GridPane();
	Label loginUsername = new Label("Username: ");
	TextField loginUsernameField = new TextField();
	Label loginPassword = new Label("Password: ");
	TextField loginPasswordField = new TextField();
	GridPane.setConstraints(loginUsername, 0, 0);
	GridPane.setConstraints(loginUsernameField, 1, 0);
	GridPane.setConstraints(loginPassword, 0, 1);
	GridPane.setConstraints(loginPasswordField, 1, 1);
	loginGrid.getChildren().addAll(loginUsername, loginUsernameField, loginPassword,
			loginPasswordField);
	loginGrid.setAlignment(Pos.CENTER);
	Button tryLoginButton = new Button("Log in");
	tryLoginButton.setOnAction(e -> {
		int pos = -1;
		for (int i = 0; i < savedUsers.size(); i++) {
		    if (savedUsers.get(i).getUsername().equals(loginUsernameField.getText())) {
			pos = i;
			break;
		    }
		}
		if(pos != -1) {
		    customer = savedUsers.get(pos);
		    savedUsers.remove(pos);
		    loginUsernameField.clear();
		    loginPasswordField.clear();
		    window.setScene(mainScene);
		}
		else {
		    loginStatus.setText("User does not exist");
		}

	    });
	Button createUserButton = new Button("Create user");
	createUserButton.setOnAction(e-> {
		if(loginUsernameField.getText().length() != 0 && loginPasswordField.getText().length() != 0) {
		    boolean exists = false;
		    for (Customer c : savedUsers) {
			if (c.getUsername().equals(loginUsernameField.getText())) {
			    exists = true;
			    break;
			}
		    }
		    if(exists) {
			loginStatus.setText("User already exists");
		    }
		    else {
			customer.setUsername(loginUsernameField.getText());
			customer.setPassword(loginPasswordField.getText());
			loginUsernameField.clear();
			loginPasswordField.clear();
			window.setScene(mainScene);
		    }
		}
	    });
	Button toMenuFromLogin = new Button("Return to Menu");
	toMenuFromLogin.setOnAction(e -> window.setScene(mainScene));
	loginLayout.getChildren().addAll(loginStatus, loginGrid, tryLoginButton, createUserButton, toMenuFromLogin);
	loginLayout.setAlignment(Pos.CENTER);

	// On Close
	// save users again to file on close, save current user as well
	window.setOnCloseRequest(e -> {
		try {
		    FileWriter fileWriter = new FileWriter("users.csv");
		    String outData = "";
		    if(!customer.getUsername().equals("")){
			String line = customer.getUsername() + "," + customer.getPassword() + ",";
			for(MenuItem m : customer.getOrderItems()) {
			    String tmp = m.getFood() + ":" + m.getPrice() + ":" + m.getImagePath() + ":" + m.getTimeToCook() + ":" + m.getCategory() + ":";
			    for(String ing : m.getIngredientList()) {
				tmp += ing + ":";
			    }
			    tmp = tmp.substring(0, tmp.length() - 1);
			    line += tmp + ",";
			}
			line = line.substring(0, line.length() - 1);
			outData += line + "\n";
		    }
		    for(Customer c : savedUsers) {
			String line = c.getUsername() + "," + c.getPassword() + ",";
			for(MenuItem m : c.getOrderItems()) {
			    String tmp = m.getFood() + ":" + m.getPrice() + ":" + m.getImagePath() + ":" + m.getTimeToCook() + ":" + m.getCategory() + ":";
			    for(String ing : m.getIngredientList()) {
				tmp += ing + ":";
			    }
			    tmp = tmp.substring(0, tmp.length() - 1);
			    line += tmp + ",";
			}
			line = line.substring(0, line.length() - 1);
			outData += line + "\n";
		    }
		    outData = outData.substring(0, outData.length() - 1);
		    fileWriter.write(outData);
		    fileWriter.close();
		}
		catch (IOException ex) {
		    ex.printStackTrace();
		}
	    });

	// Finishing Window Settings
	window.setScene(mainScene);
	window.setTitle("Title of the Window");
	window.show();
    }

    public VBox orderList(Customer customer) {
	VBox orderList = new VBox(20);
	ArrayList<Button> removeButtons = new ArrayList<Button>();
	for(int i = 0; i < customer.getOrderItems().length; i++) {
	    MenuItem orderItem = customer.getOrderItems()[i];
	    // Layout prep
	    GridPane row = new GridPane();
	    // Column 0 and 3 serve as margins
	    // Column 1 contains item info, Column 2 contains buttons
	    ColumnConstraints column0 = new ColumnConstraints();
	    column0.setPercentWidth(20);
	    ColumnConstraints column1 = new ColumnConstraints();
	    column1.setPercentWidth(20);
	    ColumnConstraints column2 = new ColumnConstraints();
	    column2.setPercentWidth(20);
	    ColumnConstraints column3 = new ColumnConstraints();
	    column3.setPercentWidth(20);
	    ColumnConstraints column4 = new ColumnConstraints();
	    column4.setPercentWidth(20);
	    row.getColumnConstraints()
		.addAll(column0, column1, column2, column3, column4);
	    StackPane itemInfo = new StackPane();
	    StackPane itemPrice = new StackPane();

	    // Item prep
	    Label itemName = new Label(orderItem.getFood());
	    removeButtons.add(new Button("Remove"));
	    removeButtons.get(i).setOnAction(e -> {
		    // status.setText("Added item to the order");
		    customer.removeFromOrder(orderItem);
		    orderScrollPane.setContent(orderList(customer));
		});
	    StackPane buttonContainer = new StackPane();
	    buttonContainer.getChildren().add(removeButtons.get(i));

	    // Add Items to Layouts
	    itemInfo.getChildren().add(itemName);
	    itemPrice.getChildren().add(new Label("$" + orderItem.getPrice()));
	    GridPane.setConstraints(itemInfo, 1, 0);
	    GridPane.setConstraints(itemPrice, 2, 0);
	    GridPane.setConstraints(buttonContainer, 3, 0);
	    row.getChildren()
		.addAll(itemInfo, buttonContainer, itemPrice);
	    orderList.getChildren().add(row);
	}
	totalCost.setText("Total: $" + String.format("%.2f", customer.getOrderTotal()));
	timeToCook.setText("Expected Time to Cook: " + String.format("%.0f", customer.getOrderTimeToCook()) + " minutes.");
	customersAhead.setText("Customers Ahead: " + restaurant.getNumOfOrders());
	return orderList;
    }
}
