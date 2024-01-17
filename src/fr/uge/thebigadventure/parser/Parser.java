package fr.uge.thebigadventure.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import fr.uge.thebigadventure.game.Game;
import fr.uge.thebigadventure.game.GameObject;
import fr.uge.thebigadventure.game.GameObjectID;
import fr.uge.thebigadventure.game.entity.item.Item;
import fr.uge.thebigadventure.util.PathCreator;
import fr.uge.thebigadventure.util.Position;
import fr.uge.thebigadventure.util.Zone;

/**
 * <p>Parse '.map' files and create the game based on the map information. The fr.uge.thebigadventure.
 * The map format is separated in 'block' delimited by brackets. The two kinds on blocks are 'grid' and 'element'
 * Each block have their own attributes. Each attributes has a name and its separated from its value by a colon</p>
 * 
 *  <ul> 
 *    <li>The 'grid' refers refers to the field structure, with its size and how parse it.
 *  The grid attributes are :
 *    <ul>
 *      <li>size: ('width' x 'height'), with 'with' and 'height' as numbers</li>
 *      <li>encoding: ITEMA(W), ITEMB(B), ITEMC(F)<br>List of items that compose the data attributes and their code</li>
 *      <li>data : """<pre>
 * WWWWWW
 * W    W
 * W FF W
 * B    B
 * BBBBBB
 * """<pre></li>
 *    </ul>
 *   </li>
 *   <li>The 'element' block elements refers to elements to add on the map, like mobs, player, more obstacles
 *    <ul>
 *     <li>name: name</li>
 *     <li>skin: skin</li>
 *     <li>player: true | false</li>
 *     <li>position: ('x', 'y')<br> 'x' and 'y' are numbers</li>
 *     <li>health: 'n'<br>'n' must be a number</li>
 *     <li>kind: friend | enemy | item | obstacle</li>
 *     <li>zone: ('x', 'y') ('width' x 'height')<br> 'x', 'y', 'width' and 'height' are numbers</li>
 *     <li>behavior: shy | stroll | agressive</li>
 *     <li>damage: 'n'<br>'n' must be a number</li>
 *     <li>text: """Text element"""<br>same structure as data for the 'grid' block</li>
 *     <li>steal: ITEM name, ITEM, ITEM name, ...</li>
 *     <li>trade: CASH -> KEY red, CASH -> PIZZA</li>
 *     <li>locked: KEY red<br>Indicate which item can open the element</li>
 *     <li>flow: NORTH | SOUTH | EAST | WEST</li>
 *     <li>phantomized: true | false<br>If true, the player became a ghost when collinding with the element</li>
 *     <li>teleport: 'map'<br>Name of a map file in the map folder. Teleport the player to the map if set</li>
 *    </ul>
 *   </li>
 * </ul>
 * 
 * 
 * @see {@link fr.uge.thebigadventure.parser.Parser}
 * @see {@link fr.uge.thebigadventure.parser.Lexer}
 * 
 * @author De Oliveira Nelson
 * @author Kies Rémy
 * 
 */
public class Parser {
  
  private static final Set<String> PARSEDMAP = new HashSet<String>();
  /**
   * Current file the fr.uge.thebigadventure.
   */
  private final String sourceFile;
  
  /**
   * Lexer to give tokens
   */
  private final Lexer lexer;
  private final GameAttributes attributes;
  
  /**
   * Create a Parser object for the given text from the sourcFile
   * @param text
   * @param sourceFile
   */
  public Parser(String text, String sourceFile) {
    Objects.requireNonNull(text);
    Objects.requireNonNull(sourceFile);
    this.lexer = new Lexer(text);
    this.attributes = new GameAttributes();
    this.sourceFile = sourceFile;
    PARSEDMAP.add(sourceFile);
  }
  
  /**
   * Create a fr.uge.thebigadventure.
   * @param filePath
   * @throws IOException
   */
  public Parser(Path filePath) throws IOException {
    Objects.requireNonNull(filePath);
    String text = Files.readString(filePath);
    Objects.requireNonNull(text);
    this.lexer = new Lexer(text);
    this.attributes = new GameAttributes();
    this.sourceFile = filePath.toString();
    PARSEDMAP.add(sourceFile);
  }
  
