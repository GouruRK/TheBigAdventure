package fr.uge.thebigadventure.game.entity.mob;


import fr.uge.thebigadventure.game.Kind;
import fr.uge.thebigadventure.game.entity.Entity;
import fr.uge.thebigadventure.parser.ElementAttributes;
import fr.uge.thebigadventure.util.Direction;
import fr.uge.thebigadventure.util.Position;
import fr.uge.thebigadventure.util.Zone;

public interface Mob extends Entity {

  // ------------ Getter ------------
  
  public abstract Position pos();
  public abstract Zone zone();
  public abstract void setPos(Position pos);
  public abstract int health();
  public abstract int maxHealth();
  public abstract Direction facing();
  public abstract void setFacing(Direction dir);

  public abstract void takeDamage(int damage);
  
  public default Behaviour behaviour() {
    return Behaviour.UNKNOWN;
  }
  
  public default int damage() {
    return 0;
  }
  
  public default boolean isDead() {
    return health() < 0;
  }
  
  
  // ------------ Movement related ------------
  
  /**
   * change the current location of mob in x's axe with double
   * 
   * @param offset
   */
  public default void moveX(double offset) {
    Position pos = pos();
    Zone zone = zone();
    pos.addX(offset);
    if (!zone.isInside(pos)) {
      pos.addX(-offset);
    }
  }
  
  /**
   * change the current location of mob in x's axe with int
   * 
   * @param offset
   */
  public default void moveX(int offset) {
    moveX((double)offset);
  }
  
  /**
   * change the current location of mob in y's axe with double
   * 
   * @param offset
   */
  public default void moveY(double offset) {
    Position pos = pos();
    Zone zone = zone();
    pos.addY(offset);
    if (!zone.isInside(pos)){
    	pos.addY(-offset);
    }
  }
  
  /**
   * change the current location of mob in x's axe with int
   * 
   * @param offset
   */
  public default void moveY(int offset) {
    moveY((double)offset);
  }

  /**
   * check if mob is at his max range of location
   * 
   * @param pos
   * @return boolean
   */
  public default boolean isMoveInZonePossible(Position pos) {
  	if (!zone().isInside(pos)) {
  		return false;
  	}
  	return true;
  }
  
  // ------------ Creation related ------------
  
  /**
   * create a mob
   * 
   * @param element
   * @return Mob
   */
  public static Mob createMob(ElementAttributes element) {
    return switch (element.kind()) {
    case Kind.FRIEND -> new Friend(element.skin(), element.position(), element.zone(),element.name(), element.health(), element.trade(), element.text());
    case Kind.ENEMY -> new Enemy(element.skin(), element.position(), element.zone(), element.health(), element.damage(), element.behaviour(), element.name());
    default -> null;
    };
  }
  
  //------- Other -------
  
  public default GameMobs getMob() {
    return GameMobs.getMob(skin());
  }
}
