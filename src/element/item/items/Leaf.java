package element.item.items;

import java.util.Objects;

import util.placement.Position;

public record Leaf(Position pos) implements Items {
  
  public Leaf {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/item/leaf.png";
  }
  
}

