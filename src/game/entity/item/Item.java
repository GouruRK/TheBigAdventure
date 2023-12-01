package game.entity.item;

import game.GameObjectID;
import game.entity.Entity;
import game.entity.item.container.EmptyContainer;
import parser.ElementAttributes;
import parser.EncodingRow;
import util.Position;

public interface Item extends Entity {
  
  public static Item createItem(EncodingRow row) {
    return switch(row.id()) {
    case GameObjectID.FOOD -> new Food(row.skin());
    case GameObjectID.THING -> new Thing(row.skin());
    default -> null;
    };
  }
  
  public static Item createItem(String skin) {
    return Item.createItem(skin, null);
  }
  
  public static Item createItem(String skin, String name) {
    GameObjectID id = GameItems.getId(skin);
    return switch (id) {
    case GameObjectID.FOOD -> new Food(skin, name);
    case GameObjectID.THING -> new Thing(skin, name);
    case GameObjectID.WEAPON -> new Weapon(skin, name);
    case GameObjectID.CONTAINER -> new EmptyContainer(skin, name);
    default -> null;
    };
  }
  
  public static DroppedItem createDroppedItem(EncodingRow row, Position pos) {
    return switch (row.id()) {
      case GameObjectID.THING,
           GameObjectID.FOOD -> new DroppedItem(pos, Item.createItem(row));
    default -> null;
    };
  }
  
  public static DroppedItem createDroppedItem(ElementAttributes elem) {
    return switch (elem.getID()) {
    case GameObjectID.THING,
         GameObjectID.FOOD,
         GameObjectID.CONTAINER -> new DroppedItem(elem.getPosition(), Item.createItem(elem.getSkin(), elem.getName()));
    case GameObjectID.WEAPON -> new DroppedItem(elem.getPosition(), new Weapon(elem.getSkin(), elem.getDamage(), false, elem.getName()));
    case GameObjectID.READABLE -> new DroppedItem(elem.getPosition(), new Readable(elem.getSkin(), elem.getText(), elem.getName()));
    default -> null;
    };
  }
  
}
