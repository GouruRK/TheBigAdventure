package element.item.items;

import java.util.Objects;

import util.placement.Position;

public record Box(Position pos) implements Items {
  
  public Box {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/item/box.png";
  }
  
}

