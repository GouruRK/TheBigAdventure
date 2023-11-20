package element.mob;

import java.util.Objects;

import util.placement.Position;

public record Monster(Position pos) implements Mob {
  
  public Monster {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/pnj/monster.png";
  }
  
}

