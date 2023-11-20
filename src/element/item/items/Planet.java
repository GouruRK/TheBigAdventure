package element.item.items;

import java.util.Objects;

import util.placement.Position;

public record Planet(Position pos) implements Items {
  
  public Planet {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/item/planet.png";
  }
  
}

