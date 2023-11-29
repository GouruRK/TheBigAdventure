package parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.Game;
import game.GameObject;
import game.GameObjectID;
import game.entity.item.GameItems;
import game.entity.item.Item;
import util.Position;
import util.Zone;

public class Parser {
  
  
  private static String isExpected(Lexer lexer, Token token) throws TokenException {
    Result res = lexer.nextResult();
    return Parser.isExpected(res, token);
  }
  
  private static String isExpected(Result res, Token token) throws TokenException {
    if (res != null && res.token() == token) {
      return res.content();
    } 
    if (res != null) {
      throw new TokenException("Awaited token of type " + token + ", got " + res.token() + " : " + res.content());
    }
    throw new TokenException("No more tokens");
  }
  
  private static String isExpected(Lexer lexer, Token token, String content) throws TokenException {
    Result res = lexer.nextResult();
    return Parser.isExpected(res, token, content);
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
  
  public static Position parseSize(Lexer lexer) throws TokenException {
    // Note : here after "Result[token=IDENTIFIER, content=size]" and "Result[token=COLON, content=:]" 
    // Next : wait for : "( 'n' x 'm')"
    double width, height;
    Parser.isExpected(lexer, Token.LEFT_PARENS);
    width = Integer.parseInt(Parser.isExpected(lexer, Token.NUMBER));
    Parser.isExpected(lexer, Token.IDENTIFIER, "x");
    height = Integer.parseInt(Parser.isExpected(lexer, Token.NUMBER));
    Parser.isExpected(lexer, Token.RIGHT_PARENS);
    return new Position(width, height);
  }
  
  public static Position parsePosition(Lexer lexer) throws TokenException {
    // Note : here after "Result[token=IDENTIFIER, content=position]" and "Result[token=COLON, content=:]"
    // Next : wait for : "('x', 'y')"
    double x, y;
    Parser.isExpected(lexer, Token.LEFT_PARENS);
    x = Integer.parseInt(Parser.isExpected(lexer, Token.NUMBER));
    Parser.isExpected(lexer, Token.COMMA);
    y = Integer.parseInt(Parser.isExpected(lexer, Token.NUMBER));
    Parser.isExpected(lexer, Token.RIGHT_PARENS);
    return new Position(x, y);
  }
  
  public static Zone parseZone(Lexer lexer) throws TokenException { 
    // Note : here after "Result[token=IDENTIFIER, content=zone]" and "Result[token=COLON, content=:]"
    // Next : wait for "('x', 'y') ('w' x 'h')"
    Position topLeft = Parser.parsePosition(lexer);
    Position size = Parser.parseSize(lexer);
    return new Zone(topLeft, new Position(topLeft.x() + size.x(), topLeft.y() + size.y()));
  }
  
  private static EncodingRow parseEncodings(Lexer lexer) throws TokenException {
    // Note : here after "Result[token=IDENTIFIER, content=encodings]" and "Result[token=COLON, content=:]"
    // wait for "OBJECT(O)"
    String identifier, code;
    GameObjectID id;
    identifier = Parser.isExpected(lexer, Token.IDENTIFIER);
    id = GameObject.fromName(identifier);
    if (id == GameObjectID.UNKNOWN) {
      throw new TokenException("Unknonw element '" + identifier + "'");
    }
    Parser.isExpected(lexer, Token.LEFT_PARENS);
    code = Parser.isExpected(lexer, Token.IDENTIFIER);
    if (code.length() != 1) {
      throw new TokenException("Code for '" + identifier + "' must be one character");
    }
    Parser.isExpected(lexer, Token.RIGHT_PARENS);
    return new EncodingRow(identifier, code.charAt(0), id);
  }
  
  public static Item parseItem(Lexer lexer) throws TokenException {
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
  
  public static List<Item> parseSteal(Lexer lexer) throws TokenException {
    // Note : here after "Result[token=IDENTIFIER, content=steal]" and "Result[token=COLON, content=:]"
    // wait for "SKIN name, SKIN name, SKIN"
    
    ArrayList<Item> lst = new ArrayList<Item>();
    Item item;
    Result res;
    
    while (lexer.hasNext()) {
      item = Parser.parseItem(lexer);
      
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
  
  public static Map<String, List<Item>> parseTrade(Lexer lexer) throws TokenException {
    // Note : here after "Result[token=IDENTIFIER, content=trade]" and "Result[token=COLON, content=:]"
    // wait for "SKIN -> SKIN name" or "SKIN -> SKIN"
    HashMap<String, List<Item>> map = new HashMap<String, List<Item>>();
    Item item;
    Result res;
    String skin;
    
    while (lexer.hasNext()) {
      skin = Parser.isExpected(lexer, Token.IDENTIFIER);
      Parser.isExpected(lexer, Token.ARROW);
      item = Parser.parseItem(lexer);
      
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
  
  public static Map<Character, EncodingRow> parseEncoding(Lexer lexer) throws TokenException {
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
        row = Parser.parseEncodings(lexer);
        if (encodings.get(row.code()) != null) {
          throw new TokenException("Block code '" + row.code() + "' already register");
        }
        encodings.put(row.code(), row);
      }
    }
    return Map.copyOf(encodings);
  }
  
  public static Game parseGame(Lexer lexer) throws TokenException {
    String attribute;
    GameAttributes game = new GameAttributes();
    
    while (lexer.hasNext() && !game.isFull()) {
      attribute = Parser.isExpected(lexer, Token.IDENTIFIER);
      Parser.isExpected(lexer, Token.COLON);
      
      switch (attribute) {
      case "size" -> game.setSize(Parser.parseSize(lexer));
      case "encodings" -> game.setEncodings(Parser.parseEncoding(lexer));
      case "data" -> game.setData(Parser.isExpected(lexer, Token.QUOTE));
      default -> throw new TokenException("Unknown grid attribute '" + attribute + "'");
      };      
    }
    return game.createGame();
  }
  
  public static ElementAttributes parseElement(Lexer lexer) throws TokenException {
    Result res;
    ElementAttributes elem = new ElementAttributes();
    
    while (lexer.hasNext()) {
      res = lexer.nextResult();
      if (res.token() == Token.LEFT_BRACKET) {
        lexer.addNext(res);
        break;
      }
      Parser.isExpected(lexer, Token.COLON);
      switch(res.content()) {
        case "name" -> elem.setName(Parser.isExpected(lexer, Token.IDENTIFIER));
        case "skin" -> elem.setSkin(Parser.isExpected(lexer, Token.IDENTIFIER));
        case "player" -> elem.setPlayer(Parser.isExpected(lexer, Token.IDENTIFIER));
        case "position" -> elem.setPosition(Parser.parsePosition(lexer));
        case "health" -> elem.setHealth(Parser.isExpected(lexer, Token.NUMBER));
        case "kind" -> elem.setKind(Parser.isExpected(lexer, Token.IDENTIFIER));
        case "zone" -> elem.setZone(Parser.parseZone(lexer));
        case "behavior", "behaviour" -> elem.setBehaviour(Parser.isExpected(lexer, Token.IDENTIFIER));
        case "damage" -> elem.setDamage(Parser.isExpected(lexer, Token.NUMBER));
        case "text" -> elem.setText(Parser.isExpected(lexer, Token.QUOTE));
        case "steal" -> elem.setSteal(Parser.parseSteal(lexer));
        case "trade" -> elem.setTrade(Parser.parseTrade(lexer));
        case "locked" -> elem.setLocked(Parser.parseItem(lexer));
        case "flow" -> elem.setFlow(Parser.isExpected(lexer, Token.IDENTIFIER));
        case "phantomize" -> elem.setPhantomize(Parser.isExpected(lexer, Token.IDENTIFIER));
        case "teleport" -> elem.setTeleport(Parser.isExpected(lexer, Token.IDENTIFIER));
        default -> throw new TokenException("Unknown attribute '" + res.content() + "'");
      };
    }
    return elem;
  }
  
  public static Game parseMap(String map) throws IOException, TokenException {
    var path = Path.of(map);
    var text = Files.readString(path);
    var lexer = new Lexer(text);
    
    Game game = null;
    ArrayList<ElementAttributes> lst = new ArrayList<ElementAttributes>();
    String blockIdentifier;
    
    while (lexer.hasNext()) {
      Parser.isExpected(lexer, Token.LEFT_BRACKET);
      blockIdentifier = Parser.isExpected(lexer, Token.IDENTIFIER);
      Parser.isExpected(lexer, Token.RIGHT_BRACKET);
      if ("grid".equals(blockIdentifier)) {
        if (game != null) {
          throw new TokenException("Grid element already exists");
        }
        game = Parser.parseGame(lexer);
      } else if ("element".equals(blockIdentifier)) {
         lst.add(Parser.parseElement(lexer));
      }
    }
    game.addElements(lst);
    return game;
  }
}
