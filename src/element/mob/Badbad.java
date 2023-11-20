package element.mob;

import java.util.Objects;

import util.placement.Position;

public record Badbad(Position pos) implements Mob {
  
  public Badbad {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/pnj/badbad.png";
  }
  
}

