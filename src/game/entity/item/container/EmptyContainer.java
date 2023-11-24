package game.entity.item.container;

import java.util.Objects;

public record EmptyContainer(String skin, String name) implements Container {

  public EmptyContainer {
    Objects.requireNonNull(skin);
    Objects.requireNonNull(name);
  }
  
}
