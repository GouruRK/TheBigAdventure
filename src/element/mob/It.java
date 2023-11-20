package element.mob;

import java.util.Objects;

import util.placement.Position;

public record It(Position pos) implements Mob {
  
  public It {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/pnj/it.png";
  }
  
}

