package game.entity.mob;

import java.util.Objects;

import util.Position;

public class Player {

  private final String skin;
  private final Position pos;
  private final int max_health;
  private int health;
  
  public Player(String skin, Position pos, int max_health, int health) {
    Objects.requireNonNull(skin);
    Objects.requireNonNull(pos);
    this.max_health = max_health;
    this.health = health;
    this.skin = skin;
    this.pos = pos;
  }

  public int health() {
  	return health;
  }
  
  public int max_health() {
  	return max_health;
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
