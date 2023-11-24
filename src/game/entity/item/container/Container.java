package game.entity.item.container;

import game.entity.item.Item;

public interface Container extends Item {

  public default boolean isEmpty() {
    return false;
  }
  
}
