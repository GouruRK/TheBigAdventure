package element.item.items;

import java.util.Objects;

import util.placement.Position;

public record Seed(Position pos) implements Items {
  
  public Seed {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/item/seed.png";
  }
  
}

