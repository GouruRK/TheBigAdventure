package element.block.scenery;

import java.util.Objects;

import util.placement.Position;

public record Vine(Position pos) implements Scenery {
  
  public Vine {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/scenery/vine.png";
  }
  
}

