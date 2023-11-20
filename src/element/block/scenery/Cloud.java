package element.block.scenery;

import java.util.Objects;

import util.placement.Position;

public record Cloud(Position pos) implements Scenery {
  
  public Cloud {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/scenery/cloud.png";
  }
  
}

