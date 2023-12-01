package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import game.entity.item.DroppedItem;
import game.entity.item.Item;
import game.entity.mob.Mob;
import game.entity.mob.Player;
import game.environnement.Environnement;
import parser.ElementAttributes;
import parser.EncodingRow;
import parser.TokenException;
import util.Position;

public class Game {
  
  private final Position size;
  private final Environnement[][] field;
  private final ArrayList<Mob> mobs;
  private final ArrayList<DroppedItem> items;
  private Player player;
  
  public Game(Position size, Map<Character, EncodingRow> encodings, List<char[]> data) throws TokenException {
    Objects.requireNonNull(size);
    Objects.requireNonNull(encodings);
    Objects.requireNonNull(data);
    this.size = size;
    this.field = new Environnement[(int) size.y()][(int) size.x()];
    mobs = new ArrayList<Mob>(); 
    items = new ArrayList<DroppedItem>();
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
    EncodingRow row;
    
    for (int y = 0; y < data.size(); y++) {
      for (int x = 0; x < data.get(y).length; x++) {
        if (data.get(y)[x] == ' ') {
          addEnvironnementToField(new Position(x, y), null);
          continue;
        }
        row = encodings.getOrDefault(data.get(y)[x], null);
        if (row == null) {
          throw new TokenException("Unknonw code '" + data.get(y)[x] + "' while creating the map");
        }
        addElement(row, x, y);
      }
    }
  }

  private void addElement(EncodingRow row, int x, int y) throws TokenException {
    Environnement env;
    DroppedItem item;
    Position pos = new Position(x, y);
    
    if ((env = Environnement.createEnvironnement(row, pos)) != null) {
      addEnvironnementToField(env.pos(), env);
    } else if ((item = Item.createDroppedItem(row, pos)) != null) {
      items.add(item);
    } else {      
      throw new TokenException("Element '" + row.skin() + "' is not a map element and cannot be given from data and encodings");
    }
  }
  
  private void addZoneOfElements(ElementAttributes elem) {
    System.out.println("Zone Elements : " + elem.getSkin());
  }
  
  private void addElement(ElementAttributes element) throws TokenException {
    Environnement env;
    DroppedItem item;
    Mob mob;
    
    if ((env = Environnement.createEnvironnement(element)) != null) {
      addEnvironnementToField(env.pos(), env);
    } else if((item = Item.createDroppedItem(element)) != null) {
      items.add(item);
    } else if ((mob = Mob.createMob(element)) != null) {
      mobs.add(mob);
    } else {      
      throw new TokenException("Element '" + element.getSkin() + "' is not a map element");
    }
  }
  
  public void addElements(List<ElementAttributes> lst) throws TokenException {
    for (ElementAttributes element: lst) {
      if (element.isPlayer()) {
        this.player = new Player(element.getSkin(), element.getPosition());
      } else if (element.hasZone() && element.getID() != GameObjectID.MOB) {
        addZoneOfElements(element);
      } else {
        addElement(element);
      }
    }
  }
  
  public boolean isInside(Position pos) {
    return (0 <= pos.x() && pos.x() < size.x())
        && (0 <= pos.y() && pos.y() < size.y());
  }
  
}
