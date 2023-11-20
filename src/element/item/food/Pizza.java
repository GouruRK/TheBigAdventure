package element.item.food;

import java.util.Objects;

import util.placement.Position;

public record Pizza(Position pos) implements Food {
  
  public Pizza {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/food/pizza.png";
  }
  
}

