package components;

import java.util.ArrayList;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Item extends Component {
  private final IntegerProperty marketValue;

  public Item() {
    this("default", 0, 0, 0, 0, 0, 0, 0);
  }

  public Item(String name, int price, int marketValue, int locationX, int locationY, int length, int width,
      int height) {
    super(name, price, locationX, locationY, length, width, height);
    this.marketValue = new SimpleIntegerProperty(marketValue);
  }

  public int getMarketValue() {
    return marketValue.get();
  }

  public void setMarketValue(int marketValue) {
    this.marketValue.set(marketValue);
  }

  public void addComponent(Component component) {
    System.out.println("Items cannot contain other items");
  }

  public void deleteComponent(Component component) {
    System.out.println("Items cannot contain other items");
  }

  public boolean isContainer() {
    return false;
  }

  @Override
  public ArrayList<Component> getCollection() {
    System.out.println("Only Containers contain collections!");
    return null;
  }
}
