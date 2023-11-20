package element.block.scenery;

import java.util.Objects;

import util.placement.Position;

public record Tile(Position pos) implements Scenery {
  
  public Tile {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/scenery/tile.png";
  }
  
}

