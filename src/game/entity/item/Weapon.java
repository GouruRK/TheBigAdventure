package game.entity.item;

import java.util.Objects;

public record Weapon(String skin, String name, int damage, boolean burning) implements Item {

  public Weapon {
    Objects.requireNonNull(skin);
    Objects.requireNonNull(name);
  }
  
}
