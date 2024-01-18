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

/**
 * Represents all attributes an element can contains
 */
public class ElementAttributes {
  
  /**
   * Parsed game element id
   */
  private GameObjectID id = GameObjectID.UNKNOWN;
  
  /**
   * Parsed element  skin
   */
  private String skin = null;
  
  /**
   * Parsed element name
   */
  private String name = null;
  
  /**
   * Check if parsed element is the player
   */
  private boolean player = false;
  
  /**
   * Parsed element position
   */
  private Position pos = null;
  
  /**
   * Parsed element health
   * Note that an element health cannot be negative because '-' is not a valid token
   */
  private int health = -1;
  
  /**
   * Parsed element kind
   */
  private Kind kind = Kind.UNKNOWN;
  
  /**
   * Parsed element zone
   */
  private Zone zone = null;
  
  /**
   * Parsed element behaviour
   */
  private Behaviour behaviour = Behaviour.UNKNOWN;
  
  /**
   * Parsed element damage
   */
  private int damage = -1;
  
  /**
   * Parsed element text
   */
  private Text text = null;
  
  /**
   * Parsed element steal list
   */
  private List<Item> steal = null;
  
  /**
   * Parsed element trade map
   */
  private Map<Item, List<Item>> trade = null;
  
  /**
   * Parsed element the item to unlock it
   */
  private Item locked = null;
  
  /**
   * Parsed element flow
   */
  private Direction flow = Direction.UNKNOWN;
  
  /**
   * Parsed element phantomized
   */
  private boolean phantomized = false;
  
  /**
   * Parsed element teleport
   */
  private Game teleport = null;
  
  /**
   * Tells if gate has the "back" attribute
   */
  private boolean back = false;
  
  /**
   * Check if an element is null. Used to generalize verification if 
   * attributes are already register
   * @param <E> object
   * @param object object to check
   * @param name name of the object (use to throw error)
   * @throws TokenException
   */
  private static <E> void checkNull(E object, String name) throws TokenException {
    if (object != null) {
      throw new TokenException(name + " is already register");
    }
  }

  /**
   * Check if element has the skin attribute
   * Note that for an element to be valid, the skin attribute is mandatory
   * @return
   */
  public boolean hasSkin() {
    return skin != null;
  }

  /**
   * Check if element has the name attribute
   * @return
   */
  public boolean hasName() {
    return name != null;
  }
  
  /**
   * Check if element is the player name attribute
   * @return
   */
  public boolean isPlayer() {
    return player;
  }
  
  /**
   * Check if element has the position attribute
   * @return
   */
  public boolean hasPosition() {
    return pos != null;
  }
  
  /**
   * Check if element has the health attribute
   * @return
   */
  public boolean hasHealth() {
    return health != -1;
  }
  
  /**
   * Check if element has the kind attribute
   * @return
   */
  public boolean hasKind() {
    return kind != Kind.UNKNOWN;
  }
  
  /**
   * Check if element has the zone attribute
   * @return
   */
  public boolean hasZone() {
    return zone != null;
  }
  
  /**
   * Check if element has the behaviour attribute
   * @return
   */
  public boolean hasBehaviour() {
    return behaviour != Behaviour.UNKNOWN;
  }
  
  /**
   * Check if element has the damage attribute
   * @return
   */
  public boolean hasDamage() {
    return damage != -1;
  }
  
  /**
   * Check if element has the text attribute
   * @return
   */
  public boolean hasText() {
    return text != null;
  }
  
  /**
   * Check if element has the steal attribute
   * @return
   */
  public boolean hasSteal() {
    return steal != null;
  }
  
  /**
   * Check if element has the trade attribute
   * @return
   */
  public boolean hasTrade() {
    return trade != null;
  }
  
  /**
   * Check if element has the locked attribute
   * @return
   */
  public boolean hasLocked() {
    return locked != null;
  }
  
  /**
   * Check if element has the flow attribute
   * @return
   */
  public boolean hasFlow() {
    return flow != Direction.UNKNOWN;
  }
  
  /**
   * Check if element can phantomized the player attribute
   * @return
   */
  public boolean hasPhantomized() {
    return phantomized;
  }
  
  /**
   * Check if element has the teleport attribute
   * @return
   */
  public boolean hasTeleport() {
    return teleport != null;
  }
  
  /**
   * Check if element has the back attribute
   * @return
   */
  public boolean hasBack() {
    return back != false;
  }
  
  /**
   * Get the id attribute
   * @return
   */
  public GameObjectID getID() {
    return id;
  }

  /**
   * Get the skin attribute
   * @return
   */
  public String skin() {
    return skin;
  }
  
  /**
   * Get the name attribute
   * @return
   */
  public String name() {
    return name;
  }
  
