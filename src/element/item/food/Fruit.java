package element.item.food;

import java.util.Objects;

import util.placement.Position;

public record Fruit(Position pos) implements Food {
  
  public Fruit {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/food/fruit.png";
  }
  
}

