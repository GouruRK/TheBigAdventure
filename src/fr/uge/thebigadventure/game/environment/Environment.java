package fr.uge.thebigadventure.game.environment;

import java.util.Locale;
import java.util.Objects;

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
    Objects.requireNonNull(row);
    Objects.requireNonNull(pos);
    String skin = row.skin().toUpperCase(Locale.ROOT);
    return switch (row.id()) {
      case GameObjectID.SCENERY -> new Block(skin, pos, true);
      case GameObjectID.OBSTACLE -> new Block(skin, pos, false);
      case GameObjectID.GATE -> new Gate(skin, pos);
      default -> null;
    };
  }
  
  public static Environment createEnvironment(String skin, Position pos) {
    Objects.requireNonNull(skin);
    Objects.requireNonNull(pos);
    skin = skin.toUpperCase(Locale.ROOT);
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
    Objects.requireNonNull(elem);
    Objects.requireNonNull(pos);
    
    String skin = elem.skin().toUpperCase(Locale.ROOT);
    return switch (elem.getID()) {
      case GameObjectID.SCENERY -> new Block(skin, pos, true);
      case GameObjectID.OBSTACLE -> new Block(skin, pos, false);
      case GameObjectID.GATE -> new Gate(skin, pos, elem.locked(), elem.teleport(), false);
      default -> null;
    };
  }
  
  public static Environment createEnvironment(ElementAttributes elem) {
    Objects.requireNonNull(elem);
    return Environment.createEnvironment(elem, elem.position());
  }
  
  //------- Other -------
  
  public default GameEnvironment getEnvironment() {
    return GameEnvironment.getEnvironment(skin());
  }
  
  public static GameEnvironment getEnvironment(String skin) {
    Objects.requireNonNull(skin);
    return GameEnvironment.getEnvironment(skin);
  }
  
}
