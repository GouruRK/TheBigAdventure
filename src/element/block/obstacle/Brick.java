package element.block.obstacle;

import java.util.Objects;

import util.placement.Position;

public record Brick(Position pos) implements Obstacle {
  
  public Brick {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/obstacle/brick.png";
  }
  
}

