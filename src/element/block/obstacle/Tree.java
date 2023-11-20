package element.block.obstacle;

import java.util.Objects;

import util.placement.Position;

public record Tree(Position pos) implements Obstacle {
  
  public Tree {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/obstacle/tree.png";
  }
  
}

