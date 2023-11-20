package element.block.obstacle;

import java.util.Objects;

import util.placement.Position;

public record Husks(Position pos) implements Obstacle {
  
  public Husks {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/obstacle/husks.png";
  }
  
}

