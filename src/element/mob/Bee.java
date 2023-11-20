package element.mob;

import java.util.Objects;

import util.placement.Position;

public record Bee(Position pos) implements Mob {
  
  public Bee {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/pnj/bee.png";
  }
  
}

