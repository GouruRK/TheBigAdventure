package game.entity.item;

import java.util.Objects;

public record Readable(String skin, String name, String text) implements Item {

  public Readable {
    Objects.requireNonNull(skin);
    Objects.requireNonNull(name);
    Objects.requireNonNull(text);
  }
}
