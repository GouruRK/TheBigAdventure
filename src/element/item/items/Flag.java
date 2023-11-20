package element.item.items;

import java.util.Objects;

import util.placement.Position;

public record Flag(Position pos) implements Items {
  
  public Flag {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/item/flag.png";
  }
  
}

