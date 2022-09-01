package view;

import components.Component;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ItemViewController {
  @FXML
  private Label nameField;
  @FXML
  private Label priceField;
  @FXML
  private Label marketValueField;
  @FXML
  private Label xLocationField;
  @FXML
  private Label yLocationField;
  @FXML
  private Label lengthField;
  @FXML
  private Label widthField;
  @FXML
  private Label heightField;

  private Stage dialogStage;
  private boolean okClicked = false;

  @FXML
  private void initialize() {
  }

  public void setDialogStage(Stage dialogStage) {
    this.dialogStage = dialogStage;
  }

  public void setItemComponent(Component itemComponent) {
    nameField.setText(itemComponent.getName());
    priceField.setText(Integer.toString(itemComponent.getPrice()));
    if (itemComponent.isContainer()) {
      marketValueField.setText("N/A");
    } else {
      marketValueField.setText(Integer.toString(itemComponent.getMarketValue()));
    }
    xLocationField.setText(Integer.toString(itemComponent.getLocationX()));
    yLocationField.setText(Integer.toString(itemComponent.getLocationY()));
    lengthField.setText(Integer.toString(itemComponent.getLength()));
    widthField.setText(Integer.toString(itemComponent.getWidth()));
    heightField.setText(Integer.toString(itemComponent.getHeight()));
  }

  public boolean isOkClicked() {
    return okClicked;
  }

  @FXML
  private void handleClose() {
    this.okClicked = true;
    dialogStage.close();
  }
}
