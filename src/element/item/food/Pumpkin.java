package element.item.food;

import java.util.Objects;

import util.placement.Position;

public record Pumpkin(Position pos) implements Food {
  
  public Pumpkin {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/food/pumpkin.png";
  }
  
}

