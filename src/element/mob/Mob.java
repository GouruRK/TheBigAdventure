package element.mob;

import util.placement.Position;

public interface Mob {
  public abstract Position pos();
  
  public abstract String pathToImage();
}
