package element.block.obstacle;

import java.util.Objects;

import util.placement.Position;

public record Cliff(Position pos) implements Obstacle {
  
  public Cliff {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/obstacle/cliff.png";
  }
  
}

