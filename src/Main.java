import java.util.ArrayList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.GridPane;
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

    public static void main(String[] args) {
	launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
	// Preparation
	BorderPane mainLayout = new BorderPane();
	ScrollPane scrollPane = new ScrollPane();
	VBox titleLayout = new VBox(10);
	VBox menuLayout = new VBox(20);
	VBox searchLayout = new VBox(40);
	VBox orderLayout = new VBox(20);
	Stage window = primaryStage;
	Scene mainScene, searchScene, orderScene;
	Button searchButton, toMainFromSearchButton,
	    orderButton, toMainFromOrderButton;
	Label title, status;

	ObservableList<MenuItem> menuItems = FXCollections.observableArrayList();
	ArrayList<Image> images = new ArrayList<Image>();
	ArrayList<ImageView> imageView = new ArrayList<ImageView>();
	ArrayList<Button> imageButtons = new ArrayList<Button>();

	// food, price, path, ingredients, timecook, category
	menuItems.add
	    (new MenuItem("Hamburger", 10.99, "hamburger.jpg", new String[] {"Brioche Bun", "Angus Beef", "American Cheese"}, 10.0, "Main"));
	menuItems.add
	    (new MenuItem("Hotdog", 7.99, "hotdog.jpg", new String[] {"Bun", "Sausage"}, 5.0, "Main"));
	menuItems.add
	    (new MenuItem("Empanada", 8.29, "empanada.jpg", new String[] {"Corn", "Meat"}, 4.0, "Appetizer"));
	menuItems.add
	    (new MenuItem("Blueberry Pie", 5.69, "blueberryPie.jpg", new String[] {"Pie Crust, Blueberries"}, 1.0, "Dessert"));
	menuItems.add
	    (new MenuItem("Tacos", 6.99, "tacos.jpg", new String[] {"Hard Corn Shell", "Carne Asada", "Cheese"}, 4.0, "Main"));
	menuItems.add
	    (new MenuItem("Dumplings", 4.39, "dumplings.jpg", new String[] {"Dumpling"}, 3.0, "Appetizer"));

	mainScene = new Scene(mainLayout, 600, 600);
	searchScene = new Scene(searchLayout, 600, 600);
	orderScene = new Scene(orderLayout, 600, 600);

	// Main Scene

	// Title
	searchButton = new Button("Search");
	searchButton.setOnAction(e -> window.setScene(searchScene));
	title = new Label("RestaurantName");
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
	for(int i = 0; i < menuItems.size(); i++) {
	    images.add
		(new Image(getClass().getResourceAsStream(menuItems.get(i).getImagePath())));
	    imageView.add(new ImageView(images.get(i)));
	    imageView.get(i).setFitWidth(100);
	    imageView.get(i).setPreserveRatio(true);

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
	    Label itemName = new Label(menuItems.get(i).getFood());
	    String description = "";
	    for(String ing : menuItems.get(i).getIngredientList()) {
		description += ing;
		description += ", ";
	    }
	    description = description.substring(0, description.length() - 2);
	    Label itemIng = new Label(description);
	    imageButtons.add(new Button("Add"));
	    imageButtons.get(i).setOnAction(e -> {
		    status.setText("Added item to the order");
			});
	    StackPane buttonContainer = new StackPane();
	    buttonContainer.getChildren().add(imageButtons.get(i));

	    // Add Items to Layouts
	    itemInfo.getChildren().addAll(itemName, itemIng);
	    imageContainer.getChildren().add(imageView.get(i));
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
	orderButton.setOnAction(e -> window.setScene(orderScene));
	StackPane orderButtonWrapper = new StackPane();
	orderButtonWrapper.getChildren().add(orderButton);
	mainLayout.setBottom(orderButtonWrapper);
	mainLayout.setMargin(orderButtonWrapper, new Insets(20));

	// Search Scene

	TextField filterField = new TextField();
	TableView<MenuItem> table = new TableView<MenuItem>(menuItems);
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
	    new FilteredList<MenuItem>(menuItems, p -> true);

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
	toMainFromOrderButton = new Button("Return to Menu");
	toMainFromOrderButton.setOnAction(e -> window.setScene(mainScene));
	orderLayout.setAlignment(Pos.CENTER);
	orderLayout.getChildren().addAll(toMainFromOrderButton);
	orderLayout.setMargin(filterField, new Insets(0, 80, 0, 80));
	orderLayout.setMargin(table, new Insets(0, 40, 0, 40));

	// Finishing Window Settings
	window.setScene(mainScene);
	window.setTitle("Title of the Window");
	window.show();
    }
}
