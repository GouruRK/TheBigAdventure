package game.entity.mob;

import java.util.Objects;

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
  
  public Enemy(String skin, Position pos, Zone zone, int health, int damage, Behaviour behaviour, String name) {
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
  }
  
  public Enemy(String skin, Position pos, Zone zone, int health, int damage, Behaviour behaviour) {
    this(skin, pos, zone, health, damage, behaviour, null);
  }
  
  public Enemy(String skin, Position pos, Zone zone, int health, int damage) {
    this(skin, pos, zone, health, damage, Behaviour.STROLL, null);
  }
  
  public Enemy(String skin, Position pos, Zone zone, int health, int damage, String name) {
    this(skin, pos, zone, health, damage, Behaviour.STROLL, name);
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
  
  @Override
  public int damage() {
    return damage;
  }

  public void setPos(Position pos) {
  	this.pos = pos;
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
