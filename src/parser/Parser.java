package parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import game.Game;
import game.GameObject;
import game.GameObjectID;
import game.entity.item.GameItems;
import game.entity.item.Item;
import util.Position;
import util.Zone;

public class Parser {
  
  private final Lexer lexer;
  
  public Parser(String text) {
    Objects.requireNonNull(text);
    this.lexer = new Lexer(text);
  }
  
  private String isExpected(Token token) throws TokenException {
    Result res = lexer.nextResult();
    return isExpected(res, token);
  }
  
  private String isExpected(Result res, Token token) throws TokenException {
    if (res != null && res.token() == token) {
      return res.content();
    } 
    if (res != null) {
      throw new TokenException("Awaited token of type " + token + ", got " + res.token() + " : " + res.content());
    }
    throw new TokenException("No more tokens");
  }
  
  private String isExpected(Token token, String content) throws TokenException {
    Result res = lexer.nextResult();
    return isExpected(res, token, content);
  }
  
  
  private static String isExpected(Result res, Token token, String content) throws TokenException {
    if (res != null && res.token() == token && res.content().equals(content)) {
      return res.content();
    }
    if (res != null) {
      throw new TokenException("Awaited '" + content + "', got " + res.content());
    }
    throw new TokenException("Awaited token, got none");
  }
  
  public Position parseSize() throws TokenException {
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
  
  public Position parsePosition() throws TokenException {
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
      throw new TokenException("Unknonw element '" + identifier + "'");
    }
    isExpected(Token.LEFT_PARENS);
    code = isExpected(Token.IDENTIFIER);
    if (code.length() != 1) {
      throw new TokenException("Code for '" + identifier + "' must be one character");
    }
    isExpected(Token.RIGHT_PARENS);
    return new EncodingRow(identifier, code.charAt(0), id);
  }
  
  public Item parseItem() throws TokenException {
    // wait for "SKIN name" or "SKIN"
    Result skinResult, nameResult, next;
    String skin, name;
    GameObjectID id;
    
    skinResult = lexer.nextResult();
    id = GameItems.getId(skinResult.content());
    if (id == GameObjectID.UNKNOWN) {
      throw new TokenException("Unknown item '" + skinResult.content() + "'");
    }
    skin = skinResult.content();
    
    nameResult = lexer.nextResult();
    if (nameResult == null) {
      return Item.createItem(skin);
    }
    if (nameResult.token() == Token.COMMA) { // item has no name
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
  
  public List<Item> parseSteal() throws TokenException {
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
  
  public Map<String, List<Item>> parseTrade() throws TokenException {
    // Note : here after "Result[token=IDENTIFIER, content=trade]" and "Result[token=COLON, content=:]"
    // wait for "SKIN -> SKIN name" or "SKIN -> SKIN"
    HashMap<String, List<Item>> map = new HashMap<String, List<Item>>();
    Item item;
    Result res;
    String skin;
    
    while (lexer.hasNext()) {
      skin = isExpected(Token.IDENTIFIER);
      isExpected(Token.ARROW);
      item = parseItem();
      
      map.computeIfAbsent(skin, key -> new ArrayList<Item>()).add(item);

      res = lexer.nextResult();
      if (res.token() == Token.COMMA) {
        continue;
      }
      lexer.addNext(res);
      break;
    }
    map.replaceAll((k, v) -> List.copyOf(v));
    return Map.copyOf(map);
  }
  
  public Map<Character, EncodingRow> parseEncoding() throws TokenException {
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
        if (encodings.get(row.code()) != null) {
          throw new TokenException("Block code '" + row.code() + "' already register");
        }
        encodings.put(row.code(), row);
      }
    }
    return Map.copyOf(encodings);
  }
  
  public Game parseGame() throws TokenException {
    String attribute;
    GameAttributes game = new GameAttributes();
    
    while (lexer.hasNext() && !game.isFull()) {
      attribute = isExpected(Token.IDENTIFIER);
      isExpected(Token.COLON);
      
      switch (attribute) {
      case "size" -> game.setSize(parseSize());
      case "encodings" -> game.setEncodings(parseEncoding());
      case "data" -> game.setData(isExpected(Token.QUOTE));
      default -> throw new TokenException("Unknown grid attribute '" + attribute + "'");
      };      
    }
    return game.createGame();
  }
  
  public ElementAttributes parseElement() throws TokenException {
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
        case "player" -> elem.setPlayer(isExpected(Token.IDENTIFIER));
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
        case "teleport" -> elem.setTeleport(isExpected(Token.IDENTIFIER));
        default -> throw new TokenException("Unknown attribute '" + res.content() + "'");
      };
    }
    if (!elem.isValid()) throw new TokenException("Element must have a skin");
    return elem;
  }
  
  public Game parseMap() throws TokenException {
    Game game = null;
    ArrayList<ElementAttributes> lst = new ArrayList<ElementAttributes>();
    String blockIdentifier;
    
    while (lexer.hasNext()) {
      isExpected(Token.LEFT_BRACKET);
      blockIdentifier = isExpected(Token.IDENTIFIER);
      isExpected(Token.RIGHT_BRACKET);
      if ("grid".equals(blockIdentifier)) {
        if (game != null) {
          throw new TokenException("Grid element already exists");
        }
        game = parseGame();
      } else if ("element".equals(blockIdentifier)) {
         lst.add(parseElement());
      } else {
        throw new TokenException("Unknown block name '" + blockIdentifier + "'");
      }
    }
    game.addElements(lst);
    return game;
  }
}
