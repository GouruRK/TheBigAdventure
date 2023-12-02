package game.entity;

public interface Entity {
  
  public abstract String skin();
  public abstract String name();
  
  public default boolean hasName() {
    return name() != null;
  }
  
}
