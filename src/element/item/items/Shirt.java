package element.item.items;

import java.util.Objects;

import util.placement.Position;

public record Shirt(Position pos) implements Items {
  
  public Shirt {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/item/shirt.png";
  }
  
}

