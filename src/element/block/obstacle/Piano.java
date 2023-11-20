package element.block.obstacle;

import java.util.Objects;

import util.placement.Position;

public record Piano(Position pos) implements Obstacle {
  
  public Piano {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/obstacle/piano.png";
  }
  
}

