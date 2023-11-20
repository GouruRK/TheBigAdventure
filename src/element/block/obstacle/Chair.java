package element.block.obstacle;

import java.util.Objects;

import util.placement.Position;

public record Chair(Position pos) implements Obstacle {
  
  public Chair {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/obstacle/chair.png";
  }
  
}

