package element.item.items;

import java.util.Objects;

import util.placement.Position;

public record Paper(Position pos) implements Items {
  
  public Paper {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/item/paper.png";
  }
  
}

