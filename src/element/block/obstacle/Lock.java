package element.block.obstacle;

import java.util.Objects;

import util.placement.Position;

public record Lock(Position pos) implements Obstacle {
  
  public Lock {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/obstacle/lock.png";
  }
  
}

