package element.block.obstacle;

import java.util.Objects;

import util.placement.Position;

public record Gate(Position pos) implements Obstacle {
  
  public Gate {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/obstacle/gate.png";
  }
  
}

