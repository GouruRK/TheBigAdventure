package element.block.obstacle;

import java.util.Objects;

import util.placement.Position;

public record Statue(Position pos) implements Obstacle {
  
  public Statue {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/obstacle/statue.png";
  }
  
}

