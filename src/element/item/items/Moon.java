package element.item.items;

import java.util.Objects;

import util.placement.Position;

public record Moon(Position pos) implements Items {
  
  public Moon {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/item/moon.png";
  }
  
}

