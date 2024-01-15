package parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import game.Game;
import game.GameObjectID;
import game.entity.item.DroppedItem;
import game.entity.item.Item;
import game.entity.mob.Mob;
import game.entity.mob.Player;
import game.environment.Environment;
import util.Position;
import util.Text;
import util.Zone;

public class GameAttributes {

  // --------- Init ---------
  
  private final ArrayList<ElementAttributes> elements;
  private final ArrayList<Mob> mobs;
  private final ArrayList<DroppedItem> items;
  private final HashMap<Position, Game> teleports;

  private String data = null;
  private Zone zone = null;
  private Map<Character, EncodingRow> encodings = null;
  private Environment[][] field;
  private Player player = null;
  private Position startingPos = null;
  private Position backPos = null;
  
  // --------- Constructors --------- 
  
  public GameAttributes() {
    teleports = new HashMap<Position, Game>();
    elements = new ArrayList<ElementAttributes>();
    mobs = new ArrayList<Mob>();
    items = new ArrayList<DroppedItem>();
  }
  
  // --------- Checker ---------
  
  public boolean hasData() {
    return data != null;
  }
  
  public boolean hasZone() {
    return zone != null;
  }
  
  public boolean hasEncodings() {
    return encodings != null;
  }
  
  public boolean hasGameInfo() {
    return hasData() && hasZone() && hasEncodings();
  }
  
  public boolean hasStartingPos() {
    return startingPos != null;
  }

  public boolean isInsideGrid(Position pos) {
    return zone.isInside(pos);
  }
  
  
  // --------- Setters --------- 
  
  public void setData(String data) throws TokenException {
    Objects.requireNonNull(data);
    if (this.data != null) {
      throw new TokenException("Data already register");
    }
    this.data = data;
  }
  
  public void addElement(ElementAttributes elem) {
    Objects.requireNonNull(elem);
    elements.add(elem);
  }
  
  public void setZone(Zone zone) throws TokenException {
    Objects.requireNonNull(zone);
    if (this.zone != null) {
      throw new TokenException("Size already register");
    }
    this.zone = zone;
  }
  
  public void setEncodings(Map<Character, EncodingRow> encodings) throws TokenException {
    Objects.requireNonNull(encodings);
    if (this.encodings != null) {
      throw new TokenException("Encodings already register");
    }
    this.encodings = encodings;
  }
  
  public void setStartingPos(Position pos) throws TokenException {
    Objects.requireNonNull(pos);
    if (startingPos != null) {
      throw new TokenException("Starting pos already register");
    }
    this.startingPos = pos;
  }
  
  // --------- Check data field --------- 
  
  private void checkFieldSize(List<String> tempField) throws TokenException {
    if (!hasZone()) throw new TokenException("Size of grid is unknown");
    
    int width = (int) zone.bottomRight().x(), height = (int) zone.bottomRight().y();
    
    if (tempField.size() != height) {
      throw new TokenException("Invalid map height : got " + tempField.size() + " expected " + height);
    }
    
    if (!tempField.stream().allMatch(line -> line.length() == width)) {
      String l = tempField.stream().filter(line -> line.length() != width).findFirst().orElse("");
      throw new TokenException("Invalid map width : got " + l.length() + " expected " + width + " at " + l);
    }
    
    field = new Environment[height][width];
  }
  
  private boolean checkFullLineOfField(String line) {
    // check if a line does not contains space
    return line.chars().allMatch(c -> c != ' ');
  }
  
  private void checkFieldConstruction(List<String> field) throws TokenException {
    // top line must be full
    if (!checkFullLineOfField(field.getFirst())) {
      throw new TokenException("First line of data must not contains spaces");
    }
    
    // last line must be full
    if (!checkFullLineOfField(field.getLast())) {
      throw new TokenException("Last line of data must not contains spaces");
    }
    
    if (!field.stream().allMatch(line -> line.charAt(0) != ' ' && line.charAt(line.length() - 1) != ' ')) {
      throw new TokenException("Lines of data must begins and ends with a different character than space");
    }
  }
  
  private Text checkField() throws TokenException {
    Text field = new Text(data, true);
    checkFieldConstruction(field.text());
    checkFieldSize(field.text());
    return field;
  }

  // --------- Create Field ---------
  
  private void createField() throws TokenException {
    if (encodings == null) {
      throw new TokenException("Map encoding is missing");
    }
    Text oldField = checkField();
    String line;
    
    for (int y = 0; y < zone.height(); y++) {
      line = oldField.get(y);
      for (int x = 0; x < zone.width(); x++) {
        if (line.charAt(x) == ' ') {
          field[y][x] = null;
          continue;
        }
        createElement(encodings.getOrDefault(line.charAt(x), null), new Position(x, y));
      }
    }
  }
  
  // --------- Element Creation ---------
  
  private void createElement(EncodingRow row, Position pos) throws TokenException {
    Environment env;
    DroppedItem item;
    
    if ((env = Environment.createEnvironment(row, pos)) != null) {
      field[(int) pos.y()][(int) pos.x()] = env;
    } else if ((item = Item.createDroppedItem(row, pos)) != null) {
      items.add(item);
    } else {
      throw new TokenException("Element '" + row.skin() + "' is not a map element and cannot be given from data and encodings");
    }
  }
  
  private void createElement(ElementAttributes element, Position pos) throws TokenException {
    Environment env;
    DroppedItem item;
    Mob mob;
    
    if ((mob = Mob.createMob(element)) != null) {
      mobs.add(mob);
    } else if((item = Item.createDroppedItem(element)) != null) {
      items.add(item);
    } else if ((env = Environment.createEnvironment(element, pos)) != null) {
      field[(int) env.pos().y()][(int) env.pos().x()] = env;
      if (element.hasTeleport()) {
        teleports.put(element.getPosition(), element.getTeleport());
      } else if (element.hasBack()) {
        backPos = element.getPosition();
      }
    } else {      
      throw new TokenException("Element '" + element.getSkin() + "' is not a map element");
    }
  }
  
  private void createElement(ElementAttributes element) throws TokenException {
    createElement(element, element.getPosition());
  }
  
  private void createElementWithZone(ElementAttributes element) throws TokenException {
    Zone zone = element.getZone();
    Position pos;
    for (int x = (int) zone.topLeft().x(); x < zone.bottomRight().x(); x++) {
      for (int y = (int) zone.topLeft().y(); y < zone.bottomRight().y(); y++) {
        pos = new Position(x, y);
        if (!isInsideGrid(pos)) {
          throw new TokenException("Invalid given size for element '" + element.getSkin() + "'");
        }
        createElement(element, pos);
      }
    }
  }
  
  private void addElements() throws TokenException {
    for (ElementAttributes element: elements) {
      if (element.isPlayer()) {
        player = new Player(element.getSkin(), element.getPosition(), zone, element.getHealth(), element.getName(), null);
      } else if (element.hasZone() && element.getID() != GameObjectID.MOB) {
        createElementWithZone(element);
      } else {
        createElement(element);
      }
    }
  }
  
  public Game createGame() throws TokenException {
    createField();
    addElements();
    if (startingPos == null) {
      throw new TokenException("No starting position found from either teleport or player");
    }
    return new Game(zone, field, mobs, items, startingPos, Map.copyOf(teleports), backPos, player);
  }
  
}
