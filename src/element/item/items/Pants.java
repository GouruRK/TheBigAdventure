package element.item.items;

import java.util.Objects;

import util.placement.Position;

public record Pants(Position pos) implements Items {
  
  public Pants {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/item/pants.png";
  }
  
}