  /**
   * Get the player attribute
   * @return
   */
  public boolean player() {
    return player;
  }
  
  /**
   * Get the position attribute
   * @return
   */
  public Position position() {
    return pos;
  }
  
  /**
   * Get the health attribute
   * @return
   */
  public int health() {
    if (hasHealth()) return health;
    return 0;
  }
  
  /**
   * Get the kind attribute
   * @return
   */
  public Kind kind() {
    return kind;
  }
  
  /**
   * Get the zone attribute
   * @return
   */
  public Zone zone() {
    if (hasZone()) {
      return zone;
    }
    return new Zone(position(), position());
  }
  
  /**
   * Get the behaviour attribute
   * @return
   */
  public Behaviour behaviour() {
    if (hasBehaviour()) return behaviour;
    return Behaviour.STROLL;
  }
  
  /**
   * Get the damage attribute
   * @return
   */
  public int damage() {
    return damage;
  }
  
  /**
   * Get the text attribute
   * @return
   */
  public Text text() {
    return text;
  }
  
  /**
   * Get the steal attribute
   * @return
   */
  public List<Item> steal() {
    return steal;
  }
  
  /**
   * Get the trade attribute
   * @return
   */
  public Map<Item, List<Item>> trade() {
    return trade;
  }
  
  /**
   * Get the locked attribute
   * @return
   */
  public Item locked() {
    return locked;
  }
  
  /**
   * Get the flow attribute
   * @return
   */
  public Direction flow() {
    if (hasFlow()) return flow;
    return Direction.NONE;
  }
  
  /**
   * Get the phantomized attribute
   * @return
   */
  public boolean phantomized() {
    return phantomized;
  }
  
  /**
   * Get the teleport attribute
   * @return
   */
  public Game teleport() {
    return teleport;
  }
  
  /**
   * Get the back attribute
   * @return
   */
  public boolean back() {
    return back;
  }
  
  /**
   * Set skin attribute
   * @param skin
   * @throws TokenException if skin is already register
   */
  public void setSkin(String skin) throws TokenException {
    Objects.requireNonNull(skin);
    checkNull(this.skin, "Skin");
    this.skin = skin;
    id = GameObject.fromName(skin);
  }
  
  /**
   * Set name attribute
   * @param name
   * @throws TokenException if name is already register
   */
  public void setName(String name) throws TokenException {
    Objects.requireNonNull(name);
    checkNull(this.name, "Name");
    this.name = name;
  }
  
  /**
   * Set player attribute
   * @throws TokenException if player is already register
   */
  private void setPlayer() throws TokenException {
    if (player) {
      throw new TokenException("Player is already register");
    }
    player = true;
  }
  
  /**
   * Set player attribute
   * @param player true or false
   * @throws TokenException if player is already register or if given value isn't recognize
   */
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
  
  /**
   * Set position attribute
   * @param pos
   * @throws TokenException if position is already register
   */
  public void setPosition(Position pos) throws TokenException {
    Objects.requireNonNull(pos);
    checkNull(this.pos, "Position");
    this.pos = pos;
  }
  
  /**
   * Check the validity of health and set it to the element instance
   * @param health
   * @throws TokenException if health is already register
   */
  private void setHealth(int health) throws TokenException {
    if (health <= 0) {
      throw new TokenException("Invalid health");
    }
    if (this.health > 0) {
      throw new TokenException("Health already register");
    }
    this.health = health;
  }
  
  /**
   * Set health attribute
   * @param health
   * @throws TokenException if name is already register
   */
  public void setHealth(String health) throws TokenException {
    Objects.requireNonNull(health);
    setHealth(Integer.parseInt(health));
  }
  
  /**
   * Check the validity of the kind element and set it to the object instance
   * @param kind
   * @throws TokenException if kind is already register
   */
  private void setKind(Kind kind) throws TokenException {
    if (this.kind != Kind.UNKNOWN) {
      throw new TokenException("Kind is already register");
    }
    if (kind == Kind.UNKNOWN) {
      throw new TokenException("Unknown kind");
    }
    this.kind = kind;
  }
  
  /**
   * Set kind attribute
   * @param kind
   * @throws TokenException if kind is already register
   */
  public void setKind(String kind) throws TokenException {
    Objects.requireNonNull(kind);
    Kind k = Kind.get(kind);
    if (k == Kind.UNKNOWN) {
      throw new TokenException("Unknown kind '" + kind + "'");
    }
    setKind(k);
  }
  
  /**
   * Set zone attribute
   * @param zone
   * @throws TokenException if zone is already register
   */
  public void setZone(Zone zone) throws TokenException {
    Objects.requireNonNull(zone);
    checkNull(this.zone, "Zone");
    this.zone = zone;
  }
  
