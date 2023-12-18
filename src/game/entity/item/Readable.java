package game.entity.item;

import java.util.Objects;

import game.GameObjectID;

public record Readable(String skin, String text, String name) implements Item {

  //------- Constructors -------
  
  public Readable {
    Objects.requireNonNull(skin);
  }
  
  public Readable(String skin, String text) {
    this(skin, text, null);
  }
  
  // ------- Getter -------
  
  public GameObjectID id() {
    return GameObjectID.READABLE;
  }
}