  /**
   * Check if the next token is the expected one
   * @param token
   * @return value of the next token
   * @throws TokenException
   */
  private String isExpected(Token token) throws TokenException {
    Result res = lexer.nextResult();
    return isExpected(res, token);
  }
  
  /**
   * Check if the given Result ({@link parer.Result} contains the required token
   * @param res
   * @param token
   * @return value of the given result
   * @throws TokenException
   */
  private String isExpected(Result res, Token token) throws TokenException {
    if (res != null && res.token() == token) {
      return res.content();
    } 
    if (res != null) {
      throw new TokenException("Awaited token of type " + token + ", got " + res.token() + " : " + res.content());
    }
    throw new TokenException("No more tokens");
  }
  
  /**
   * Check if the next token contains the expected one with the given content
   * @param token
   * @param content
   * @return content of the next token
   * @throws TokenException
   */
  private String isExpected(Token token, String content) throws TokenException {
    Result res = lexer.nextResult();
    return isExpected(res, token, content);
  }
  
  /***
   * Check if the given result contains the required token and the given content
   * @param res
   * @param token
   * @param content
   * @return content of the result
   * @throws TokenException
   */
  private static String isExpected(Result res, Token token, String content) throws TokenException {
    if (res != null && res.token() == token && res.content().equals(content)) {
      return res.content();
    }
    if (res != null) {
      throw new TokenException("Awaited '" + content + "', got " + res.content());
    }
    throw new TokenException("Awaited token, got none");
  }
  
  private Position parseSize() throws TokenException {
    // Note : here after "Result[token=IDENTIFIER, content=size]" and "Result[token=COLON, content=:]" 
    // Next : wait for : "( 'n' x 'm')"
    double width, height;
    isExpected(Token.LEFT_PARENS);
    width = Integer.parseInt(isExpected(Token.NUMBER));
    isExpected(Token.IDENTIFIER, "x");
    height = Integer.parseInt(isExpected(Token.NUMBER));
    isExpected(Token.RIGHT_PARENS);
    return new Position(width, height);
  }
  
  private Position parsePosition() throws TokenException {
    // Note : here after "Result[token=IDENTIFIER, content=position]" and "Result[token=COLON, content=:]"
    // Next : wait for : "('x', 'y')"
    double x, y;
    isExpected(Token.LEFT_PARENS);
    x = Integer.parseInt(isExpected(Token.NUMBER));
    isExpected(Token.COMMA);
    y = Integer.parseInt(isExpected(Token.NUMBER));
    isExpected(Token.RIGHT_PARENS);
    return new Position(x, y);
  }
  
  public Zone parseZone() throws TokenException { 
    // Note : here after "Result[token=IDENTIFIER, content=zone]" and "Result[token=COLON, content=:]"
    // Next : wait for "('x', 'y') ('w' x 'h')"
    Position topLeft = parsePosition();
    Position size = parseSize();
    return new Zone(topLeft, new Position(topLeft.x() + size.x(), topLeft.y() + size.y()));
  }
  
  private EncodingRow parseEncodings() throws TokenException {
    // Note : here after "Result[token=IDENTIFIER, content=encodings]" and "Result[token=COLON, content=:]"
    // wait for "OBJECT(O)"
    String identifier, code;
    GameObjectID id;
    
    identifier = isExpected(Token.IDENTIFIER);
    id = GameObject.fromName(identifier);
    if (id == GameObjectID.UNKNOWN) {
      throw new TokenException("Unknown element '" + identifier + "'");
    }
    isExpected(Token.LEFT_PARENS);
    code = isExpected(Token.IDENTIFIER);
    if (code.length() != 1) {
      throw new TokenException("Code for '" + identifier + "' must be one character");
    }
    isExpected(Token.RIGHT_PARENS);
    return new EncodingRow(identifier, code.charAt(0), id);
  }
  
