package element.item.food;

import java.util.Objects;

import util.placement.Position;

public record Fungi(Position pos) implements Food {
  
  public Fungi {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/food/fungi.png";
  }
  
}

