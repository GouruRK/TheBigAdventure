package fr.uge.thebigadventure.game.environment;

import fr.uge.thebigadventure.game.GameObjectID;
import fr.uge.thebigadventure.parser.ElementAttributes;
import fr.uge.thebigadventure.parser.EncodingRow;
import fr.uge.thebigadventure.util.Position;

public interface Environment {

  //------- Getters -------
  
  public abstract String skin();
  public abstract Position pos();
  public abstract GameObjectID id();
  
  public default boolean standable() {
    return false;
  }
  
  public default boolean isOpen() {
    return false;
  }
  
  //------- Creation related -------
  
  /**
   * create an Environemment depending of his type from his encoding row
   * 
   * @param row
   * @param pos
   * @return Environment
   */
  public static Environment createEnvironment(EncodingRow row, Position pos) {
    return switch (row.id()) {
      case GameObjectID.SCENERY -> new Block(row.skin(), pos, true);
      case GameObjectID.OBSTACLE -> new Block(row.skin(), pos, false);
      case GameObjectID.GATE -> new Gate(row.skin(), pos);
      default -> null;
    };
  }
  
  public static Environment createEnvironment(String skin, Position pos) {
    return switch (GameEnvironment.getId(skin)) {
      case GameObjectID.SCENERY -> new Block(skin, pos, true);
      case GameObjectID.OBSTACLE -> new Block(skin, pos, false);
      case GameObjectID.GATE -> new Gate(skin, pos);
      default -> null;
    };
  }
  
  /**
   * create an Environment depending of his type from his Attributes element
   * 
   * @param elem
   * @param pos
   * @return Environment
   */
  public static Environment createEnvironment(ElementAttributes elem, Position pos) {
    return switch (elem.getID()) {
    case GameObjectID.SCENERY -> new Block(elem.getSkin(), pos, true);
    case GameObjectID.OBSTACLE -> new Block(elem.getSkin(), pos, false);
    case GameObjectID.GATE -> new Gate(elem.getSkin(), pos, elem.getLocked(), elem.getTeleport(), false);
    default -> null;
    };
  }
  
  public static Environment createEnvironment(ElementAttributes elem) {
    return Environment.createEnvironment(elem, elem.getPosition());
  }
  
  //------- Other -------
  
  public default GameEnvironment getEnvironment() {
    return GameEnvironment.getEnvironment(skin());
  }
  
  public static GameEnvironment getEnvironment(String skin) {
    return GameEnvironment.getEnvironment(skin);
  }
  
}
