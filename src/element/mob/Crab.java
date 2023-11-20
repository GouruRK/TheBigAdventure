package element.mob;

import java.util.Objects;

import util.placement.Position;

public record Crab(Position pos) implements Mob {
  
  public Crab {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/pnj/crab.png";
  }
  
}

