package parser;

public enum Token {
  IDENTIFIER("[A-Za-z]+"),
  NUMBER("[0-9]+"),
  LEFT_PARENS("\\("),
  RIGHT_PARENS("\\)"),
  LEFT_BRACKET("\\["),
  RIGHT_BRACKET("\\]"),
  COMMA(","),
  COLON(":"),
  DOT("\\."),
  QUOTE("\"\"\"[^\"]+\"\"\""),
  ARROW("->"),
  NEWLINE("\\n");
  ;

  public final String regex;

  Token(String regex) {
    this.regex = regex;
  }
}
