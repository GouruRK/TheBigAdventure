package game;

import java.util.Map;
import java.util.Objects;

import game.environnement.Environnement;
import util.Position;

public class Game {
  
  private final Position size;
  private final Environnement[][] field;
  
  private void applyData(Map<String, GameObjectID> encodings, String data) {
    
  }
  
  public Game(Position size, Map<String, GameObjectID> encodings, String data) {
    Objects.requireNonNull(size);
    Objects.requireNonNull(encodings);
    Objects.requireNonNull(data);
    this.size = size;
    this.field = new Environnement[(int) size.x()][(int) size.y()];
    applyData(encodings, data);
  }
  
}
