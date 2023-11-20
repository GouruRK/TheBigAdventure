package element.item.food;

import java.util.Objects;

import util.placement.Position;

public record Love(Position pos) implements Food {
  
  public Love {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/food/love.png";
  }
  
}

