package components;

public class MarketValueVisitor extends Visitor {
  private int total;

  public MarketValueVisitor() {
    total = 0;
  }

  public int value() {
    return total;
  }

  /* Get component value, or children value, if container */
  public void visit(Component selected) {
    if (selected instanceof Item) { // only items
      total += selected.getMarketValue();
    } else if (selected instanceof ItemContainer) {
      for (Component childComponent : selected.getCollection()) {
        visit(childComponent);
      }
    }
  }

}
