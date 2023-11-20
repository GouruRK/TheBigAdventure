package element.item.items;

import java.util.Objects;

import util.placement.Position;

public record Cash(Position pos) implements Items {
  
  public Cash {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/item/cash.png";
  }
  
}

