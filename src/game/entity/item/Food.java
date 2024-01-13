package game.entity.item;

import java.util.Objects;

import game.GameObjectID;

public record Food(String skin, String name) implements Item {

  public static final int HEALTH_MODIFIER = 1;
  
  //------- Constructors -------
  
  public Food {
    Objects.requireNonNull(skin);
  }
  
  public Food(String skin) {
    this(skin, null);
  }
  
  // ------- Getter -------
  
  public GameObjectID id() {
    return GameObjectID.FOOD;
  }
  
  // ------- Other -------
  
  @Override
  public boolean equals(Object obj) {
    return obj instanceof Food food
        && food.skin().equalsIgnoreCase(skin)
        && (food.name() == null ? food.name() == name: food.name().equals(name))
        && food.hashCode() == hashCode();
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(skin.toUpperCase(), name);
  }
  
}
