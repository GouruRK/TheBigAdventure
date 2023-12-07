package game.entity;

import game.GameObjectID;

public interface Entity {
  
  public abstract String skin();
  public abstract String name();
  
  public abstract GameObjectID getID();
  
  public default boolean hasName() {
    return name() != null;
  }
  
}
