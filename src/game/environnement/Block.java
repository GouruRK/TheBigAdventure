package game.environnement;

import java.util.Objects;

import util.Position;

public record Block(String skin, Position pos, boolean standable) implements Environnement {
  
  //------- Constructors -------
  
  public Block {
    Objects.requireNonNull(skin);
    Objects.requireNonNull(pos);
  }
  
  //------- Getter -------
  
  @Override
  public boolean standable() {
    return standable;
  }
  
}
