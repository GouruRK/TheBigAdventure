package element.block.obstacle;

import java.util.Objects;

import util.placement.Position;

public record Trees(Position pos) implements Obstacle {
  
  public Trees {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/obstacle/trees.png";
  }
  
}

