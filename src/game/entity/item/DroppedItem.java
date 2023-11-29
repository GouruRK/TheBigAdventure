package game.entity.item;

import java.util.Objects;

import game.entity.Entity;
import util.Position;

public record DroppedItem(Position pos, Item item) implements Entity {

  public DroppedItem {
    Objects.requireNonNull(pos);
    Objects.requireNonNull(item);
  }

  @Override
  public String skin() {
    return item.skin();
  }

  @Override
  public String name() {
    return item.name();
  }
  
}
