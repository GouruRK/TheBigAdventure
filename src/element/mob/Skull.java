package element.mob;

import java.util.Objects;

import util.placement.Position;

public record Skull(Position pos) implements Mob {
  
  public Skull {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/pnj/skull.png";
  }
  
}