  /**
   * Check the behaviour's validity and set it to the object instance
   * @param behaviour
   * @throws TokenException if behaviour is already register
   */
  private void setBehaviour(Behaviour behaviour) throws TokenException {
    if (this.behaviour != Behaviour.UNKNOWN) {
      throw new TokenException("Behaviour already register");
    }
    if (behaviour == Behaviour.UNKNOWN) {
      throw new TokenException("Unkown behaviour");
    }
    this.behaviour = behaviour;
  }
  
  /**
   * Set behaviour attribute
   * @param behaviour
   * @throws TokenException if behaviour is already register
   */
  public void setBehaviour(String behaviour) throws TokenException {
    Objects.requireNonNull(behaviour);
    setBehaviour(Behaviour.get(behaviour));
  }
  
  /**
   * Check the damage's validity and set it to the object instance
   * @param damage
   * @throws TokenException if damage is already register
   */
  private void setDamage(int damage) throws TokenException {
    if (damage <= 0) {
      throw new TokenException("Invalid damage");
    }
    if (this.damage > 0) {
      throw new TokenException("Damage already register");
    }
    this.damage = damage;
  }
  
  /**
   * Set damage attribute
   * @param damage
   * @throws TokenException if damage is already register
   */
  public void setDamage(String damage) throws TokenException {
    Objects.requireNonNull(damage);
    setDamage(Integer.parseInt(damage));
  }
  
  /**
   * Set text attribute
   * @param text
   * @throws TokenException if text is already register
   */
  public void setText(String text) throws TokenException {
    Objects.requireNonNull(text);
    checkNull(this.text, "Text");
    
    this.text = new Text(text);
  }
  
  /**
   * Set steal attribute
   * @param steal
   * @throws TokenException if steal is already register
   */
  public void setSteal(List<Item> steal) throws TokenException {
    Objects.requireNonNull(steal);
    checkNull(this.steal, "Steal table");
    this.steal = steal;
  }
  
  /**
   * Set trade attribute
   * @param trade
   * @throws TokenException if trade is already register
   */
  public void setTrade(Map<Item, List<Item>> trade) throws TokenException {
    Objects.requireNonNull(trade);
    checkNull(this.trade, "Trade table");
    this.trade = trade;
  }
  
  /**
   * Set locked attribute
   * @param item item to unlock the element
   * @throws TokenException if locked is already register
   */
  public void setLocked(Item item) throws TokenException {
    Objects.requireNonNull(item);
    checkNull(this.locked, "Item lock");
    this.locked = item;
  }
  
  /**
   * Check flow's validity and set it to the object instance
   * @param flow
   * @throws TokenException if flow is already register
   */
  private void setFlow(Direction flow) throws TokenException {
    if (this.flow != Direction.UNKNOWN) {
      throw new TokenException("Flow is aldready register");
    }
    if (flow == Direction.UNKNOWN) {
      throw new TokenException("Unknown direction");
    }
    this.flow = flow;
  }
  
  /**
   * Set flow attribute
   * @param flow
   * @throws TokenException if flow is already register
   */
  public void setFlow(String flow) throws TokenException {
    setFlow(Direction.get(flow));
  }
  
  /**
   * Set phantomized attribute
   * @throws TokenException if phantomized is already register
   */
  public void setPhantomize() throws TokenException {
    if (phantomized) {
      throw new TokenException("Phantomize is already register");
    }
    this.phantomized = true;
  }
  
  /**
   * Check phantomized's validity and set it the object instance
   * @param phantomize
   * @throws TokenException if name is already register
   */
  public void setPhantomize(String phantomize) throws TokenException {
    Objects.requireNonNull(phantomize);
    if ("false".equals(phantomize)) {
      return;
    } else if ("true".equals(phantomize)) {
      setPhantomize();
    }
    throw new TokenException("Unknown value for 'phantomize'");
  }
  
  /**
   * Set teleport attribute
   * @param game game to be teleported
   * @throws TokenException if teleport is already register
   */
  public void setTeleport(Game game) throws TokenException {
    Objects.requireNonNull(game);
    checkNull(this.teleport, "Teleport");
    this.teleport = game;
  }
  
  
  /**
   * Set back attribute
   * @throws TokenException if back is already register
   */
  public void setBack() throws TokenException {
    if (back) {
      throw new TokenException("Back is already register");
    }
    this.back = true;
  }
  
  /**
   * Check if the object is valid
   * @return
   */
  public boolean isValid() {
    boolean validateHealth = true;
    if (id == GameObjectID.MOB && health == -1) {
      return false;
    }
    return skin != null && (pos != null || zone != null) && validateHealth;
  }
  
}
