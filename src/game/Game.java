package game;

import java.util.Map;
import java.util.Objects;

import game.environnement.Environnement;
import parser.EncodingRow;
import parser.TokenException;
import util.Position;

public class Game {
  
  private final Position size;
  private final Environnement[][] field;
  
  
  public Game(Position size, Map<String, EncodingRow> encodings, String data) throws TokenException {
    Objects.requireNonNull(size);
    Objects.requireNonNull(encodings);
    Objects.requireNonNull(data);
    this.size = size;
    this.field = new Environnement[(int) size.y()][(int) size.x()];
    applyData(encodings, data);
  }
  
  
  private boolean addEnvironnementToField(Position pos, Environnement env) {
    if (isInside(pos)) {
      field[(int) pos.y()][(int) pos.x()] = env;
      return true;
    }
    return false;
  }
  
  private void applyData(Map<String, EncodingRow> encodings, String data) throws TokenException {
    String[] temp = data.split("\n");
    int y = 0, x = 0;
    EncodingRow row;
    Environnement env;

    for (String line: temp) {
      x = 0;
      for (char c: line.toCharArray()) {
        row = encodings.getOrDefault(c, null);
        if (row == null) throw new TokenException("Unknonw code '" + c + "' while creating the map");
        
        env = Environnement.createEnvironnement(row, new Position(x, y));
        
        if (env == null) throw new TokenException("Element '" + row.skin() + "' is not a map element");
        if (!addEnvironnementToField(env.pos(), env)) throw new TokenException("Invalid map size");
        
        x++;
      }
      y++;
    }
  }
  
  public boolean isInside(Position pos) {
    return (0 <= pos.x() && pos.x() < size.x())
        && (0 <= pos.y() && pos.y() < size.y());
  }
  
}
