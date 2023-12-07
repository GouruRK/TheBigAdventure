package game.entity.item;

import java.util.Objects;

import game.GameObjectID;

public record Weapon(String skin, int damage, boolean burning, String name) implements Item {

  public Weapon {
    Objects.requireNonNull(skin);
    Objects.requireNonNull(name);
  }
  
  public Weapon(String skin, int damage, boolean burning) {
    this(skin, damage, burning, null);
  }
  
  public Weapon(String skin) {
    this(skin, 0, false, null);
  }
  
  public Weapon(String skin, String name) {
    this(skin, 0, false, name);
  }
  
  public GameObjectID id() {
    return GameObjectID.WEAPON;
  }
  
}
