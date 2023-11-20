package element.mob;

import java.util.Objects;

import util.placement.Position;

public record Lizard(Position pos) implements Mob {
  
  public Lizard {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/pnj/lizard.png";
  }
  
}

