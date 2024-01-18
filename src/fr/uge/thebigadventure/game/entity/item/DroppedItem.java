package fr.uge.thebigadventure.game.entity.item;

import java.util.Objects;

import fr.uge.thebigadventure.game.GameObjectID;
import fr.uge.thebigadventure.game.entity.Entity;
import fr.uge.thebigadventure.util.Position;

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
  
}
