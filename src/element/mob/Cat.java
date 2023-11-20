package element.mob;

import java.util.Objects;

import util.placement.Position;

public record Cat(Position pos) implements Mob {
  
  public Cat {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/pnj/cat.png";
  }
  
}

