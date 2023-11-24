package game.entity.mob;

import java.util.Objects;

import util.Direction;
import util.Position;
import util.Zone;

public class Friend implements Mob {

  private final String skin;
  private final String name;
  private final Zone zone; 
  private Position pos;
  private Direction lookAt;
  
  public Friend(String skin, String name, Position pos, Zone zone, Direction lookAt) {
    Objects.requireNonNull(skin);
    Objects.requireNonNull(name);
    Objects.requireNonNull(pos);
    Objects.requireNonNull(zone);
    this.skin = skin;
    this.name = name;
    this.pos = pos;
    this.zone = zone;
    this.lookAt = lookAt;
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
