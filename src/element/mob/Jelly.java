package element.mob;

import java.util.Objects;

import util.placement.Position;

public record Jelly(Position pos) implements Mob {
  
  public Jelly {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/pnj/jelly.png";
  }
  
}

