package game.entity.mob;


import game.Kind;
import game.entity.Entity;
import parser.ElementAttributes;
import util.Direction;
import util.Position;
import util.Zone;

public interface Mob extends Entity {

  // ------------ Getter ------------
  
  public abstract Position pos();
  public abstract Zone zone();
  public abstract void setPos(Position pos);
  public abstract int health();
  public abstract int maxHealth();
  public abstract Direction facing();
  public abstract void setFacing(Direction dir);
  
  public default Behaviour behaviour() {
    return Behaviour.UNKNOWN;
  }
  
  public default int damage() {
    return 0;
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
    return switch (element.getKind()) {
    case Kind.FRIEND -> new Friend(element.getSkin(), element.getPosition(), element.getZone(),element.getName(),element.getHealth(), element.getTrade());
    case Kind.ENEMY -> new Enemy(element.getSkin(), element.getPosition(), element.getZone(), element.getHealth(), element.getDamage(), element.getBehaviour(), element.getName());
    default -> null;
    };
  }
}
