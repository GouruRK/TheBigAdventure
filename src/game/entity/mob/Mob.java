package game.entity.mob;

import game.Kind;
import game.entity.Entity;
import parser.ElementAttributes;
import util.Position;
import util.Zone;

public interface Mob extends Entity {

  public abstract Position pos();
  public abstract Zone zone();
  
  public default void moveX(double offset) {
    Position pos = pos();
    Zone zone = zone();
    pos.addX(offset);
    if (!zone.isInside(pos)) {
      pos.addX(-offset);
    }
  }
  
  public default void moveX(int offset) {
    moveX((double)offset);
  }
  
  public default void moveY(double offset) {
    Position pos = pos();
    pos.addY(offset);
  }
  
  public default void moveY(int offset) {
    moveY((double)offset);
  }
  
  public default int damage() {
    return 0;
  }
  
  public default int health() {
    return -1;
  }
  
  
  public static Mob createMob(ElementAttributes element) {
    return switch (element.getKind()) {
    case Kind.FRIEND -> new Friend(element.getSkin(), element.getPosition(), element.getZone(), element.getName(), element.getTrade());
    case Kind.ENEMY -> new Enemy(element.getSkin(), element.getPosition(), element.getZone(), element.gethealth(), element.getDamage(), element.getBehaviour(), element.getName());
    default -> null;
    };
  }
  
}
