package element.mob;

import java.util.Objects;

import util.placement.Position;

public record Bat(Position pos) implements Mob {
  
  public Bat {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/pnj/bat.png";
  }
  
}

