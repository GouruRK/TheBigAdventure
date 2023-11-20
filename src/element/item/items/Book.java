package element.item.items;

import java.util.Objects;

import util.placement.Position;

public record Book(Position pos) implements Items {
  
  public Book {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/item/book.png";
  }
  
}

