package element.item.food;

import java.util.Objects;

import util.placement.Position;

public record Fungus(Position pos) implements Food {
  
  public Fungus {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/food/fungus.png";
  }
  
}

