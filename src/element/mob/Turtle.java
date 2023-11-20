package element.mob;

import java.util.Objects;

import util.placement.Position;

public record Turtle(Position pos) implements Mob {
  
  public Turtle {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/pnj/turtle.png";
  }
  
}

