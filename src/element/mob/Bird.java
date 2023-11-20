package element.mob;

import java.util.Objects;

import util.placement.Position;

public record Bird(Position pos) implements Mob {
  
  public Bird {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/pnj/bird.png";
  }
  
}

