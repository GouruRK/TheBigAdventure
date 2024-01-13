package game.environnement;

import game.GameObjectID;
import parser.ElementAttributes;
import parser.EncodingRow;
import util.Position;

public interface Environnement {

  //------- Getters -------
  
  public abstract String skin();
  public abstract Position pos();
  
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
   * @return Environnement
   */
  public static Environnement createEnvironnement(EncodingRow row, Position pos) {
    return switch (row.id()) {
      case GameObjectID.SCENERY -> new Block(row.skin(), pos, true);
      case GameObjectID.OBSTACLE -> new Block(row.skin(), pos, false);
      case GameObjectID.GATE -> new Gate(row.skin(), pos, null, false);
      default -> null;
    };
  }
  
  public static Environnement createEnvironnement(String skin, Position pos) {
    return switch (GameEnvironnement.getId(skin)) {
      case GameObjectID.SCENERY -> new Block(skin, pos, true);
      case GameObjectID.OBSTACLE -> new Block(skin, pos, false);
      case GameObjectID.GATE -> new Gate(skin, pos, null, false);
      default -> null;
    };
  }
  
  /**
   * create an Environnement depending of his type from his Attributes element
   * 
   * @param elem
   * @param pos
   * @return Environnement
   */
  public static Environnement createEnvironnement(ElementAttributes elem, Position pos) {
    return switch (elem.getID()) {
    case GameObjectID.SCENERY -> new Block(elem.getSkin(), pos, true);
    case GameObjectID.OBSTACLE -> new Block(elem.getSkin(), pos, false);
    case GameObjectID.GATE -> new Gate(elem.getSkin(), pos, elem.getLocked(), false);
    default -> null;
    };
  }
  
  public static Environnement createEnvironnement(ElementAttributes elem) {
    return Environnement.createEnvironnement(elem, elem.getPosition());
  }
  
  //------- Other -------
  
  public default GameEnvironnement getEnvironnement() {
    return GameEnvironnement.getEnvironnement(skin());
  }
  
  public static GameEnvironnement getEnvironnement(String skin) {
    return GameEnvironnement.getEnvironnement(skin);
  }
  
}
