package element.block.scenery;

import java.util.Objects;

import util.placement.Position;

public record Lily(Position pos) implements Scenery {
  
  public Lily {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/scenery/lily.png";
  }
  
}

