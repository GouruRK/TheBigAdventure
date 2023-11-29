package game.entity.item;

import game.GameObjectID;
import game.entity.Entity;
import game.entity.item.container.EmptyContainer;
import parser.EncodingRow;

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
  
}
