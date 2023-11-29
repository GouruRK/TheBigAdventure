package game.entity.item;

import java.util.Objects;

public record Readable(String skin, String text, String name) implements Item {

  public Readable {
    Objects.requireNonNull(skin);
    Objects.requireNonNull(text);
  }
  
  public Readable(String skin, String text) {
    this(skin, text, null);
  }
}
