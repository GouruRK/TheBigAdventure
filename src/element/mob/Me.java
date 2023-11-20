package element.mob;

import java.util.Objects;

import util.placement.Position;

public record Me(Position pos) implements Mob {
  
  public Me {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/pnj/me.png";
  }
  
}

