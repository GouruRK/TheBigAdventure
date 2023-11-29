package game.entity;

import game.GameObjectID;
import game.entity.item.DroppedItem;
import game.entity.item.Item;
import parser.EncodingRow;
import util.Position;

public interface Entity {
  
  public abstract String skin();
  public abstract String name();
  
  public default boolean hasName() {
    return name() != null;
  }
  
  public static Entity createEntity(EncodingRow row, Position pos) {
    return switch (row.id()) {
      case GameObjectID.THING,
           GameObjectID.FOOD -> new DroppedItem(pos, Item.createItem(row));
    default -> null;
    };
  }
  
}
