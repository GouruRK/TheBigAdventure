package element.block.scenery;

import java.util.Objects;

import util.placement.Position;

public record Reed(Position pos) implements Scenery {
  
  public Reed {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/scenery/reed.png";
  }
  
}

