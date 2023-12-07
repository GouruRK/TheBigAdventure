package game.entity.item.container;

import game.GameObjectID;
import game.entity.item.Item;

public interface Container extends Item {

  public default boolean isEmpty() {
    return false;
  }
  
  public default GameObjectID getID() {
    return GameObjectID.CONTAINER;
  }
  
}
