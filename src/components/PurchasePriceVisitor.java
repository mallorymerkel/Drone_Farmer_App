package components;

public class PurchasePriceVisitor extends Visitor {
  private int total;

  public PurchasePriceVisitor() {
    total = 0;
  }

  public int value() {
    return total;
  }

  /* Get component price, and children value, if container */
  public void visit(Component selected) {
    total += selected.getPurchasePrice();
    if (selected instanceof ItemContainer) {
      for (Component childComponent : selected.getCollection()) {
        visit(childComponent);
      }
    }
  }

}
