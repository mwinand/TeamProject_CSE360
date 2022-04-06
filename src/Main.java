import java.util.ArrayList;
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

public class Main extends Application {

    ScrollPane orderScrollPane = new ScrollPane();
    Label totalCost = new Label("$");
    Label timeToCook = new Label("");

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
	Stage window = primaryStage;
	Scene mainScene, searchScene, orderScene, paymentScene;
	Button searchButton, toMainFromSearchButton,
	    orderButton, toMainFromOrderButton, checkoutButton, payButton;
	Label title, status, paymentStatus;
	TextField paymentInfo, nameField, securityField, monthField, yearField;
	Label creditCardLabel, nameLabel, securityLabel, expirationLabel;

	Restaurant restaurant = new Restaurant("RestaurantName", "username", "password");
	Customer customer = new Customer();
	ArrayList<Button> menuButtons = new ArrayList<Button>();

	// food, price, path, ingredients, timecook, category
	restaurant.addToMenu
	    (new MenuItem("Hamburger", 10.99, "hamburger.jpg", new String[] {"Brioche Bun", "Angus Beef", "American Cheese"}, 10.0, "Main"));
	restaurant.addToMenu
	    (new MenuItem("Hotdog", 7.99, "hotdog.jpg", new String[] {"Bun", "Sausage"}, 5.0, "Main"));
	restaurant.addToMenu
	    (new MenuItem("Empanada", 8.29, "empanada.jpg", new String[] {"Corn", "Meat"}, 4.0, "Appetizer"));
	restaurant.addToMenu
	    (new MenuItem("Blueberry Pie", 5.69, "blueberryPie.jpg", new String[] {"Pie Crust, Blueberries"}, 1.0, "Dessert"));
	restaurant.addToMenu
	    (new MenuItem("Tacos", 6.99, "tacos.jpg", new String[] {"Hard Corn Shell", "Carne Asada", "Cheese"}, 4.0, "Main"));
	restaurant.addToMenu
	    (new MenuItem("Dumplings", 4.39, "dumplings.jpg", new String[] {"Dumpling"}, 3.0, "Appetizer"));

	mainScene = new Scene(mainLayout, 600, 600);
	searchScene = new Scene(searchLayout, 600, 600);
	orderScene = new Scene(orderLayout, 600, 600);
	paymentScene = new Scene(paymentLayout, 600, 600);

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
	StackPane orderButtonWrapper = new StackPane();
	orderButtonWrapper.getChildren().add(orderButton);
	mainLayout.setBottom(orderButtonWrapper);
	mainLayout.setMargin(orderButtonWrapper, new Insets(40));

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
	orderBottomLayout.getChildren().addAll(totalCost, timeToCook, orderButtonRow);
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
	return orderList;
    }
}
