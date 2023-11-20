package element.item.items;

import java.util.Objects;

import util.placement.Position;

public record Lamp(Position pos) implements Items {
  
  public Lamp {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/item/lamp.png";
  }
  
}

