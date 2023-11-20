package element.block.obstacle;

import java.util.Objects;

import util.placement.Position;

public record Table(Position pos) implements Obstacle {
  
  public Table {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/obstacle/table.png";
  }
  
}

