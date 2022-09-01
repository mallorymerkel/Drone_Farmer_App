package view;

import components.Component;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ItemEditDialogController {
  @FXML
  private TextField nameField;
  @FXML
  private TextField priceField;
  @FXML
  private TextField marketValueField;
  @FXML
  private TextField xLocationField;
  @FXML
  private TextField yLocationField;
  @FXML
  private TextField lengthField;
  @FXML
  private TextField widthField;
  @FXML
  private TextField heightField;

  private Stage dialogStage;
  private Component itemComponent;
  private boolean okClicked = false;

  @FXML
  private void initialize() {
  }

  public void setDialogStage(Stage dialogStage) {
    this.dialogStage = dialogStage;
  }

  public void setItemComponent(Component itemComponent) {
    this.itemComponent = itemComponent;

    nameField.setText(itemComponent.getName());
    priceField.setText(Integer.toString(itemComponent.getPrice()));
    marketValueField.setText(Integer.toString(itemComponent.getMarketValue()));
    xLocationField.setText(Integer.toString(itemComponent.getLocationX()));
    yLocationField.setText(Integer.toString(itemComponent.getLocationY()));
    lengthField.setText(Integer.toString(itemComponent.getLength()));
    widthField.setText(Integer.toString(itemComponent.getWidth()));
    heightField.setText(Integer.toString(itemComponent.getHeight()));

    // Make Market Value non-editable for containers
    if (itemComponent.isContainer()) {
      marketValueField.setEditable(false);
      marketValueField.setText("N/A");
    }
  }

  public boolean isOkClicked() {
    return okClicked;
  }

  @FXML
  private void handleOk() {
    if (isInputValid()) {
      itemComponent.setName(nameField.getText());
      itemComponent.setPrice(Integer.parseInt(priceField.getText()));
      if (!itemComponent.isContainer()) {
        itemComponent.setMarketValue(Integer.parseInt(marketValueField.getText()));
      }
      itemComponent.setLocationX(Integer.parseInt(xLocationField.getText()));
      itemComponent.setLocationY(Integer.parseInt(yLocationField.getText()));
      itemComponent.setLength(Integer.parseInt(lengthField.getText()));
      itemComponent.setWidth(Integer.parseInt(widthField.getText()));
      itemComponent.setHeight(Integer.parseInt(heightField.getText()));

      okClicked = true;
      dialogStage.close();
    }
  }

  @FXML
  private void handleCancel() {
    dialogStage.close();
  }

  private boolean isInputValid() {
    String errorMessage = "";

    if (nameField.getText() == null || nameField.getText().length() == 0) {
      errorMessage += "No valid name!\n";
    }

    if (priceField.getText() == null || priceField.getText().length() == 0) {
      errorMessage += "No valid price!\n";
    } else {
      // try to parse the price into an int.
      try {
        Integer.parseInt(priceField.getText());
      } catch (NumberFormatException e) {
        errorMessage += "No valid price (must be an integer)!\n";
      }
    }

    if (!itemComponent.isContainer()) {
      if (marketValueField.getText() == null || marketValueField.getText().length() == 0) {
        errorMessage += "No valid marketValue!\n";
      } else {
        // try to parse the price into an int.
        try {
          Integer.parseInt(marketValueField.getText());
        } catch (NumberFormatException e) {
          errorMessage += "No valid Market Value (must be an integer)!\n";
        }
      }

      if (xLocationField.getText() == null || xLocationField.getText().length() == 0) {
        errorMessage += "No valid xLocation!\n";
      } else {
        try {
          Integer.parseInt(xLocationField.getText());
        } catch (NumberFormatException e) {
          errorMessage += "No valid xLocation (must be an integer)!\n";
        }
      }
    }

    if (yLocationField.getText() == null || yLocationField.getText().length() == 0) {
      errorMessage += "No valid yLocation!\n";
    } else {
      try {
        Integer.parseInt(yLocationField.getText());
      } catch (NumberFormatException e) {
        errorMessage += "No valid yLocation (must be an integer)!\n";
      }
    }

    if (lengthField.getText() == null || lengthField.getText().length() == 0) {
      errorMessage += "No valid length!\n";
    } else {
      try {
        Integer.parseInt(lengthField.getText());
      } catch (NumberFormatException e) {
        errorMessage += "No valid length (must be an integer)!\n";
      }
    }

    if (widthField.getText() == null || widthField.getText().length() == 0) {
      errorMessage += "No valid width!\n";
    } else {
      try {
        Integer.parseInt(widthField.getText());
      } catch (NumberFormatException e) {
        errorMessage += "No valid width (must be an integer)!\n";
      }
    }

    if (heightField.getText() == null || heightField.getText().length() == 0) {
      errorMessage += "No valid height!\n";
    } else {
      try {
        Integer.parseInt(heightField.getText());
      } catch (NumberFormatException e) {
        errorMessage += "No valid height (must be an integer)!\n";
      }
    }

    if (errorMessage.length() == 0) {
      return true;
    } else {
      // Show the error message.
      Alert alert = new Alert(AlertType.ERROR);
      alert.initOwner(dialogStage);
      alert.setTitle("Invalid Fields");
      alert.setHeaderText("Please correct invalid fields");
      alert.setContentText(errorMessage);

      alert.showAndWait();

      return false;
    }
  }
}
