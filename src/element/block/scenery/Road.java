package element.block.scenery;

import java.util.Objects;

import util.placement.Position;

public record Road(Position pos) implements Scenery {
  
  public Road {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/scenery/road.png";
  }
  
}

