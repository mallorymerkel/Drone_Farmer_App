package components;

import java.util.ArrayList;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Component {

  private final StringProperty name;
  protected final IntegerProperty purchasePrice; // make visible to children
  private final IntegerProperty locationX;
  private final IntegerProperty locationY;
  private final IntegerProperty length;
  private final IntegerProperty width;
  private final IntegerProperty height;
  private Rectangle itemDraw;
  private Label itemLabel;

  public Component() {
    this("default", 0, 0, 0, 0, 0, 0);
  }

  public Component(String name, int purchasePrice, int locationX, int locationY, int length, int width, int height) {
    this.name = new SimpleStringProperty(name);
    this.purchasePrice = new SimpleIntegerProperty(purchasePrice);
    this.locationX = new SimpleIntegerProperty(locationX);
    this.locationY = new SimpleIntegerProperty(locationY);
    this.length = new SimpleIntegerProperty(length);
    this.width = new SimpleIntegerProperty(width);
    this.height = new SimpleIntegerProperty(height);
    this.itemDraw = new Rectangle();
    this.itemLabel = new Label();
    this.itemDraw.setFill(Color.WHITE);
    this.itemDraw.setStroke(Color.BLACK);
    this.itemDraw.setX((double) this.locationX.getValue());
    this.itemDraw.setY((double) this.locationY.getValue());
    this.itemDraw.setWidth((double) this.width.getValue());
    this.itemDraw.setHeight((double) this.length.getValue());
    this.itemLabel.setText(this.name.getValue());
    this.itemLabel.setLayoutX((double) this.locationX.getValue());
    this.itemLabel.setLayoutY((double) this.locationY.getValue() + (double) this.length.getValue());
  }

  // ABSTRACT METHODS

  public abstract void addComponent(Component component);

  public abstract void deleteComponent(Component component);

  public abstract boolean isContainer();

  public abstract ArrayList<Component> getCollection();

  public abstract int getMarketValue();

  public abstract void setMarketValue(int marketValue);

  // CONCRETE METHODS

  public void accept(Visitor visitor) {
    visitor.visit(this);
  }

  // Price
  public int getPrice() {
    return purchasePrice.get();
  }

  public void setPrice(int price) {
    this.purchasePrice.set(price);
  }

  public int getPurchasePrice() {
    return purchasePrice.get();
  }

  public void setPurchasePrice(int purchasePrice) {
    this.purchasePrice.set(purchasePrice);
  }

  // Rectangle
  public Rectangle getRectangle() {
    return this.itemDraw;
  }

  // Label
  public Label getLabel() {
    return this.itemLabel;
  }

  // Name
  public String getName() {
    return name.get();
  }

  public void setName(String name) {
    this.name.set(name);
    this.itemLabel.setText(name);
  }

  // LocationX
  public int getLocationX() {
    return locationX.get();
  }

  public void setLocationX(int locationX) {
    this.locationX.set(locationX);
    this.itemDraw.setX((double) locationX);
    this.itemLabel.setLayoutX((double) locationX);
  }

  // LocationY
  public int getLocationY() {
    return locationY.get();
  }

  public void setLocationY(int locationY) {
    this.locationY.set(locationY);
    this.itemDraw.setY((double) locationY);
    this.itemLabel.setLayoutY((double) locationY - 20);
  }

  // Length
  public int getLength() {
    return length.get();
  }

  public void setLength(int length) {
    this.length.set(length);
    this.itemDraw.setHeight((double) length);
  }

  // Width
  public int getWidth() {
    return width.get();
  }

  public void setWidth(int width) {
    this.width.set(width);
    this.itemDraw.setWidth((double) width);
  }

  // Height
  public int getHeight() {
    return height.get();
  }

  public void setHeight(int height) {
    this.height.set(height);
  }
}
