package element.item.items;

import java.util.Objects;

import util.placement.Position;

public record Star(Position pos) implements Items {
  
  public Star {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/item/star.png";
  }
  
}

