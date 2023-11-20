package element.mob;

import java.util.Objects;

import util.placement.Position;

public record Ghost(Position pos) implements Mob {
  
  public Ghost {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/pnj/ghost.png";
  }
  
}

