package element.block.obstacle;

import java.util.Objects;

import util.placement.Position;

public record Bed(Position pos) implements Obstacle {
  
  public Bed {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/obstacle/bed.png";
  }
  
}

