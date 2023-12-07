package game.entity.item;

import java.util.Objects;

import game.GameObjectID;

public record Thing(String skin, String name) implements Item {

  public Thing {
    Objects.requireNonNull(skin);
  }
  
  public Thing(String skin) {
    this(skin, null);
  }
  
  public GameObjectID id() {
    return GameObjectID.THING;
  }
  
}
