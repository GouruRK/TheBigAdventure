package element.block.obstacle;

import java.util.Objects;

import util.placement.Position;

public record Monitor(Position pos) implements Obstacle {
  
  public Monitor {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/obstacle/monitor.png";
  }
  
}

