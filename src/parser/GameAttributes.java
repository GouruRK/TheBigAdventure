package parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import game.Game;
import game.GameObjectID;
import game.entity.item.DroppedItem;
import game.entity.item.Item;
import game.entity.mob.Mob;
import game.entity.mob.Player;
import game.environnement.Environnement;
import util.Position;
import util.Zone;

public class GameAttributes {

  // --------- Init ---------
  
  private final ArrayList<ElementAttributes> elements;
  private final ArrayList<Mob> mobs;
  private final ArrayList<DroppedItem> items;

  private String data = null;
  private Position size = null;
  private Map<Character, EncodingRow> encodings = null;
  private Environnement[][] field;
  private Player player = null;
  
  // --------- Constructors --------- 
  
  public GameAttributes() {
    elements = new ArrayList<ElementAttributes>();
    mobs = new ArrayList<Mob>();
    items = new ArrayList<DroppedItem>();
  }
  
  // --------- Checker ---------
  
  public boolean hasData() {
    return data != null;
  }
  
  public boolean hasSize() {
    return size != null;
  }
  
  public boolean hasEncodings() {
    return encodings != null;
  }
  
  public boolean hasGameInfo() {
    return hasData() && hasSize() && hasEncodings();
  }
  
  public boolean isInsideGrid(Position pos) {
    return (0 <= pos.x() && pos.x() < size.y()) && 
           (0 <= pos.y() && pos.y() < size.y());
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
  
  public void setSize(Position size) throws TokenException {
    Objects.requireNonNull(size);
    if (this.size != null) {
      throw new TokenException("Size already register");
    }
    this.size = size;
  }
  
  public void setEncodings(Map<Character, EncodingRow> encodings) throws TokenException {
    Objects.requireNonNull(encodings);
    if (this.encodings != null) {
      throw new TokenException("Encodings already register");
    }
    this.encodings = encodings;
  }
  
  // --------- Transformers --------- 
  
  private List<String> parseField() {
    String tempData = data.replaceAll("\\\"\\\"\\\"", "").stripIndent();
    String[] lineArray = tempData.split("\n");

    
    List<String> field = Arrays.stream(lineArray)
        .map(line -> line.replaceAll("\n", ""))
        .collect(Collectors.toList());
    
    field.removeIf(line -> line.isEmpty());
    return field;
  }
  
  // --------- Check data field --------- 
  
  private void checkFieldSize(List<String> tempField) throws TokenException {
    if (!hasSize()) throw new TokenException("Size of grid is unknown");
    
    int width = (int) size.x(), height = (int) size.y();
    
    if (tempField.size() != height) {
      throw new TokenException("Invalid map height : got " + tempField.size() + " expected " + height);
    }
    
    if (!tempField.stream().allMatch(line -> line.length() == width)) {
      throw new TokenException("Invalid map width");
    }
    
    field = new Environnement[height][width];
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
  
  private List<String> checkField() throws TokenException {
    List<String> field = parseField();
    checkFieldConstruction(field);
    checkFieldSize(field);
    return field;
  }

  // --------- Create Field ---------
  
  private void createField() throws TokenException {
    if (encodings == null) {
      throw new TokenException("Map encoding is missing");
    }
    List<String> oldField = checkField();
    String line;
    
    for (int y = 0; y < size.y(); y++) {
      line = oldField.get(y);
      for (int x = 0; x < size.x(); x++) {
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
    Environnement env;
    DroppedItem item;
    
    if ((env = Environnement.createEnvironnement(row, pos)) != null) {
      field[(int) pos.y()][(int) pos.x()] = env;
    } else if ((item = Item.createDroppedItem(row, pos)) != null) {
      items.add(item);
    } else {
      throw new TokenException("Element '" + row.skin() + "' is not a map element and cannot be given from data and encodings");
    }
  }
  
  private void createElement(ElementAttributes element, Position pos) throws TokenException {
    Environnement env;
    DroppedItem item;
    Mob mob;
    
    if ((env = Environnement.createEnvironnement(element, pos)) != null) {
      field[(int) env.pos().y()][(int) env.pos().x()] = env;
    } else if((item = Item.createDroppedItem(element)) != null) {
      items.add(item);
    } else if ((mob = Mob.createMob(element)) != null) {
      mobs.add(mob);
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
  
  public void addElements() throws TokenException {
    for (ElementAttributes element: elements) {
      if (element.isPlayer()) {
        player = new Player(element.getSkin(), element.getPosition());
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
    return new Game(size, field, List.copyOf(mobs), List.copyOf(items), player);
  }
  
}
