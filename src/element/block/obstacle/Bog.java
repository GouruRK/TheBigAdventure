package element.block.obstacle;

import java.util.Objects;

import util.placement.Position;

public record Bog(Position pos) implements Obstacle {
  
  public Bog {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/obstacle/bog.png";
  }
  
}

