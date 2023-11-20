package element.mob;

import java.util.Objects;

import util.placement.Position;

public record Fish(Position pos) implements Mob {
  
  public Fish {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/pnj/fish.png";
  }
  
}

