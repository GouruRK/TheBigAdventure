package parser;

import java.util.HashMap;
import java.util.Map;

import game.GameObject;
import game.GameObjectID;
import util.Position;
import util.Zone;


public class Parser {
  
  
  private static String isExpected(Lexer lexer, Token token) throws TokenException {
    Result res = lexer.nextResult();
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
  
  public static Map<String, GameObjectID> parseEncoding(Lexer lexer) throws TokenException {
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
    return Map.<String, GameObjectID>of(code, id);
  }
  
}
