package element.mob;

import java.util.Objects;

import util.placement.Position;

public record Baba(Position pos) implements Mob {
  
  public Baba {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/pnj/baba.png";
  }
  
}

