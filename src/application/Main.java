package application;

import application.Main;
import components.Component;
import components.ItemContainer;
import java.io.FileInputStream;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import view.Dashboard;
import view.ItemEditDialogController;
import view.ItemViewController;

public class Main extends Application {

  private Stage primaryStage;
  private AnchorPane dashboardPane;

  private TreeItem<Component> root = new TreeItem<Component>(
      new ItemContainer("Farm (Container)", 0, 0, 0, 600, 800, 0));

  public Main() {
  }

  // Set root
  public TreeItem<Component> getItemComponents() {
    return this.root;
  }

  @Override
  public void start(Stage primaryStage) {
    // Load view
    this.primaryStage = primaryStage;
    this.primaryStage.setTitle("Cool Drone Thing");

    initDashboard();
  }

  public void initDashboard() {
    try {

      // Load dashboard layout
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(Main.class.getResource("../view/Dashboard.fxml"));
      dashboardPane = (AnchorPane) loader.load();

      // Set controller (singleton)
      Dashboard dashboardController = loader.getController();
      dashboardController.setMain(this);

      // Visualize
      Scene scene = new Scene(dashboardPane);
      primaryStage.setScene(scene);
      primaryStage.getIcons().add(new Image(new FileInputStream("resources/barn.png")));
      primaryStage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public boolean showItemEditDialog(Component itemComponent) {
    try {
      // Load the fxml file and create a new stage for the popup dialog.
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(Main.class.getResource("../view/ItemEditDialog.fxml"));
      AnchorPane page = (AnchorPane) loader.load();

      // Create the dialog Stage.
      Stage dialogStage = new Stage();
      dialogStage.setTitle("Add/Edit");
      dialogStage.initModality(Modality.WINDOW_MODAL);
      dialogStage.initOwner(primaryStage);
      Scene scene = new Scene(page);
      dialogStage.setScene(scene);

      // Set the item into the controller.
      ItemEditDialogController controller = loader.getController();
      controller.setDialogStage(dialogStage);
      controller.setItemComponent(itemComponent);

      // Show the dialog and wait until the user closes it
      dialogStage.showAndWait();

      return controller.isOkClicked();
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean showItemView(Component itemComponent) {
    try {
      // Load the fxml file and create a new stage for the popup dialog.
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(Main.class.getResource("../view/ItemView.fxml"));
      AnchorPane page = (AnchorPane) loader.load();

      // Create the dialog Stage.
      Stage dialogStage = new Stage();
      dialogStage.setTitle("View Item");
      dialogStage.initModality(Modality.WINDOW_MODAL);
      dialogStage.initOwner(primaryStage);
      Scene scene = new Scene(page);
      dialogStage.setScene(scene);

      // Set the item into the controller.
      ItemViewController controller = loader.getController();
      controller.setDialogStage(dialogStage);
      controller.setItemComponent(itemComponent);

      // Show the dialog and wait until the user closes it
      dialogStage.showAndWait();

      return controller.isOkClicked();
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

  public Stage getPrimaryStage() {
    return primaryStage;
  }

  public static void main(String[] args) {
    launch(args);
  }
}
