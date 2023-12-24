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

  //------- Other -------
  
  @Override
  public boolean equals(Object obj) {
    return obj instanceof Block block
        && block.skin().equals(skin)
        && block.pos().equals(pos)
        && block.standable() == standable
        && block.hashCode() == hashCode();
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(skin, pos, standable);
  }
  
}
