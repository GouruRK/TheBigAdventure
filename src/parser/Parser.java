package parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import game.Game;
import game.GameObject;
import game.GameObjectID;
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
      throw new TokenException("Awaited token of type " + token + ", got " + res.token());      
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
    // Note : here after "Result[token=IDENTIFIER, content=size]"
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
    // Note : here after "Result[token=IDENTIFIER, content=position]"
    // Next : wait for : "('x', 'y')"
    double x, y;
    Parser.isExpected(lexer, Token.LEFT_PARENS);
    x = Integer.parseInt(Parser.isExpected(lexer, Token.NUMBER));
    Parser.isExpected(lexer, Token.IDENTIFIER, ",");
    y = Integer.parseInt(Parser.isExpected(lexer, Token.NUMBER));
    Parser.isExpected(lexer, Token.RIGHT_PARENS);
    return new Position(x, y);
  }
  
  public static Zone parseZone(Lexer lexer) throws TokenException { 
    // Note : here after "Result[token=IDENTIFIER, content=zone]"
    // Next : wait for "('x', 'y') ('w' x 'h')"
    Position topLeft = Parser.parsePosition(lexer);
    Position size = Parser.parseSize(lexer);
    return new Zone(topLeft, new Position(topLeft.x() + size.x(), topLeft.y() + size.y()));
  }
  
  private static EncodingCouple parseEncodings(Lexer lexer) throws TokenException {
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
    return new EncodingCouple(code, id);
  }
  
  
  public static Map<String, GameObjectID> parseEncoding(Lexer lexer) throws TokenException {
    HashMap<String, GameObjectID> encodings = new HashMap<String, GameObjectID>();
    Result res;
    EncodingCouple cpl;
    while (lexer.hasNext()) {
      res = lexer.nextResult();
      lexer.addNext(res);
      if ("data".equals(res.content()) || "size".equals(res.content())) {
        // end of parsing encodings
        return encodings;
      } else {
        cpl = Parser.parseEncodings(lexer);
        if (encodings.getOrDefault(cpl.code(), GameObjectID.UNKNOWN) != GameObjectID.UNKNOWN) {
          throw new TokenException("Block code '" + cpl.code() + "' already register");
        }
        encodings.put(cpl.code(), cpl.id());
      }
    }
    return encodings;
  }
  
  public static Game parseGame(Lexer lexer) throws TokenException {
    Position size = null;
    Map<String, GameObjectID> encodings = null;
    String attribute, data = null;
    int attributeCount = 0;
    
    while (lexer.hasNext() && attributeCount != 3) {
      attribute = Parser.isExpected(lexer, Token.IDENTIFIER);
      Parser.isExpected(lexer, Token.COLON);
      if ("size".equals(attribute)) {
        if (size == null) throw new TokenException("Size already given");
        size = Parser.parseSize(lexer);
      } else if ("encodings".equals(attribute)) {
        if (encodings == null) throw new TokenException("Encodings already given");
        encodings = Parser.parseEncoding(lexer);
      } else if ("data".equals(attribute)) {
        if (data == null) throw new TokenException("Data already given");
        data = Parser.isExpected(lexer, Token.QUOTE).stripIndent(); // remove indentation
      } else {
        throw new TokenException("Unknonw attribute '" + attribute + "'");
      }
      attributeCount++;
    }
    
    if (attributeCount != 3) {
      if (size == null) throw new TokenException("Size not given");
      if (encodings == null) throw new TokenException("Encodings not given");
      throw new TokenException("data not given");
    }
    
    return new Game(size, encodings, data);
    
  }
  
  public static void parseElement(Lexer lexer, Game game) {
    return;
  }
  
  public static void parseMap(String map) throws IOException, TokenException {
    var path = Path.of(map);
    var text = Files.readString(path);
    var lexer = new Lexer(text);
    
    Game game = null;
    String blockIdentifier;
    
    
    while (lexer.hasNext()) {
      Parser.isExpected(lexer, Token.LEFT_BRACKET);
      blockIdentifier = Parser.isExpected(lexer, Token.IDENTIFIER);
      Parser.isExpected(lexer, Token.RIGHT_BRACKET);
      if ("grid".equals(blockIdentifier)) {
        if (game == null) {
          throw new TokenException("Grid element already exists");
        }
        game = Parser.parseGame(lexer);
      } else if ("element".equals(blockIdentifier)) {
         if (game == null) {
           throw new TokenException("Grid element unexistend");
         }
         Parser.parseElement(lexer, game);
      }
    }
  }
  
}
