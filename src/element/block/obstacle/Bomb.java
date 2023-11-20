package element.block.obstacle;

import java.util.Objects;

import util.placement.Position;

public record Bomb(Position pos) implements Obstacle {
  
  public Bomb {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/obstacle/bomb.png";
  }
  
}

