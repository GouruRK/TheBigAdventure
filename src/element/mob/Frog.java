package element.mob;

import java.util.Objects;

import util.placement.Position;

public record Frog(Position pos) implements Mob {
  
  public Frog {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/pnj/frog.png";
  }
  
}