  private Item parseItem() throws TokenException {
    // wait for "SKIN name" or "SKIN"
    Result skinResult, nameResult, next;
    String skin, name;
    GameObjectID id;
    
    skinResult = lexer.nextResult();
    id = GameObject.fromName(skinResult.content());
    if (id == GameObjectID.UNKNOWN) {
      throw new TokenException("Unknown item '" + skinResult.content() + "'");
    }
    skin = skinResult.content();
    
    nameResult = lexer.nextResult();
    if (nameResult == null) {
      return Item.createItem(skin);
    }
    if (nameResult.token() == Token.COMMA 
        || nameResult.token() == Token.LEFT_BRACKET 
        || nameResult.token() == Token.ARROW) { // item has no name
      lexer.addNext(nameResult);
      return Item.createItem(skin);
    } else if (nameResult.token() != Token.IDENTIFIER) { // item name is not an IDENTIFIER
      throw new TokenException("Expected a name for an item or next attribute");
    }
    name = nameResult.content();
    
    next = lexer.nextResult();
    if (next == null) { // no more tokens
      return Item.createItem(skin, name);
    }
    if (next.token() == Token.COLON) { // item name is the next attribute
      lexer.addNext(next);
      lexer.addNext(nameResult);
      return Item.createItem(skin);
    }
    lexer.addNext(next);
    return Item.createItem(skin, name);
  }
  
  private List<Item> parseSteal() throws TokenException {
    // Note : here after "Result[token=IDENTIFIER, content=steal]" and "Result[token=COLON, content=:]"
    // wait for "SKIN name, SKIN name, SKIN"
    
    ArrayList<Item> lst = new ArrayList<Item>();
    Item item;
    Result res;
    
    while (lexer.hasNext()) {
      item = parseItem();
      
      lst.add(item);
      
      res = lexer.nextResult();
      if (res.token() == Token.COMMA) {
        continue;
      } 
      lexer.addNext(res);
      break;
    }
    return List.copyOf(lst);
  }
  
  private Map<Item, List<Item>> parseTrade() throws TokenException {
    // Note : here after "Result[token=IDENTIFIER, content=trade]" and "Result[token=COLON, content=:]"
    // wait for "SKIN -> SKIN name" or "SKIN -> SKIN"
    HashMap<Item, List<Item>> map = new HashMap<Item, List<Item>>();
    Item wanted, toSell;
    Result res;
    
    while (lexer.hasNext()) {
      wanted = parseItem();
      
      isExpected(Token.ARROW);
      toSell = parseItem();
      
      map.computeIfAbsent(wanted, key -> new ArrayList<Item>()).add(toSell);

      res = lexer.nextResult();
      if (res == null) {
        break;
      }
      
      if (res.token() == Token.COMMA) {
        continue;
      }
      lexer.addNext(res);
      break;
    }
    map.replaceAll((k, v) -> List.copyOf(v));
    return Map.copyOf(map);
  }
  
  private Map<Character, EncodingRow> parseEncoding() throws TokenException {
    HashMap<Character, EncodingRow> encodings = new HashMap<Character, EncodingRow>();
    Result res;
    EncodingRow row;
    while (lexer.hasNext()) {
      res = lexer.nextResult();
      lexer.addNext(res);
      if ("data".equals(res.content()) || "size".equals(res.content())) {
        // end of parsing encodings
        return encodings;
      } else {
        row = parseEncodings();
        if (encodings.getOrDefault(row.code(), null) != null) {
          throw new TokenException("Block code '" + row.code() + "' already register");
        }
        encodings.put(row.code(), row);
      }
    }
    return Map.copyOf(encodings);
  }
  
  private void parseTeleport(ElementAttributes element) throws TokenException, IOException {
    String name = isExpected(Token.IDENTIFIER);
    if (name.equals("back")) {
      element.setBack();
    } else {
      String path = PathCreator.mapPath(name);
      isExpected(Token.DOT);
      isExpected(Token.IDENTIFIER, "map");
      if (PARSEDMAP.contains(path)) {
        return;
      }
      Parser parser = new Parser(Path.of(path));
      Game game = parser.parseMap();
      if (game.player() != null) {
        throw new TokenException("Map to be teleported can't contains a player");
      } else {
        if (game.startingPosition() == null) {
          throw new TokenException("Map to be teleported must have an element with 'teleport: back'");
        }
      }
      element.setTeleport(game);
    }
  }
  
