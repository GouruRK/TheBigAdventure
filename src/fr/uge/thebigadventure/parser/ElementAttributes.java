package fr.uge.thebigadventure.parser;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import fr.uge.thebigadventure.game.Game;
import fr.uge.thebigadventure.game.GameObject;
import fr.uge.thebigadventure.game.GameObjectID;
import fr.uge.thebigadventure.game.Kind;
import fr.uge.thebigadventure.game.entity.item.Item;
import fr.uge.thebigadventure.game.entity.mob.Behaviour;
import fr.uge.thebigadventure.util.Direction;
import fr.uge.thebigadventure.util.Position;
import fr.uge.thebigadventure.util.Text;
import fr.uge.thebigadventure.util.Zone;

public class ElementAttributes {
  private GameObjectID id = GameObjectID.UNKNOWN;
  private String skin = null;
  private String name = null;
  private boolean player = false;
  private Position pos = null;
  private int health = -1;
  private Kind kind = Kind.UNKNOWN;
  private Zone zone = null;
  private Behaviour behaviour = Behaviour.UNKNOWN;
  private int damage = -1;
  private Text text = null;
  private List<Item> steal = null;
  private Map<Item, List<Item>> trade = null;
  private Item locked = null;
  private Direction flow = Direction.UNKNOWN;
  private boolean phantomized = false;
  private Game teleport = null;
  private boolean back = false;
  
  private static <E> void checkNull(E object, String name) throws TokenException {
    if (object != null) {
      throw new TokenException(name + " is already register");
    }
  }

  public boolean hasSkin() {
    return skin != null;
  }

  public boolean hasName() {
    return name != null;
  }
  
  public boolean isPlayer() {
    return player;
  }
  
  public boolean hasPosition() {
    return pos != null;
  }
  
  public boolean hasHealth() {
    return health != -1;
  }
  
  public boolean hasKind() {
    return kind != Kind.UNKNOWN;
  }
  
  public boolean hasZone() {
    return zone != null;
  }
  
  public boolean hasBehaviour() {
    return behaviour != Behaviour.UNKNOWN;
  }
  
  public boolean hasDamage() {
    return damage != -1;
  }
  
  public boolean hasText() {
    return text != null;
  }
  
  public boolean hasSteal() {
    return steal != null;
  }
  
  public boolean hasTrade() {
    return trade != null;
  }
  
  public boolean hasLocked() {
    return locked != null;
  }
  
  public boolean hasFlow() {
    return flow != Direction.UNKNOWN;
  }
  
  public boolean hasPhantomized() {
    return phantomized;
  }
  
  public boolean hasTeleport() {
    return teleport != null;
  }
  
  public boolean hasBack() {
    return back != false;
  }
  
  public GameObjectID getID() {
    return id;
  }
  
  public String getSkin() {
    return skin;
  }
  
  public String getName() {
    return name;
  }
  
  public boolean getPlayer() {
    return player;
  }
  
  public Position getPosition() {
    return pos;
  }
  
  public int getHealth() {
    if (hasHealth()) return health;
    return 0;
  }
  
  public Kind getKind() {
    return kind;
  }
  
  public Zone getZone() {
    if (hasZone()) {
      return zone;
    }
    return new Zone(getPosition(), getPosition());
  }
  
  public Behaviour getBehaviour() {
    if (hasBehaviour()) return behaviour;
    return Behaviour.STROLL;
  }
  
  public int getDamage() {
    return damage;
  }
  
  public Text getText() {
    return text;
  }
  
  public List<Item> getSteal() {
    return steal;
  }
  
  public Map<Item, List<Item>> getTrade() {
    return trade;
  }
  
  public Item getLocked() {
    return locked;
  }
  
  public Direction getFlow() {
    if (hasFlow()) return flow;
    return Direction.NONE;
  }
  
  public boolean getPhantimized() {
    return phantomized;
  }
  
  public Game getTeleport() {
    return teleport;
  }
  
  public boolean getBack() {
    return back;
  }
  
  public void setSkin(String skin) throws TokenException {
    Objects.requireNonNull(skin);
    ElementAttributes.checkNull(this.skin, "Skin");
    this.skin = skin;
    id = GameObject.fromName(skin);
  }
  
  public void setName(String name) throws TokenException {
    Objects.requireNonNull(name);
    ElementAttributes.checkNull(this.name, "Name");
    this.name = name;
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
    
    this.text = new Text(text);
  }
  
  public void setSteal(List<Item> steal) throws TokenException {
    Objects.requireNonNull(steal);
    ElementAttributes.checkNull(this.steal, "Steal table");
    this.steal = steal;
  }
  
  public void setTrade(Map<Item, List<Item>> trade) throws TokenException {
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
  
  public void setTeleport(Game game) throws TokenException {
    Objects.requireNonNull(game);
    ElementAttributes.checkNull(this.teleport, "Teleport");
    this.teleport = game;
  }
  
  public void setBack() throws TokenException {
    if (back) {
      throw new TokenException("Back is already register");
    }
    this.back = true;
  }
  
  public boolean isValid() {
    boolean validateHealth = true;
    if (id == GameObjectID.MOB && health == -1) {
      return false;
    }
    return skin != null && (pos != null || zone != null) && validateHealth;
  }
  
}
