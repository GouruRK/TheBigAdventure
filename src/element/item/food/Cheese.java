package element.item.food;

import java.util.Objects;

import util.placement.Position;

public record Cheese(Position pos) implements Food {
  
  public Cheese {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/food/cheese.png";
  }
  
}

