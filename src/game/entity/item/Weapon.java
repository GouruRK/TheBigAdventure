package game.entity.item;

import java.util.Objects;

import game.GameObjectID;

public record Weapon(String skin, int damage, boolean burning, String name) implements Item {

  // ------- Constructors -------
  
  public Weapon {
    Objects.requireNonNull(skin);
  }
  
  public Weapon(String skin, int damage, boolean burning) {
    this(skin, damage, burning, null);
  }
  
  public Weapon(String skin) {
    this(skin, Item.getItem(skin) == GameItems.BOLT ? Integer.MAX_VALUE: 0, false, null);    
  }
  
  public Weapon(String skin, String name) {
    this(skin, Item.getItem(skin) == GameItems.BOLT ? Integer.MAX_VALUE: 0, false, name);
  }
  
  // ------- Getter -------
  
  public GameObjectID id() {
    return GameObjectID.WEAPON;
  }
  
  //------- Modifiers -------
  
  public Weapon setFire() {
    if (!burning) {
      GameItems type = getItem();
      if (type == GameItems.SWORD || type == GameItems.STICK) {
        return new Weapon(skin, damage, true, name);
      }
    }
    return this;
  }

  // ------- Other -------
  
  @Override
  public boolean equals(Object obj) {
    return obj instanceof Weapon weapon
        && weapon.skin().equals(skin)
        && weapon.damage() == damage
        && weapon.burning() == burning
        && (weapon.name() == null ? weapon.name() == name: weapon.name().equals(name))
        && weapon.hashCode() == hashCode();
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(skin, damage, burning, name);
  }
  
}
