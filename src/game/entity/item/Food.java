package game.entity.item;

import java.util.Objects;

public record Food(String skin, String name) implements Item {

  public Food {
    Objects.requireNonNull(skin);
    Objects.requireNonNull(name);
  }
  
}
