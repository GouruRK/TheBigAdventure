package element.item.food;

import java.util.Objects;

import util.placement.Position;

public record Potato(Position pos) implements Food {
  
  public Potato {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/food/potato.png";
  }
  
}

