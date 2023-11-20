package element.item.items;

import java.util.Objects;

import util.placement.Position;

public record Mirror(Position pos) implements Items {
  
  public Mirror {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/item/mirror.png";
  }
  
}

