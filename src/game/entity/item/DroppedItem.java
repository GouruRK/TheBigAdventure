package game.entity.item;

import java.util.Objects;

import game.GameObjectID;
import game.entity.Entity;
import util.Position;

public record DroppedItem(Position pos, Item item) implements Entity {

  public DroppedItem {
    Objects.requireNonNull(pos);
    Objects.requireNonNull(item);
  }

  public String skin() {
    return item.skin();
  }

  public String name() {
    return item.name();
  }
  
  public GameObjectID id() {
    return item.id();
  }
  
  
}
