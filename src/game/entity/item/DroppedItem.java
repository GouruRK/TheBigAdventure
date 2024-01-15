package game.entity.item;

import java.util.Objects;

import game.GameObjectID;
import game.entity.Entity;
import util.Position;

public record DroppedItem(Position pos, Item item) implements Entity {

  // ------- Constructor -------
  
  public DroppedItem {
    Objects.requireNonNull(pos);
    Objects.requireNonNull(item);
  }

  // ------- Getter -------
  
  @Override
  public String skin() {
    return item.skin();
  }

  @Override
  public String name() {
    return item.name();
  }
  
  @Override
  public GameObjectID id() {
    return item.id();
  }
  
  // ------- Other ------
  
  @Override
  public boolean equals(Object obj) {
    return obj instanceof DroppedItem dItem
        && dItem.item().equals(item)
        && dItem.pos().equals(pos)
        && dItem.hashCode() == hashCode();
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(pos, item);
  }
  
}
