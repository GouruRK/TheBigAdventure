package element.block.obstacle;

import java.util.Objects;

import util.placement.Position;

public record Pillar(Position pos) implements Obstacle {
  
  public Pillar {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/obstacle/pillar.png";
  }
  
}

