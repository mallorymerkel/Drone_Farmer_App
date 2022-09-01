package components;

public abstract class Visitor {

  public abstract int value();

  public abstract void visit(Component component);
}
