package element.item.weapon;

import java.util.Objects;

import util.placement.Position;

public record Shovel(Position pos) implements Weapon {
  
  public Shovel {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/item/shovel.png";
  }
  
}

