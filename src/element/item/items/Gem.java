package element.item.items;

import java.util.Objects;

import util.placement.Position;

public record Gem(Position pos) implements Items {
  
  public Gem {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/item/gem.png";
  }
  
}

