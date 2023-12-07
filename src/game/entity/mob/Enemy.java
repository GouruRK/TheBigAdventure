package game.entity.mob;

import java.util.Objects;

import game.GameObjectID;
import util.Position;
import util.Zone;

public class Enemy implements Mob {
  
  private final String skin;
  private final String name;
  private final Behaviour behaviour;
  private final Zone zone; 
  private final int damage;
  private Position pos;
  private int health;
  private int maxHealth;
  
  public Enemy(String skin, Position pos, Zone zone, int health, int maxHealth, int damage, Behaviour behaviour, String name) {
    Objects.requireNonNull(skin);
    Objects.requireNonNull(pos);
    Objects.requireNonNull(zone);
    this.skin = skin;
    this.name = name;
    this.pos = pos;
    this.zone = zone;
    this.health = health;
    this.behaviour = behaviour;
    this.damage = damage;
    this.maxHealth = maxHealth;
  }
  
  public Enemy(String skin, Position pos, Zone zone, int health, int damage, Behaviour behaviour, String name) {
    this(skin, pos, zone, health, health, damage, behaviour, name);
  }
  
  public String skin() {
    return skin;
  }
  
  public String name() {
    return name;
  }
    
  public Position pos() {
    return pos;
  }
  
  public Zone zone() {
    return zone;
  }
  
  public int health() {
  	return health;
  }
  
  public int damage() {
    return damage;
  }
  
  public int maxHealth() {
  	return maxHealth;
  }

  public void setPos(Position pos) {
  	this.pos = pos;
  }
  
  public GameObjectID id() {
    return GameObjectID.ENEMY;
  }
  
  @Override
  public String toString() {
    return "Enemy(skin: " + skin 
        + ", pos: " + pos.toString()
        + ", zone: " + zone.toString()
        + ", health: " + health
        + ", damage: " + damage
        + ", behaviour: " + behaviour
        + ")";
  }
  
}
