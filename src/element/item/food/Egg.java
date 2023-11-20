package element.item.food;

import java.util.Objects;

import util.placement.Position;

public record Egg(Position pos) implements Food {
  
  public Egg {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/food/egg.png";
  }
  
}

