package element.block.scenery;

import java.util.Objects;

import util.placement.Position;

public record Plank(Position pos) implements Scenery {
  
  public Plank {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/scenery/plank.png";
  }
  
}

