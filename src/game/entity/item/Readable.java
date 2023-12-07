package game.entity.item;

import java.util.Objects;

import game.GameObjectID;

public record Readable(String skin, String text, String name) implements Item {

  public Readable {
    Objects.requireNonNull(skin);
  }
  
  public Readable(String skin, String text) {
    this(skin, text, null);
  }
  
  public GameObjectID getID() {
    return GameObjectID.READABLE;
  }
}
