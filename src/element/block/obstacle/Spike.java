package element.block.obstacle;

import java.util.Objects;

import util.placement.Position;

public record Spike(Position pos) implements Obstacle {
  
  public Spike {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/obstacle/spike.png";
  }
  
}

