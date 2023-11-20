package element.item.items;

import java.util.Objects;

import util.placement.Position;

public record Orb(Position pos) implements Items {
  
  public Orb {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/item/orb.png";
  }
  
}

