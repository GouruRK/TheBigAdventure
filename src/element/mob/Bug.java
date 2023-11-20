package element.mob;

import java.util.Objects;

import util.placement.Position;

public record Bug(Position pos) implements Mob {
  
  public Bug {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/pnj/bug.png";
  }
  
}

