package element.item.items;

import java.util.Objects;

import util.placement.Position;

public record Cog(Position pos) implements Items {
  
  public Cog {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/item/cog.png";
  }
  
}

