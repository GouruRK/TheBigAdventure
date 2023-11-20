package element.item.items;

import java.util.Objects;

import util.placement.Position;

public record Scissors(Position pos) implements Items {
  
  public Scissors {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/item/scissors.png";
  }
  
}

