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
  private final Position pos;
  private final Map<String, List<Item>> trade; 
  
  
  public Friend(String skin, Position pos, Zone zone, String name, Map<String, List<Item>> trade) {
    Objects.requireNonNull(skin);
    Objects.requireNonNull(pos);
    Objects.requireNonNull(zone);
    this.skin = skin;
    this.name = name;
    this.pos = pos;
    this.zone = zone;
    this.trade = trade;
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
  
  public Map<String, List<Item>> trade() {
    return trade;
  }
  
}
