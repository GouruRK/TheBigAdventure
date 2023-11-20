package element.item.weapon;

import java.util.Objects;

import util.placement.Position;

public record Sword(Position pos) implements Weapon {
  
  public Sword {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/item/sword.png";
  }
  
}

