package element.block.scenery;

import java.util.Objects;

import util.placement.Position;

public record Foliage(Position pos) implements Scenery {
  
  public Foliage {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/scenery/foliage.png";
  }
  
}

