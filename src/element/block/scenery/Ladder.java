package element.block.scenery;

import java.util.Objects;

import util.placement.Position;

public record Ladder(Position pos) implements Scenery {
  
  public Ladder {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/scenery/ladder.png";
  }
  
}

