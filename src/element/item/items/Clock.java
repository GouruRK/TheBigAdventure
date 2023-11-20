package element.item.items;

import java.util.Objects;

import util.placement.Position;

public record Clock(Position pos) implements Items {
  
  public Clock {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/item/clock.png";
  }
  
}

