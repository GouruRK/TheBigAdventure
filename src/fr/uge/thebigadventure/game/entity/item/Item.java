package fr.uge.thebigadventure.game.entity.item;

import java.util.Objects;

import fr.uge.thebigadventure.game.GameObjectID;
import fr.uge.thebigadventure.game.entity.Entity;
import fr.uge.thebigadventure.parser.ElementAttributes;
import fr.uge.thebigadventure.parser.EncodingRow;
import fr.uge.thebigadventure.util.Position;

public interface Item extends Entity {
  
  //------- Default -------
  
  public default int damage() {
    return 0;
  }

  public static Item setFire(Item item) {
    Objects.requireNonNull(item);
    return switch(item) {
      case Weapon weapon -> weapon.setFire();
      default -> item;
    };
  }
  
  // ------- Item creation -------
  
  /**
   * Create an Item from the EncodingRow
   * 
   * @param row
   * @return Item
   */
  public static Item createItem(EncodingRow row) {
    return switch(row.id()) {
    case GameObjectID.FOOD -> new Food(row.skin());
    case GameObjectID.THING -> new Thing(row.skin());
    case GameObjectID.WEAPON -> new Weapon(row.skin());
    case GameObjectID.CONTAINER -> new Bucket(row.skin());
    default -> null;
    };
  }
  
  /**
   * Create an Item
   * 
   * @param skin
   * @return Item
   */
  public static Item createItem(String skin) {
    Objects.requireNonNull(skin);
    return Item.createItem(skin, null);
  }
  
  
  /**
   * Create an Item from skin and name
   * 
   * @param skin
   * @param name
   * @return Item
   */
  public static Item createItem(String skin, String name) {
    Objects.requireNonNull(skin);
    // no check on name because item can have no name
    GameObjectID id = GameItems.getId(skin);
    return switch (id) {
    case GameObjectID.FOOD -> new Food(skin, name);
    case GameObjectID.THING -> new Thing(skin, name);
    case GameObjectID.WEAPON -> new Weapon(skin, name);
    case GameObjectID.CONTAINER -> new Bucket(skin, name);
    case GameObjectID.READABLE -> new Readable(skin, name);
    default -> null;
    };
  }
  
  /**
   * return a DroppedItem if in the EencodingRow
   * 
   * @param row
   * @param pos
   * @return DroppedItem
   */
  public static DroppedItem createDroppedItem(EncodingRow row, Position pos) {
    Objects.requireNonNull(row);
    Objects.requireNonNull(pos);
    return switch (row.id()) {
      case GameObjectID.THING,
           GameObjectID.WEAPON, 
           GameObjectID.CONTAINER,
           GameObjectID.FOOD -> new DroppedItem(pos, Item.createItem(row));
    default -> null;
    };
  }
  
  /**
   * Create a DroppedItem according to different GameObjectID 
   * 
   * @param elem
   * @return DroppedItem
   */
  public static DroppedItem createDroppedItem(ElementAttributes elem) {
    Objects.requireNonNull(elem);
    return switch (elem.getID()) {
    case GameObjectID.THING,
         GameObjectID.FOOD,
         GameObjectID.CONTAINER -> new DroppedItem(elem.getPosition(), Item.createItem(elem.getSkin(), elem.getName()));
    case GameObjectID.WEAPON -> new DroppedItem(elem.getPosition(), new Weapon(elem.getSkin(), elem.getDamage(), false, elem.getName()));
    case GameObjectID.READABLE -> new DroppedItem(elem.getPosition(), new Readable(elem.getSkin(), elem.getName(), elem.getText()));
    default -> null;
    };
  }
  
  //------- Other -------
  
  public default GameItems getItem() {
    return GameItems.getItem(skin());
  }
  
  public static GameItems getItem(String skin) {
    Objects.requireNonNull(skin);
    return GameItems.getItem(skin);
  }
  
}
