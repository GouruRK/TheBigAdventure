package game.entity.mob;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import game.GameObjectID;
import game.entity.item.Item;
import util.Direction;
import util.Position;
import util.Text;
import util.Zone;

public class Friend implements Mob {

  //------- Fields -------
  
  private final String skin;
  private final String name;
  private final Zone zone; 
  private final int maxHealth;
  private final Map<Item, List<Item>> trade;
  private final Text text;
  private Position pos;
  private int health;
  private Direction facing;
  
  //------- Constructors -------
  
  public Friend(String skin, Position pos, Zone zone, String name, int health, int maxHealth, Map<Item, List<Item>> trade, Text text) {
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
    this.facing = Direction.EAST;
    this.text = text;
  }
  
  public Friend(String skin, Position pos, Zone zone, String name, int health, Map<Item, List<Item>> trade, Text text) {
    this(skin, pos, zone, name, health, health, trade, text);
  }
  
  //------- Getter -------
  
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
  
  public Direction facing() {
    return facing;
  }
  
  public Map<Item, List<Item>> trade() {
    return trade;
  }

  public boolean hasText() {
    return text != null;
  }
  
  public boolean hasTrade() {
    return trade != null;
  }
  
  public Text text() {
    return text;
  }

  public GameObjectID id() {
    return GameObjectID.FRIEND;
  }
  
  // ------- Modifiers -------
  
  public void setPos(Position pos) {
  	this.pos = pos;
  }
  
  public void setFacing(Direction dir) {
    this.facing = dir;
  }
  
  public void takeDamage(int damage) {
    this.health -= damage;
  }
  
}
