package element.block.scenery;

import java.util.Objects;

import util.placement.Position;

public record Algae(Position pos) implements Scenery {
  
  public Algae {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/scenery/algae.png";
  }
  
}

