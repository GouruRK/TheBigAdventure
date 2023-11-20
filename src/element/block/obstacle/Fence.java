package element.block.obstacle;

import java.util.Objects;

import util.placement.Position;

public record Fence(Position pos) implements Obstacle {
  
  public Fence {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/obstacle/fence.png";
  }
  
}

