package element.item.items;

import java.util.Objects;

import util.placement.Position;

public record Sun(Position pos) implements Items {
  
  public Sun {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/item/sun.png";
  }
  
}

