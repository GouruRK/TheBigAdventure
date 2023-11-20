package element.item.weapon;

import java.util.Objects;

import util.placement.Position;

public record Stick(Position pos) implements Weapon {
  
  public Stick {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/item/stick.png";
  }
  
}

