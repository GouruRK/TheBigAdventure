package element.item.items;

import java.util.Objects;

import util.placement.Position;

public record Vase(Position pos) implements Items {
  
  public Vase {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/item/vase.png";
  }
  
}

