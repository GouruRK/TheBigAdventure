package element.block.obstacle;

import java.util.Objects;

import util.placement.Position;

public record Rock(Position pos) implements Obstacle {
  
  public Rock {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/obstacle/rock.png";
  }
  
}

