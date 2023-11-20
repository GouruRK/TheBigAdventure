package element.block.obstacle;

import java.util.Objects;

import util.placement.Position;

public record House(Position pos) implements Obstacle {
  
  public House {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/obstacle/house.png";
  }
  
}

