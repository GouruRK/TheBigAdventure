package game.environnement;

import game.GameObjectID;
import parser.ElementAttributes;
import parser.EncodingRow;
import util.Position;

public interface Environnement {

  public abstract String skin();
  public abstract Position pos();
  
  public default boolean standable() {
    return true;
  }
  
  public static Environnement createEnvironnement(EncodingRow row, Position pos) {
    return switch (row.id()) {
      case GameObjectID.SCENERY -> new Block(row.skin(), pos, true);
      case GameObjectID.OBSTACLE -> new Block(row.skin(), pos, false);
      case GameObjectID.GATE -> new Gate(row.skin(), pos, null, false);
      default -> null;
    };
  }
  
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
}
