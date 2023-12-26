package game.environnement;

import java.util.Objects;

import game.entity.item.Item;
import util.Position;

public class Gate implements Environnement {
  
  //------- Fields -------
  
  private final String skin;
  private final Position pos;
  private final Item key;
  private boolean open;
  
  //------- Constructors -------
  
  public Gate(String skin, Position pos, Item key, boolean open) {
    Objects.requireNonNull(skin);
    Objects.requireNonNull(pos);
    this.skin = skin;
    this.pos = pos;
    this.key = key;
    this.open = open;
  }
  
  public Gate(String skin, Position pos, Item key) {
    this(skin, pos, key, false);
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
  
  public Item key() {
    return key;
  }
  
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
        && gate.skin().equals(skin)
        && gate.pos().equals(pos)
        && (gate.key() == null ? gate.key() == key: gate.key().equals(key))
        && gate.open() == open
        && gate.hashCode() == hashCode();
  }
  
  
  @Override
  public int hashCode() {
    return Objects.hash(skin, pos, key, open);
  }
}
