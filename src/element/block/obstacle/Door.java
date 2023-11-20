package element.block.obstacle;

import java.util.Objects;

import util.placement.Position;

public record Door(Position pos) implements Obstacle {
  
  public Door {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/obstacle/door.png";
  }
  
}

