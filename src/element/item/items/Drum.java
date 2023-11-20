package element.item.items;

import java.util.Objects;

import util.placement.Position;

public record Drum(Position pos) implements Items {
  
  public Drum {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/item/drum.png";
  }
  
}

