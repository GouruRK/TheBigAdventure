package element.block.obstacle;

import java.util.Objects;

import util.placement.Position;

public record Wall(Position pos) implements Obstacle {
  
  public Wall {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/obstacle/wall.png";
  }
  
}

