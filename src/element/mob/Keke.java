package element.mob;

import java.util.Objects;

import util.placement.Position;

public record Keke(Position pos) implements Mob {
  
  public Keke {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/pnj/keke.png";
  }
  
}

