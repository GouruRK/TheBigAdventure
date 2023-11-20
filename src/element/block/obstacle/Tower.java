package element.block.obstacle;

import java.util.Objects;

import util.placement.Position;

public record Tower(Position pos) implements Obstacle {
  
  public Tower {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/obstacle/tower.png";
  }
  
}

