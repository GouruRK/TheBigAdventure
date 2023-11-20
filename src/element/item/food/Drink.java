package element.item.food;

import java.util.Objects;

import util.placement.Position;

public record Drink(Position pos) implements Food {
  
  public Drink {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/food/drink.png";
  }
  
}

