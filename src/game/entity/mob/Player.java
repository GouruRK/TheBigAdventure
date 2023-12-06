package game.entity.mob;

import java.util.Objects;

import util.Position;
import util.Zone;

public class Player implements Mob {

  private final String skin;
  private Position pos;
  private final int max_health;
  private final String name;
  private final Zone zone;
  private int health;
  
  public Player(String skin, Position pos, int max_health, int health, String name, Zone zone) {
    Objects.requireNonNull(skin);
    Objects.requireNonNull(pos);
    this.max_health = max_health;
    this.health = health;
    this.skin = skin;
    this.pos = pos;
    this.name = name;
    this.zone = zone;
  }

  public int health() {
  	return health;
  }
  
  public int max_health() {
  	return max_health;
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
  
  @Override
  public Zone zone() {
  	// TODO Auto-generated method stub
  	return zone;
  }
  	
  @Override
  public String toString() {
    return "Player(name ; " + name + ", skin: " + skin + ", " + pos.toString() + ")";
	}
  
}
