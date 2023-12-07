package game.entity.item;

import java.util.Objects;

import game.GameObjectID;

public record Food(String skin, String name) implements Item {

  public Food {
    Objects.requireNonNull(skin);
  }
  
  public Food(String skin) {
    this(skin, null);
  }
  
  public GameObjectID getID() {
    return GameObjectID.FOOD;
  }
  
}
