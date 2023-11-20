package element.item.items;

import java.util.Objects;

import util.placement.Position;

public record Trumpet(Position pos) implements Items {
  
  public Trumpet {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/item/trumpet.png";
  }
  
}