  private void parseGame() throws TokenException {
    String attribute;
    
    while (lexer.hasNext() && !attributes.hasGameInfo()) {
      attribute = isExpected(Token.IDENTIFIER);
      isExpected(Token.COLON);
      
      switch (attribute) {
      case "size" -> attributes.setZone(new Zone(new Position(0, 0), parseSize()));
      case "encodings" -> attributes.setEncodings(parseEncoding());
      case "data" -> attributes.setData(isExpected(Token.QUOTE));
      default -> throw new TokenException("Unknown grid attribute '" + attribute + "'");
      };      
    }
  }
  
  private void parsePlayer(ElementAttributes element) throws TokenException {
    if (PARSEDMAP.size() != 1) {
      throw new TokenException("Map to be teleported mustn't contains a player");
    }
    element.setPlayer(isExpected(Token.IDENTIFIER));
  }
  
  private void parseElement() throws TokenException,
                                    IOException {
    Result res;
    ElementAttributes elem = new ElementAttributes();
    
    while (lexer.hasNext()) {
      res = lexer.nextResult();
      if (res.token() == Token.LEFT_BRACKET) {
        lexer.addNext(res);
        break;
      }

      isExpected(Token.COLON);
      switch(res.content()) {
        case "name" -> elem.setName(isExpected(Token.IDENTIFIER));
        case "skin" -> elem.setSkin(isExpected(Token.IDENTIFIER));
        case "player" -> parsePlayer(elem);
        case "position" -> elem.setPosition(parsePosition());
        case "health" -> elem.setHealth(isExpected(Token.NUMBER));
        case "kind" -> elem.setKind(isExpected(Token.IDENTIFIER));
        case "zone" -> elem.setZone(parseZone());
        case "behavior", "behaviour" -> elem.setBehaviour(isExpected(Token.IDENTIFIER));
        case "damage" -> elem.setDamage(isExpected(Token.NUMBER));
        case "text" -> elem.setText(isExpected(Token.QUOTE));
        case "steal" -> elem.setSteal(parseSteal());
        case "trade" -> elem.setTrade(parseTrade());
        case "locked" -> elem.setLocked(parseItem());
        case "flow" -> elem.setFlow(isExpected(Token.IDENTIFIER));
        case "phantomize" -> elem.setPhantomize(isExpected(Token.IDENTIFIER));
        case "teleport" -> parseTeleport(elem);
        default -> throw new TokenException("Unknown attribute '" + res.content() + "'");
      };
    }
    
    if (elem.hasBack()) {
      if (elem.hasPosition()) {
        attributes.setStartingPos(elem.getPosition());
      } else {
        throw new TokenException("Element that teleports back must have a position");
      }
    }
    
    if (elem.isPlayer()) {
      if (elem.hasPosition()) {
        attributes.setStartingPos(elem.getPosition());
      } else {
        throw new TokenException("Player must have a position");
      }
    }
    
    if (!elem.isValid()) throw new TokenException("Element must have a skin");
    attributes.addElement(elem);
  }
  
  public Game parseMap() throws TokenException, IOException {
    String blockIdentifier;
    try {
      while (lexer.hasNext()) {
        isExpected(Token.LEFT_BRACKET);
        blockIdentifier = isExpected(Token.IDENTIFIER);
        isExpected(Token.RIGHT_BRACKET);
        if ("grid".equals(blockIdentifier)) {
          parseGame();
        } else if ("element".equals(blockIdentifier)) {
          parseElement();
        } else {
          throw new TokenException("Unknown block name '" + blockIdentifier + "'");
        }
      }
    } catch (TokenException e) {
      throw new TokenException("Error at " + sourceFile + ": line " + lexer.last().line() + " " + e.getMessage());
    }
    return attributes.createGame();
  }
}
