package element.mob;

import java.util.Objects;

import util.placement.Position;

public record Bunny(Position pos) implements Mob {
  
  public Bunny {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/pnj/bunny.png";
  }
  
}

