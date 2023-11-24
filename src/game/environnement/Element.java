package game.environnement;

import java.util.Objects;

import util.Position;

public record Element(String skin, Position pos) implements Environnement {
  
  public Element {
    Objects.requireNonNull(skin);
    Objects.requireNonNull(pos);
  }
  
}
