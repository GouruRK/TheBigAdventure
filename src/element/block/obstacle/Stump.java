package element.block.obstacle;

import java.util.Objects;

import util.placement.Position;

public record Stump(Position pos) implements Obstacle {
  
  public Stump {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/obstacle/stump.png";
  }
  
}

