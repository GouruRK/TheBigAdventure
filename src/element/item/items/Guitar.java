package element.item.items;

import java.util.Objects;

import util.placement.Position;

public record Guitar(Position pos) implements Items {
  
  public Guitar {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/item/guitar.png";
  }
  
}

