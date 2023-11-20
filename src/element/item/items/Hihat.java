package element.item.items;

import java.util.Objects;

import util.placement.Position;

public record Hihat(Position pos) implements Items {
  
  public Hihat {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/item/hihat.png";
  }
  
}

