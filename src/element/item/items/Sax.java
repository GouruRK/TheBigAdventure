package element.item.items;

import java.util.Objects;

import util.placement.Position;

public record Sax(Position pos) implements Items {
  
  public Sax {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/item/sax.png";
  }
  
}

