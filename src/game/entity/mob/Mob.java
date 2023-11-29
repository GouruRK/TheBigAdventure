package game.entity.mob;

import game.entity.Entity;
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
  
}
