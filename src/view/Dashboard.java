package view;

import application.Main;
import components.Component;
import components.Item;
import components.ItemContainer;
import components.MarketValueVisitor;
import components.PurchasePriceVisitor;
import components.Visitor;
import components.AnimatedDrone;
import components.PhysicalDroneAdapter;

import java.io.FileInputStream;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class Dashboard {

  @FXML
  private ChoiceBox<String> actionChoiceBox;
  @FXML
  private Button launchDroneButton;
  @FXML
  private BorderPane itemTreeBorder;
  @FXML
  private AnchorPane mapPane;

  @FXML
  private TreeTableView<Component> itemComponentTree;
  @FXML
  private TreeTableColumn<Component, String> nameColumn;

  @FXML
  private Label marketValue;
  @FXML
  private Label purchasePrice;

  private ImageView drone;
  private Group rectangles;
  private Group labels;
  private int max_height = 0;

  ItemContainer collectionContainer = new ItemContainer();

  private Main main;

  private static Dashboard instance;

  /* public constructor for controller class with JavaFX */
  public Dashboard() {
  }

  /* Singleton dashboard to ensure only one instance */
  public static Dashboard getInstance() {
    if (instance == null) {
      instance = new Dashboard();
    }
    return instance;
  }

  @FXML
  private void initialize() {

    actionChoiceBox.getItems().addAll("Visit Selection", "Scan Farm");
    actionChoiceBox.setValue("Scan Farm");
    nameColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("name")); // populate name col

  }

  private void setDrone() {
    try {
      this.drone = new ImageView(new Image(new FileInputStream("resources/drone.png")));
      this.drone.setFitHeight(50);
      this.drone.setFitWidth(50);
      this.drone.setPickOnBounds(true);
      this.drone.setPreserveRatio(true);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /* Connect Dashboard with Main */
  public void setMain(Main main) {
    this.main = main;
    itemComponentTree.setRoot(main.getItemComponents());

    // Set root (map) dimensions
    itemComponentTree.getRoot().getValue().setLength((int) (mapPane.getMinHeight()));
    itemComponentTree.getRoot().getValue().setWidth((int) (mapPane.getMinWidth()));

    itemComponentTree.setOnMouseClicked(new EventHandler<Event>() {
      @Override
      public void handle(Event event) {
        visitValueLabels();
      }
    });

    // Gather images
    this.rectangles = this.getRectangleGroup();
    this.labels = this.getLabelGroup();
    this.setDrone();

    // Add images to map
    this.mapPane.getChildren().add(this.rectangles);
    this.mapPane.getChildren().add(this.labels);
    this.mapPane.getChildren().add(this.drone);
  }

  /* Set price & market-value labels */
  private void visitValueLabels() {
    Component component = itemComponentTree.getSelectionModel().getSelectedItem().getValue();

    Visitor pp_visitor = new PurchasePriceVisitor();
    Visitor mv_visitor = new MarketValueVisitor();

    component.accept(pp_visitor);
    component.accept(mv_visitor);

    purchasePrice.setText("Purchase Price: " + pp_visitor.value());
    marketValue.setText("Market Value: " + mv_visitor.value());
  }

  /* Re-calculate all rectangles */
  private Group getRectangleGroup() {
    this.rectangles = new Group();
    getRectangles(itemComponentTree.getRoot());
    return this.rectangles;
  }

  /* Re-calculate all labels */
  private Group getLabelGroup() {
    this.labels = new Group();
    getLabels(itemComponentTree.getRoot());
    return this.labels;
  }

  /* Helper function that recursively finds all rectangles */
  private void getRectangles(TreeItem<Component> root) {
    this.rectangles.getChildren().add(root.getValue().getRectangle());
    for (TreeItem<Component> child : root.getChildren()) {
      if (child.getChildren().isEmpty()) {
        this.rectangles.getChildren().add(child.getValue().getRectangle());
      } else {
        getRectangles(child);
      }
    }
  }

  /* Helper function that recursively finds all labels */
  private void getLabels(TreeItem<Component> root) {
    this.labels.getChildren().add(root.getValue().getLabel());
    for (TreeItem<Component> child : root.getChildren()) {
      if (child.getChildren().isEmpty()) {
        this.labels.getChildren().add(child.getValue().getLabel());
      } else {
        getLabels(child);
      }
    }
  }

  /* Helper function to calculate max height of all items for physical drone */
  private void getMaxHeight(TreeItem<Component> root) {
    if (root.getValue().getHeight() > max_height) {
      max_height = root.getValue().getHeight();
    }
    for (TreeItem<Component> child : root.getChildren()) {
      if (child.getChildren().isEmpty()) {
        if (child.getValue().getHeight() > max_height) {
          max_height = child.getValue().getHeight();
        }
      } else {
        getMaxHeight(child);
      }
    }
  }

  /*
   * Helper function to delete a component and all children from flat master-list
   * of nodes
   */
  private void deleteAllDescendants(TreeItem<Component> parent) {
    this.collectionContainer.deleteComponent(parent.getValue());
    for (TreeItem<Component> child : parent.getChildren()) {
      if (child.getChildren().isEmpty()) {
        this.collectionContainer.deleteComponent(child.getValue());
      } else {
        getLabels(child);
      }
    }
  }

  @FXML
  private void refreshMap() {
    this.rectangles = this.getRectangleGroup();
    this.labels = this.getLabelGroup();
    this.mapPane.getChildren().setAll(this.rectangles.getChildren());
    this.mapPane.getChildren().addAll(this.labels.getChildren());
    this.mapPane.getChildren().add(this.drone);

  }

  @FXML
  private void launchDrone() {
    AnimatedDrone animatedDrone = new AnimatedDrone(this.drone);
    PhysicalDroneAdapter physicalDrone = new PhysicalDroneAdapter();

    getMaxHeight(itemComponentTree.getRoot());
    physicalDrone.setDroneHeight(max_height);

    if (this.actionChoiceBox.getValue() == "Scan Farm") {

      animatedDrone.scanFarm();
      physicalDrone.scanFarm();

    } else if (this.actionChoiceBox.getValue() == "Visit Selection") {
      TreeItem<Component> selected = (TreeItem<Component>) itemComponentTree.getSelectionModel().getSelectedItem();
      if (selected != null && selected.getParent() != null) {

        animatedDrone.gotoComponent(selected.getValue());
        physicalDrone.gotoComponent(selected.getValue());

      } else if (selected == null) {
        noSelectionAlert();
      } else {
        cannotEditRootAlert();
      }
    }
  }

  @FXML
  private void launchAnimation() {
    AnimatedDrone animatedDrone = new AnimatedDrone(this.drone);
    if (this.actionChoiceBox.getValue() == "Scan Farm") {
      animatedDrone.scanFarm();
    } else if (this.actionChoiceBox.getValue() == "Visit Selection") {
      TreeItem<Component> selected = (TreeItem<Component>) itemComponentTree.getSelectionModel().getSelectedItem();
      if (selected != null && selected.getParent() != null) {
        animatedDrone.gotoComponent(selected.getValue());
      } else if (selected == null) {
        noSelectionAlert();
      } else {
        cannotEditRootAlert();
      }
    }
  }

  /* Delete item/item-container */
  @FXML
  private void handleDeleteItemComponent() {
    // Get selected item
    TreeItem<Component> selected = (TreeItem<Component>) itemComponentTree.getSelectionModel().getSelectedItem();

    // Check that an item is selected and that it is not the root
    if (selected != null && selected.getParent() != null) {
      deleteAllDescendants(selected); // Remove from list
      selected.getParent().getValue().deleteComponent(selected.getValue()); // Remove from parent
      selected.getParent().getChildren().remove(selected); // Remove from tree

      refreshMap();

    } else {
      // Throw error if nothing selected
      if (selected == null) {
        noSelectionAlert();
      }
      // Throw error if selected item is root
      else {
        cannotEditRootAlert();
      }
    }
  }

  /* Edit item/item-container */
  @FXML
  private void handleEditItemComponent() {
    TreeItem<Component> selected = (TreeItem<Component>) itemComponentTree.getSelectionModel().getSelectedItem();

    if (selected != null && selected.getParent() != null) {
      Component tempItem = selected.getValue();
      boolean okClicked = main.showItemEditDialog(tempItem);

      if (okClicked) {
        collectionContainer.deleteComponent(selected.getValue()); // Remove (old) from list
        selected.getParent().getValue().deleteComponent(selected.getValue()); // Remove (old) from parent
        selected.setValue(tempItem); // Reassign tree value
        collectionContainer.addComponent(tempItem); // Add (new) to list
        selected.getParent().getValue().addComponent(tempItem); // Add (new) to parent

        refreshMap();

      }
    } else {
      if (selected == null) {
        noSelectionAlert();
      } else {
        cannotEditRootAlert();
      }
    }
  }

  /* Add new item to parent container */
  @FXML
  private void handleNewItem() {
    TreeItem<Component> selected = (TreeItem<Component>) itemComponentTree.getSelectionModel().getSelectedItem();

    if (selected != null && selected.getValue() instanceof ItemContainer) {
      Component parent = selected.getValue();
      Component tempItem = new Item("New Item", 0, 0, parent.getLocationX(), parent.getLocationY(),
          parent.getLength() / 4, parent.getWidth() / 4, parent.getHeight() / 4);
      boolean okClicked = main.showItemEditDialog(tempItem);

      if (okClicked) {
        String name = tempItem.getName();
        tempItem.setName(name + " (Item)"); // Add (Item) to end of name
        TreeItem<Component> tempTreeItem = new TreeItem<Component>(tempItem);

        collectionContainer.addComponent(tempItem); // add to list
        selected.getValue().addComponent(tempItem); // add to parent
        selected.getChildren().add(tempTreeItem); // add to tree

        refreshMap();
      }
    } else {
      if (selected == null) {
        noSelectionAlert();
      } else {
        cannotAddItemAlert();
      }
    }
  }

  /* Add new item container to parent container */
  @FXML
  private void handleNewItemContainer() {
    TreeItem<Component> selected = (TreeItem<Component>) itemComponentTree.getSelectionModel().getSelectedItem();

    if (selected != null && selected.getValue() instanceof ItemContainer) {
      Component parent = selected.getValue();
      Component tempItem = new ItemContainer("New Container", 0, parent.getLocationX(), parent.getLocationY(),
          parent.getLength() / 4, parent.getWidth() / 4, parent.getHeight() / 4);
      boolean okClicked = main.showItemEditDialog(tempItem);

      if (okClicked) {
        String name = tempItem.getName();
        tempItem.setName(name + " (Container)");
        TreeItem<Component> tempTreeItem = new TreeItem<Component>(tempItem);

        collectionContainer.addComponent(tempItem); // add to list
        selected.getValue().addComponent(tempItem); // add to parent
        selected.getChildren().add(tempTreeItem); // add to tree

        refreshMap();

      }
    } else {
      if (selected == null) {
        noSelectionAlert();
      } else {
        cannotAddItemAlert();
      }
    }
  }

  /* View item/item-container */
  @FXML
  private void handleViewItemComponent() {
    TreeItem<Component> selected = (TreeItem<Component>) itemComponentTree.getSelectionModel().getSelectedItem();

    if (selected != null) {
      Component tempItem = selected.getValue();
      main.showItemView(tempItem);
    } else {
      noSelectionAlert();
    }
  }

  /* Must choose container to interact with */
  private void noSelectionAlert() {
    Alert alert = new Alert(AlertType.WARNING);
    alert.initOwner(main.getPrimaryStage());
    alert.setTitle("No Selection");
    alert.setHeaderText("No Item Selected");
    alert.setContentText("Please select an Item in the tree.");

    alert.showAndWait();
  }

  /* Nothing can be added to an item */
  private void cannotAddItemAlert() {
    Alert alert = new Alert(AlertType.WARNING);
    alert.initOwner(main.getPrimaryStage());
    alert.setTitle("Cannot Add Item");
    alert.setHeaderText("Cannot Add To Items");
    alert.setContentText("Please select a Container in the Tree.");

    alert.showAndWait();
  }

  /* Root cannot be altered */
  private void cannotEditRootAlert() {
    Alert alert = new Alert(AlertType.WARNING);
    alert.initOwner(main.getPrimaryStage());
    alert.setTitle("Cannot Edit Root");
    alert.setHeaderText("Cannot Edit Root");
    alert.setContentText("You cannot edit the root item container (Farm)");

    alert.showAndWait();
  }
}
