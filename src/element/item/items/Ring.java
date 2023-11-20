package element.item.items;

import java.util.Objects;

import util.placement.Position;

public record Ring(Position pos) implements Items {
  
  public Ring {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/item/ring.png";
  }
  
}

