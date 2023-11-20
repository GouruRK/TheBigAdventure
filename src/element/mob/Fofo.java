package element.mob;

import java.util.Objects;

import util.placement.Position;

public record Fofo(Position pos) implements Mob {
  
  public Fofo {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/pnj/fofo.png";
  }
  
}

