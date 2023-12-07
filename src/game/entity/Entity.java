package game.entity;

import game.GameObjectID;

public interface Entity {
  
  public abstract String skin();
  public abstract String name();
  
  public abstract GameObjectID id();
  
  public default boolean hasName() {
    return name() != null;
  }
  
}
