package element.block.obstacle;

import java.util.Objects;

import util.placement.Position;

public record Pipe(Position pos) implements Obstacle {
  
  public Pipe {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/obstacle/pipe.png";
  }
  
}

