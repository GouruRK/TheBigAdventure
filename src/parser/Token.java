package parser;

/**
 * Enumeration of tokens and their regular expression that represents them.
 * 
 * @author Forax Remy
 * @author De Oliveira Nelson
 * @author Kies Rémy
 * 
 */
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
  
  /**
   * Regular expression that represent the token
   */
  private final String regex;

  /**
   * Constructor for Token
   * @param regex
   */
  Token(String regex) {
    this.regex = regex;
  }
  
  /**
   * Getter for regex
   * @return regex of the token
   */
  public String regex() {
    return this.regex;
  }
}
