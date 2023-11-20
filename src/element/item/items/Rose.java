package element.item.items;

import java.util.Objects;

import util.placement.Position;

public record Rose(Position pos) implements Items {
  
  public Rose {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/item/rose.png";
  }
  
}

