package element.item.food;

import java.util.Objects;

import util.placement.Position;

public record Boba(Position pos) implements Food {
  
  public Boba {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/food/boba.png";
  }
  
}

