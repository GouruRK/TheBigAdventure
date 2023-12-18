package game.entity;

import game.GameObjectID;

public interface Entity {
  
  public abstract String skin();
  public abstract String name();
  
  public abstract GameObjectID id();
  
  /**
   * Put default name null
   * 
   * @return
   */
  public default boolean hasName() {
    return name() != null;
  }
  
}
