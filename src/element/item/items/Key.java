package element.item.items;

import java.util.Objects;

import util.placement.Position;

public record Key(Position pos) implements Items {
  
  public Key {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/item/key.png";
  }
  
}

