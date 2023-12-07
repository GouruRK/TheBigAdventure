package game.entity.mob;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import game.entity.item.Item;
import util.Position;
import util.Zone;

public class Friend implements Mob {

  private final String skin;
  private final String name;
  private final Zone zone; 
  private int health;
  private final int maxHealth;
  private Position pos;
  private final Map<String, List<Item>> trade; 
  
  
  public Friend(String skin, Position pos, Zone zone, String name, int health, int maxHealth, Map<String, List<Item>> trade) {
    Objects.requireNonNull(skin);
    Objects.requireNonNull(pos);
    Objects.requireNonNull(zone);
    Objects.requireNonNull(health);
    this.skin = skin;
    this.name = name;
    this.pos = pos;
    this.zone = zone;
    this.health = health;
    this.maxHealth = health;
    this.trade = trade;
  }
  
  public Friend(String skin, Position pos, Zone zone, String name, int health, Map<String, List<Item>> trade) {
    this(skin, pos, zone, name, health, health, trade);
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
  
  public int maxHealth() {
  	return maxHealth;
  }
  
  public void setPos(Position pos) {
  	this.pos = pos;
  }
  
  public Map<String, List<Item>> trade() {
    return trade;
  }
  
}
