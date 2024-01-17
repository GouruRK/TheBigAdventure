package fr.uge.thebigadventure.game.entity.mob;

import java.util.Objects;

import fr.uge.thebigadventure.game.GameObjectID;
import fr.uge.thebigadventure.game.entity.item.Item;
import fr.uge.thebigadventure.util.Direction;
import fr.uge.thebigadventure.util.Position;
import fr.uge.thebigadventure.util.Zone;

public class Player implements Mob {

  //------- Fields -------
  
  private final String skin;
  private final Zone zone;
  private final int maxHealth;
  private final String name;
  private Position pos;
  private int health;
  private Item hold;
  private Direction facing;
  
  //------- Constructors -------
  
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
    this.facing = Direction.EAST;
  }

  public Player(String skin, Position pos, Zone zone, int health, String name, Item hold) {
    this(skin, pos, zone, health, health, name, hold);
  }
  
  //------- Getter -------
  
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

  public Direction facing() {
    return facing;
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

  public GameObjectID id() {
    return GameObjectID.PLAYER;
  }
  
  //------- Setter -------
  
  public void setPos(Position pos) {
  	this.pos = pos;
  }
  
  public void setFacing(Direction dir) {
    this.facing = dir;
  }
  
  public void setHold(Item item) {
    Objects.requireNonNull(item);
    hold = item;
  }
  
  public Item removeHeldItem() {
    Item item = hold;
    this.hold = null;
    return item;
  }
  
  public void takeDamage(int damage) {
    this.health -= damage;
  }
  
  public void addHealth(int toAdd) {
    health += toAdd;
    if (health > maxHealth) {
      health = maxHealth;
    }
  }
  
  public void eat() {
    if (hold != null && hold.id() == GameObjectID.FOOD) {
      if (health != maxHealth) {
        addHealth(1);
      }
      removeHeldItem();      
    }
  }
  
  
  @Override
  public String toString() {
    return "Player(name ; " + name + ", skin: " + skin + ", " + pos.toString() + ")";
	}
  
}
