package parser;

import util.Position;


public class Parser {
  
  
  private static String isExpected(Lexer lexer, Token token) throws TokenException {
    Result res = lexer.nextResult();
    if (res != null && res.token() == token) {
      return res.content();
    } 
    if (res != null) {
      throw new TokenException("Awaited token of type " + token + ", got " + res.token());      
    }
    throw new TokenException("Awaited token, got none");
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
    // Note : we are here after "Result[token=IDENTIFIER, content=size]"
    // Next : we wait for : "( 'n' x 'm')"
    double width, height;
    Parser.isExpected(lexer, Token.LEFT_PARENS);
    width = Integer.parseInt(Parser.isExpected(lexer, Token.NUMBER));
    Parser.isExpected(lexer, Token.IDENTIFIER, "x");
    height = Integer.parseInt(Parser.isExpected(lexer, Token.NUMBER));
    Parser.isExpected(lexer, Token.RIGHT_PARENS);
    return new Position(width, height);
  }
  
  public static Position parsePosition(Lexer lexer) throws TokenException {
    // Note : we are here after "Result[token=IDENTIFIER, content=position]"
    // Next : we wait for : "('x', 'y')"
    double x, y;
    Parser.isExpected(lexer, Token.LEFT_PARENS);
    x = Integer.parseInt(Parser.isExpected(lexer, Token.NUMBER));
    Parser.isExpected(lexer, Token.IDENTIFIER, ",");
    y = Integer.parseInt(Parser.isExpected(lexer, Token.NUMBER));
    Parser.isExpected(lexer, Token.RIGHT_PARENS);
    return new Position(x, y);
  }
}
