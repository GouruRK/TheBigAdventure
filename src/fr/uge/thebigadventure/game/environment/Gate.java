package fr.uge.thebigadventure.game.environment;

import java.util.Objects;

import fr.uge.thebigadventure.game.Game;
import fr.uge.thebigadventure.game.GameObjectID;
import fr.uge.thebigadventure.game.entity.item.Item;
import fr.uge.thebigadventure.util.Position;

public class Gate implements Environment {
  
  //------- Fields -------
  
  private final String skin;
  private final Position pos;
  private final Item key;
  private final Game teleports;
  private boolean open;
  
  //------- Constructors -------
  
  public Gate(String skin, Position pos, Item key, Game teleports, boolean open) {
    Objects.requireNonNull(skin);
    Objects.requireNonNull(pos);
    this.skin = skin;
    this.pos = pos;
    this.key = key;
    this.teleports = teleports;
    this.open = open;
  }
  
  public Gate(String skin, Position pos, Item key, Game teleports) {
    this(skin, pos, key, teleports, false);
  }
  
  public Gate(String skin, Position pos, Item key) {
    this(skin, pos, key, null, false);
  }
  
  public Gate(String skin, Position pos) {
    this(skin, pos, null, null, false);
  }

  //------- Getter -------
  
  @Override
  public String skin() {
    return skin;
  }

  @Override
  public Position pos() {
    return pos;
  }
  
  public boolean open() {
    return open;
  }
  
  /**
   * Get teleports. Can be null
   * @return
   */
  public Game teleports() {
    return teleports;
  }
  
  /**
   * Return item to open the gate
   * @return
   */
  public Item key() {
    return key;
  }
  
  /**
   * Open the gate with the given item
   * @param item
   */
  public void open(Item item) {
    if (key.equals(item)) {
      open = !open;
    }
  }
  
  @Override
  public boolean standable() {
    return open;
  }
  
  //------- Other -------
  
  @Override
  public boolean equals(Object obj) {
    return obj instanceof Gate gate
        && gate.skin().equalsIgnoreCase(skin)
        && gate.pos().equals(pos)
        && (gate.key() == null ? gate.key() == key: gate.key().equals(key))
        && gate.open() == open
        && gate.hashCode() == hashCode();
  }
  
  
  @Override
  public int hashCode() {
    return Objects.hash(skin.toUpperCase(), pos, key, open);
  }

  @Override
  public GameObjectID id() {
    return GameObjectID.GATE;
  }
}
