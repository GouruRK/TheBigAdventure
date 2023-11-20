package element.mob;

import java.util.Objects;

import util.placement.Position;

public record Worm(Position pos) implements Mob {
  
  public Worm {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/pnj/worm.png";
  }
  
}

