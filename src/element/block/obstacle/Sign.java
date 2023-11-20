package element.block.obstacle;

import java.util.Objects;

import util.placement.Position;

public record Sign(Position pos) implements Obstacle {
  
  public Sign {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/obstacle/sign.png";
  }
  
}

