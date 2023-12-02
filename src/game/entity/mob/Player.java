package game.entity.mob;

import java.util.Objects;

import util.Position;

public class Player {

  private final String skin;
  private final Position pos;
  
  public Player(String skin, Position pos) {
    Objects.requireNonNull(skin);
    Objects.requireNonNull(pos);
    this.skin = skin;
    this.pos = pos;
  }

  public String skin() {
    return skin;
  }

  public Position pos() {
    return pos;
  }
  
  @Override
  public String toString() {
    return "Player(skin: " + skin + ", " + pos.toString() + ")";
  }
  
}
