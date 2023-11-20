package element.block.scenery;

import java.util.Objects;

import util.placement.Position;

public record Sprout(Position pos) implements Scenery {
  
  public Sprout {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/scenery/sprout.png";
  }
  
}

