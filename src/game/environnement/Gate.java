package game.environnement;

import java.util.Objects;

import game.entity.item.Item;
import util.Position;

public class Gate implements Environnement {
  private final String skin;
  private final Position pos;
  private final Item key;
  private boolean open;
  
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
  
  public Gate(String skin, Position pos, boolean open) {
    this(skin, pos, null, open);
  }

  @Override
  public String skin() {
    return skin;
  }

  @Override
  public Position pos() {
    return pos;
  }
  
}
