package element.block.obstacle;

import java.util.Objects;

import util.placement.Position;

public record Fort(Position pos) implements Obstacle {
  
  public Fort {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/obstacle/fort.png";
  }
  
}

