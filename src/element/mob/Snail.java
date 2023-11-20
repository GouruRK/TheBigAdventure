package element.mob;

import java.util.Objects;

import util.placement.Position;

public record Snail(Position pos) implements Mob {
  
  public Snail {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/pnj/snail.png";
  }
  
}

