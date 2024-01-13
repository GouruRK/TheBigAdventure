package game.entity.item;

import java.util.Objects;

import game.GameObjectID;

public record Thing(String skin, String name) implements Item {

  // ------- Constructors -------
  
  public Thing {
    Objects.requireNonNull(skin);
  }
  
  public Thing(String skin) {
    this(skin, null);
  }
  
  // ------- Getter -------
  
  public GameObjectID id() {
    return GameObjectID.THING;
  }
  
  // ------- Other -------
  
  @Override
  public boolean equals(Object obj) {
    return obj instanceof Thing thing
        && thing.skin().equalsIgnoreCase(skin)
        && (thing.name() == null ? thing.name() == name: thing.name().equals(name))
        && thing.hashCode() == hashCode();
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(skin.toUpperCase(), name);
  }
  
}
