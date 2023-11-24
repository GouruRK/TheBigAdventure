package game.entity.mob;

import java.util.Objects;

import util.Direction;
import util.Position;
import util.Zone;

public class Ennemy implements Mob {
  
  private final String skin;
  private final String name;
  private final Behaviour behaviour; 
  private final Zone zone; 
  private Position pos;
  private Direction lookAt;
  private int health;
  
  public Ennemy(String skin, String name, Position pos, Zone zone, Direction lookAt, int health, Behaviour behaviour) {
    Objects.requireNonNull(skin);
    Objects.requireNonNull(name);
    Objects.requireNonNull(pos);
    Objects.requireNonNull(zone);
    this.skin = skin;
    this.name = name;
    this.pos = pos;
    this.zone = zone;
    this.lookAt = lookAt;
    this.health = health;
    this.behaviour = behaviour;
  }
  
  public Ennemy(String skin, String name, Position pos, Zone zone, int health, Direction lookAt) {
    this(skin, name, pos, zone, lookAt, health, Behaviour.STROLL);
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
}
