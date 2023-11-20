package element.block.obstacle;

import java.util.Objects;

import util.placement.Position;

public record Shell(Position pos) implements Obstacle {
  
  public Shell {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/obstacle/shell.png";
  }
  
}

