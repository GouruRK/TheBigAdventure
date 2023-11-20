package element.block.scenery;

import java.util.Objects;

import util.placement.Position;

public record Flower(Position pos) implements Scenery {
  
  public Flower {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/scenery/flower.png";
  }
  
}

