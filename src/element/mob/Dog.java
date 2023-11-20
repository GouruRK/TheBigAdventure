package element.mob;

import java.util.Objects;

import util.placement.Position;

public record Dog(Position pos) implements Mob {
  
  public Dog {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/pnj/dog.png";
  }
  
}

