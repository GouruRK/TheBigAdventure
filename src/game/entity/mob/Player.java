package game.entity.mob;

import java.util.Objects;

import game.GameObjectID;
import game.entity.item.Item;
import util.Position;
import util.Zone;

public class Player implements Mob {

  private final String skin;
  private final Zone zone;
  private final int maxHealth;
  private final String name;
  private Position pos;
  private int health;
  private Item hold;
  
  public Player(String skin, Position pos, Zone zone, int health, int maxHealth, String name, Item hold) {
    Objects.requireNonNull(skin);
    Objects.requireNonNull(pos);
    Objects.requireNonNull(zone);
    this.skin = skin;
    this.pos = pos;
    this.zone = zone;
    this.health = health;
    this.maxHealth = maxHealth;
    this.name = name;
    this.hold = hold;
  }

  public Player(String skin, Position pos, Zone zone, int health, String name, Item hold) {
    this(skin, pos, zone, health, health, name, hold);
  }
  
  public int health() {
  	return health;
  }
  
  public int maxHealth() {
  	return maxHealth;
  }
  
  public String skin() {
    return skin;
  }

  public Position pos() {
    return pos;
  }
  
  public String name() {
  	return name;
  }

  public void setPos(Position pos) {
  	this.pos = pos;
  }
  
  public Zone zone() {
  	return zone;
  }
  
  public int damage() {
    if (hold == null) {
      return 0;
    }
    if (hold.id() == GameObjectID.WEAPON) {
      return hold.damage();
    }
    return 0;
  }
  
  public Item hold() {
    return hold;
  }
  
  public void setHold(Item item) {
    Objects.requireNonNull(item);
    hold = item;
  }
  
  @Override
  public String toString() {
    return "Player(name ; " + name + ", skin: " + skin + ", " + pos.toString() + ")";
	}
  
  public GameObjectID id() {
    return GameObjectID.PLAYER;
  }
  
}
