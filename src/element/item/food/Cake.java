package element.item.food;

import java.util.Objects;

import util.placement.Position;

public record Cake(Position pos) implements Food {
  
  public Cake {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/food/cake.png";
  }
  
}

