package components;

import java.util.ArrayList;

public class ItemContainer extends Component {

  private ArrayList<Component> componentsCollection = new ArrayList<Component>();

  public ItemContainer() {
    this("default", 0, 0, 0, 0, 0, 0);
  }

  public ItemContainer(String name, int price, int locationX, int locationY, int length, int width, int height) {
    super(name, price, locationX, locationY, length, width, height);
  }

  public ArrayList<Component> getCollection() {
    return componentsCollection;
  }

  public void addComponent(Component component) {
    componentsCollection.add(component);
  }

  public void deleteComponent(Component component) {
    componentsCollection.remove(component);
  }

  public int getMarketValue() {
    return 0;
  }

  public void setMarketValue(int marketValue) {
    System.out.println("Warning: Cannot assign Market Value to Container");
  }

  public boolean isContainer() {
    return true;
  }
}
