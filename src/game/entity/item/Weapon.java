package game.entity.item;

import java.util.Objects;

public record Weapon(String skin, int damage, boolean burning, String name) implements Item {

  public Weapon {
    Objects.requireNonNull(skin);
    Objects.requireNonNull(name);
  }
  
  public Weapon(String skin, int damage, boolean burning) {
    this(skin, damage, burning, null);
  }
  
}
