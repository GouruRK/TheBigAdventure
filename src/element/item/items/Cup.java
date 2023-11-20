package element.item.items;

import java.util.Objects;

import util.placement.Position;

public record Cup(Position pos) implements Items {
  
  public Cup {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/item/cup.png";
  }
  
}

