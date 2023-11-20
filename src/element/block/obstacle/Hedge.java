package element.block.obstacle;

import java.util.Objects;

import util.placement.Position;

public record Hedge(Position pos) implements Obstacle {
  
  public Hedge {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/obstacle/hedge.png";
  }
  
}

