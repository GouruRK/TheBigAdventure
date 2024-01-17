package fr.uge.thebigadventure.game.entity;

import fr.uge.thebigadventure.game.GameObjectID;

public interface Entity {
  
  //------- Getter -------
  
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
