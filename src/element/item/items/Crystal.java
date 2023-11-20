package element.item.items;

import java.util.Objects;

import util.placement.Position;

public record Crystal(Position pos) implements Items {
  
  public Crystal {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/item/crystal.png";
  }
  
}

