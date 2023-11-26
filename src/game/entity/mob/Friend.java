package game.entity.mob;

import java.util.Objects;

import util.Position;
import util.Zone;

public class Friend implements Mob {

  private final String skin;
  private final String name;
  private final Zone zone; 
  private Position pos;
  
  
  public Friend(String skin, String name, Position pos, Zone zone) {
    Objects.requireNonNull(skin);
    Objects.requireNonNull(name);
    Objects.requireNonNull(pos);
    Objects.requireNonNull(zone);
    this.skin = skin;
    this.name = name;
    this.pos = pos;
    this.zone = zone;
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
