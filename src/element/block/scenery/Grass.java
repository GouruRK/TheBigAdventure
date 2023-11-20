package element.block.scenery;

import java.util.Objects;

import util.placement.Position;

public record Grass(Position pos) implements Scenery {
  
  public Grass {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/scenery/grass.png";
  }
  
}

