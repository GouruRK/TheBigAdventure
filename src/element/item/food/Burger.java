package element.item.food;

import java.util.Objects;

import util.placement.Position;

public record Burger(Position pos) implements Food {
  
  public Burger {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/food/burger.png";
  }
  
}

