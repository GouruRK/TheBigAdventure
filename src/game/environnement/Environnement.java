package game.environnement;

import util.Position;

public interface Environnement {

  public abstract String skin();
  public abstract Position pos();
  
  public default boolean standable() {
    return true;
  }
  
}
