package game.entity.item;

import java.util.Objects;

public record Thing(String skin, String name) implements Item {

  public Thing {
    Objects.requireNonNull(skin);
  }
  
  public Thing(String skin) {
    this(skin, null);
  }
  
}
