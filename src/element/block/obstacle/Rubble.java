package element.block.obstacle;

import java.util.Objects;

import util.placement.Position;

public record Rubble(Position pos) implements Obstacle {
  
  public Rubble {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/obstacle/rubble.png";
  }
  
}

