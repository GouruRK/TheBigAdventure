package element.item.weapon;

import java.util.Objects;

import util.placement.Position;

public record Bolt(Position pos) implements Weapon {
  
  public Bolt {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/item/bolt.png";
  }
  
}

