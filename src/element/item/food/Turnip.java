package element.item.food;

import java.util.Objects;

import util.placement.Position;

public record Turnip(Position pos) implements Food {
  
  public Turnip {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/food/turnip.png";
  }
  
}

