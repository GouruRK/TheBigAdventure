package parser;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import game.Kind;
import game.entity.item.Item;
import game.entity.mob.Behaviour;
import util.Direction;
import util.Position;
import util.Zone;

public class ElementAttributes {
  private String name = null;
  private String skin = null;
  private boolean player = false;
  private Position pos = null;
  private int health = -1;
  private Kind kind = Kind.UNKNOWN;
  private Zone zone = null;
  private Behaviour behaviour = Behaviour.UNKNOWN;
  private int damage = -1;
  private String text = null;
  private List<Item> steal = null;
  private Map<String, List<Item>> trade = null;
  private Item locked = null;
  private Direction flow = Direction.UNKNOWN;
  private boolean phantomized = false;
  private String teleport = null;
  
  private static <E> void checkNull(E object, String name) throws TokenException {
    if (object != null) {
      throw new TokenException(name + " is already register");
    }
  }
  
  public void setName(String name) throws TokenException {
    Objects.requireNonNull(name);
    ElementAttributes.checkNull(this.name, "Name");
    this.name = name;
  }
  
  public void setSkin(String skin) throws TokenException {
    Objects.requireNonNull(skin);
    ElementAttributes.checkNull(this.skin, "Skin");
    this.skin = skin;
  }
  
  public void setPlayer() throws TokenException {
    if (player) {
      throw new TokenException("Player is already register");
    }
    player = true;
  }
  
  public void setPlayer(String player) throws TokenException {
    Objects.requireNonNull(player);
    if ("false".equals(player)) {
      return;
    } else if ("true".equals(player)) {
      setPlayer();
      return;
    }
    throw new TokenException("Unknown value for 'player'");
  }
  
  public void setPosition(Position pos) throws TokenException {
    Objects.requireNonNull(pos);
    ElementAttributes.checkNull(this.pos, "Position");
    this.pos = pos;
  }
  
  public void setHealth(int health) throws TokenException {
    if (health <= 0) {
      throw new TokenException("Invalid health");
    }
    if (this.health > 0) {
      throw new TokenException("Health already register");
    }
    this.health = health;
  }
  
  public void setHealth(String health) throws TokenException {
    Objects.requireNonNull(health);
    setHealth(Integer.parseInt(health));
  }
  
  public void setKind(Kind kind) throws TokenException {
    if (this.kind != Kind.UNKNOWN) {
      throw new TokenException("Kind is already register");
    }
    if (kind == Kind.UNKNOWN) {
      throw new TokenException("Unknown kind");
    }
    this.kind = kind;
  }
  
  public void setKind(String kind) throws TokenException {
    Objects.requireNonNull(kind);
    Kind k = Kind.get(kind);
    if (k == Kind.UNKNOWN) {
      throw new TokenException("Unknown kind '" + kind + "'");
    }
    setKind(k);
  }
  
  public void setZone(Zone zone) throws TokenException {
    Objects.requireNonNull(zone);
    ElementAttributes.checkNull(this.zone, "Zone");
    this.zone = zone;
  }
  
  public void setBehaviour(Behaviour behaviour) throws TokenException {
    if (this.behaviour != Behaviour.UNKNOWN) {
      throw new TokenException("Behaviour already register");
    }
    if (behaviour == Behaviour.UNKNOWN) {
      throw new TokenException("Unkown behaviour");
    }
    this.behaviour = behaviour;
  }
  
  public void setBehaviour(String behaviour) throws TokenException {
    Objects.requireNonNull(behaviour);
    setBehaviour(Behaviour.get(behaviour));
  }
  
  public void setDamage(int damage) throws TokenException {
    if (damage <= 0) {
      throw new TokenException("Invalid damage");
    }
    if (this.damage > 0) {
      throw new TokenException("Damage already register");
    }
    this.damage = damage;
  }
  
  public void setDamage(String damage) throws TokenException {
    Objects.requireNonNull(damage);
    setDamage(Integer.parseInt(damage));
  }
  
  public void setText(String text) throws TokenException {
    Objects.requireNonNull(text);
    ElementAttributes.checkNull(this.text, "Text");
    this.text = text.stripIndent();
  }
  
  public void setSteal(List<Item> steal) throws TokenException {
    Objects.requireNonNull(steal);
    ElementAttributes.checkNull(this.steal, "Steal table");
    this.steal = steal;
  }
  
  public void setTrade(Map<String, List<Item>> trade) throws TokenException {
    Objects.requireNonNull(trade);
    ElementAttributes.checkNull(this.trade, "Trade table");
    this.trade = trade;
  }
  
  public void setLocked(Item item) throws TokenException {
    Objects.requireNonNull(item);
    ElementAttributes.checkNull(this.locked, "Item lock");
    this.locked = item;
  }
  
  public void setFlow(Direction flow) throws TokenException {
    if (this.flow != Direction.UNKNOWN) {
      throw new TokenException("Flow is aldready register");
    }
    if (flow == Direction.UNKNOWN) {
      throw new TokenException("Unknown direction");
    }
    this.flow = flow;
  }
  
  public void setFlow(String flow) throws TokenException {
    setFlow(Direction.get(flow));
  }
  
  public void setPhantomize() throws TokenException {
    if (phantomized) {
      throw new TokenException("Phantomize is already register");
    }
    this.phantomized = true;
  }
  
  public void setPhantomize(String phantomize) throws TokenException {
    Objects.requireNonNull(phantomize);
    if ("false".equals(phantomize)) {
      return;
    } else if ("true".equals(phantomize)) {
      setPhantomize();
    }
    throw new TokenException("Unknown value for 'phantomize'");
  }
  
  public void setTeleport(String path) throws TokenException {
    Objects.requireNonNull(path);
    ElementAttributes.checkNull(this.teleport, "Teleport");
    this.teleport = path;
  }
  
}
