package element.item.food;

import java.util.Objects;

import util.placement.Position;

public record Donut(Position pos) implements Food {
  
  public Donut {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/food/donut.png";
  }
  
}

