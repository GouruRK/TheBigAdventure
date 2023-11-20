package element.item.food;

import java.util.Objects;

import util.placement.Position;

public record Banana(Position pos) implements Food {
  
  public Banana {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/food/banana.png";
  }
  
}

