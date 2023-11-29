package game.environnement;

import java.util.Objects;

import util.Position;

public record Block(String skin, Position pos, boolean standable) implements Environnement {
  
  public Block {
    Objects.requireNonNull(skin);
    Objects.requireNonNull(pos);
  }
  
  @Override
  public boolean standable() {
    return standable;
  }
  
}
