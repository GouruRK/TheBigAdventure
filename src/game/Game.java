package game;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import game.environnement.Environnement;
import parser.ElementAttributes;
import parser.EncodingRow;
import parser.TokenException;
import util.Position;

public class Game {
  
  private final Position size;
  private final Environnement[][] field;
  private List<ElementAttributes> lst;
  
  
  public Game(Position size, Map<Character, EncodingRow> encodings, List<char[]> data) throws TokenException {
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
  
  private void applyData(Map<Character, EncodingRow> encodings, List<char[]> data) throws TokenException {
    int y = 0, x = 0;
    EncodingRow row;
    Environnement env;

    for (char[] line: data) {
      x = 0;
      for (char c: line) {
        if (c == ' ') {
          addEnvironnementToField(new Position(x, y), null);
        } else {
          row = encodings.getOrDefault(c, null);
          if (row == null) {
            throw new TokenException("Unknonw code '" + c + "' while creating the map");
          }
          
          env = Environnement.createEnvironnement(row, new Position(x, y));
          
          if (env == null) throw new TokenException("Element '" + row.skin() + "' is not a map element");
          if (!addEnvironnementToField(env.pos(), env)) throw new TokenException("Invalid map size");
        }
        x++;
      }
      y++;
    }
  }
  
  public void addElements(List<ElementAttributes> lst) {
    this.lst = lst;
  }
  
  public boolean isInside(Position pos) {
    return (0 <= pos.x() && pos.x() < size.x())
        && (0 <= pos.y() && pos.y() < size.y());
  }
  
}
