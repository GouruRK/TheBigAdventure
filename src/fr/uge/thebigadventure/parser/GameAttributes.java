package fr.uge.thebigadventure.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import fr.uge.thebigadventure.game.Game;
import fr.uge.thebigadventure.game.GameObjectID;
import fr.uge.thebigadventure.game.entity.item.DroppedItem;
import fr.uge.thebigadventure.game.entity.item.Item;
import fr.uge.thebigadventure.game.entity.mob.Mob;
import fr.uge.thebigadventure.game.entity.mob.Player;
import fr.uge.thebigadventure.game.environment.Environment;
import fr.uge.thebigadventure.util.Position;
import fr.uge.thebigadventure.util.Text;
import fr.uge.thebigadventure.util.Zone;

/**
 * Contains attributes of the game
 */
public class GameAttributes {

  // --------- Init ---------
  
  /**
   * List of parsed elements
   */
  private final ArrayList<ElementAttributes> elements;
  
  /**
   * List of mobs
   */
  private final ArrayList<Mob> mobs;
  
  /**
   * List of dropped items
   */
  private final ArrayList<DroppedItem> items;

  /**
   * Raw map data
   */
  private String data = null;
  
  /**
   * Map dimensions
   */
  private Zone zone = null;
  
  /**
   * Map of map encodings
   */
  private Map<Character, EncodingRow> encodings = null;
  
  /**
   * Field
   */
  private Environment[][] field;
  
  /**
   * Player object
   */
  private Player player = null;
  
  /**
   * Position where the player appear on the map
   * Usefull when using teleports
   */
  private Position startingPos = null;
  
  /**
   * Position where the players needs to be to be teleported back
   */
  private Position backPos = null;
  
  // --------- Constructors --------- 
  
  public GameAttributes() {
    elements = new ArrayList<ElementAttributes>();
    mobs = new ArrayList<Mob>();
    items = new ArrayList<DroppedItem>();
  }
  
  // --------- Checker ---------
  
  /**
   * Check if game has its data attribute
   * @return
   */
  public boolean hasData() {
    return data != null;
  }
  
  /**
   * Check if game has its dimension attribute
   * @return
   */
  public boolean hasZone() {
    return zone != null;
  }
  
  /**
   * Check if game has its encodings attribute
   * @return
   */
  public boolean hasEncodings() {
    return encodings != null;
  }
  
  /**
   * Check if game has all its mandatory attributes
   * @return
   */
  public boolean hasGameInfo() {
    return hasData() && hasZone() && hasEncodings();
  }
  
  public boolean hasStartingPos() {
    return startingPos != null;
  }

  /**
   * Check if a position is inside the game
   * @param pos
   * @return
   */
  public boolean isInsideGrid(Position pos) {
    return zone.isInside(pos);
  }
  
  // --------- Setters --------- 
  
  /**
   * Set raw data attribute
   * @param data
   * @throws TokenException if data is already register
   */
  public void setData(String data) throws TokenException {
    Objects.requireNonNull(data);
    if (this.data != null) {
      throw new TokenException("Data already register");
    }
    this.data = data;
  }
  
  /**
   * Add a new parsed element
   * @param elem
   */
  public void addElement(ElementAttributes elem) {
    Objects.requireNonNull(elem);
    elements.add(elem);
  }
  
  /**
   * Set game dimensions
   * @param zone dimensions
   * @throws TokenException
   */
  public void setZone(Zone zone) throws TokenException {
    Objects.requireNonNull(zone);
    if (this.zone != null) {
      throw new TokenException("Size already register");
    }
    this.zone = zone;
  }
  
  /**
   * Set encoding attribute
   * @param encodings
   * @throws TokenException
   */
  public void setEncodings(Map<Character, EncodingRow> encodings) throws TokenException {
    Objects.requireNonNull(encodings);
    if (this.encodings != null) {
      throw new TokenException("Encodings already register");
    }
    this.encodings = encodings;
  }
  
  /**
   * Set the position where the player appears on the map
   * @param pos
   * @throws TokenException
   */
  public void setStartingPos(Position pos) throws TokenException {
    Objects.requireNonNull(pos);
    if (startingPos != null) {
      throw new TokenException("Starting pos already register");
    }
    this.startingPos = pos;
  }
  
  // --------- Check data field --------- 
  
  /**
   * Check if the field dimensions are correct based on the given zone 
   * @param tempField
   * @throws TokenException
   */
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
  
  /**
   * Check if a line doesn't contains spaces.
   * Used to check top and bottom line of data
   * @param line
   * @return
   */
  private boolean checkFullLineOfField(String line) {
    // check if a line does not contains space
    return line.chars().allMatch(c -> c != ' ');
  }
  
  /**
   * Check if raw data has correct elements
   * @param field
   * @throws TokenException
   */
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
  
  /**
   * Check field's validity and return its data as a Text element
   * @return
   * @throws TokenException
   */
  private Text checkField() throws TokenException {
    Text field = new Text(data, true);
    checkFieldConstruction(field.text());
    checkFieldSize(field.text());
    return field;
  }

  // --------- Create Field ---------
  
  /**
   * Create field
   * @throws TokenException
   */
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
        EncodingRow row = encodings.getOrDefault(line.charAt(x), null);
        if (row != null) {
          createElement(row, new Position(x, y));
        }
      }
    }
  }
  
  // --------- Element Creation ---------
  
  /**
   * Create an element from the encoding row and add it to its structure (field, array of mobs, items, ...)
   * @param row
   * @param pos
   * @throws TokenException
   */
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
  
  /**
   * Create an element from an element attributes and add it to its structure
   * @param element
   * @param pos
   * @throws TokenException
   */
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
      if (element.hasBack()) {
        backPos = element.position();
      }
    } else {      
      throw new TokenException("Element '" + element.skin() + "' is not a map element");
    }
  }
  
  /**
   * Create an element
   * @param element
   * @throws TokenException
   */
  private void createElement(ElementAttributes element) throws TokenException {
    createElement(element, element.position());
  }
  
  /**
   * Create an element that uses a zone
   * @param element
   * @throws TokenException
   */
  private void createElementWithZone(ElementAttributes element) throws TokenException {
    Zone zone = element.zone();
    Position pos;
    for (int x = (int) zone.topLeft().x(); x < zone.bottomRight().x(); x++) {
      for (int y = (int) zone.topLeft().y(); y < zone.bottomRight().y(); y++) {
        pos = new Position(x, y);
        if (!isInsideGrid(pos)) {
          throw new TokenException("Invalid given size for element '" + element.skin() + "'");
        }
        createElement(element, pos);
      }
    }
  }
  
  /**
   * Add an element to the collection of elements to be created
   * @throws TokenException
   */
  private void addElements() throws TokenException {
    for (ElementAttributes element: elements) {
      if (element.isPlayer()) {
        player = new Player(element.skin(), element.position(), zone, element.health(), element.name(), null);
      } else if (element.hasZone() && element.getID() != GameObjectID.MOB) {
        createElementWithZone(element);
      } else {
        createElement(element);
      }
    }
  }
  
  /**
   * Create game
   * @return
   * @throws TokenException
   */
  public Game createGame() throws TokenException {
    createField();
    addElements();
    if (startingPos == null) {
      throw new TokenException("No starting position found from either teleport or player");
    }
    return new Game(zone, field, mobs, items, startingPos, backPos, player);
  }
  
}
