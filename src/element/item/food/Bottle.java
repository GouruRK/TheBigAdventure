package element.item.food;

import java.util.Objects;

import util.placement.Position;

public record Bottle(Position pos) implements Food {
  
  public Bottle {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/food/bottle.png";
  }
  
}

