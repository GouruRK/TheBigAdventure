package element.mob;

import java.util.Objects;

import util.placement.Position;

public record Robot(Position pos) implements Mob {
  
  public Robot {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/pnj/robot.png";
  }
  
}

