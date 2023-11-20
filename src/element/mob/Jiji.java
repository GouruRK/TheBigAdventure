package element.mob;

import java.util.Objects;

import util.placement.Position;

public record Jiji(Position pos) implements Mob {
  
  public Jiji {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/pnj/jiji.png";
  }
  
}

