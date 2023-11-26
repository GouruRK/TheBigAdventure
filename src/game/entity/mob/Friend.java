package game.entity.mob;

import java.util.Objects;

import util.Position;
import util.Zone;

public class Friend implements Mob {

  private final String skin;
  private final String name;
  private final Zone zone; 
  private Position pos;
  
  
  public Friend(String skin, Position pos, Zone zone, String name) {
    Objects.requireNonNull(skin);
    Objects.requireNonNull(pos);
    Objects.requireNonNull(zone);
    this.skin = skin;
    this.name = name;
    this.pos = pos;
    this.zone = zone;
  }
  
  public Friend(String skin, Position pos, Zone zone) {
    this(skin, pos, zone, null);
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
