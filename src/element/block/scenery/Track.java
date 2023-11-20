package element.block.scenery;

import java.util.Objects;

import util.placement.Position;

public record Track(Position pos) implements Scenery {
  
  public Track {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/scenery/track.png";
  }
  
}

