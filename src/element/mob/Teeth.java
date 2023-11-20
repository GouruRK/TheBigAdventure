package element.mob;

import java.util.Objects;

import util.placement.Position;

public record Teeth(Position pos) implements Mob {
  
  public Teeth {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/pnj/teeth.png";
  }
  
}

