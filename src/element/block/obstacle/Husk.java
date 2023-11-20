package element.block.obstacle;

import java.util.Objects;

import util.placement.Position;

public record Husk(Position pos) implements Obstacle {
  
  public Husk {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/obstacle/husk.png";
  }
  
}

