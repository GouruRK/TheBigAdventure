package fr.uge.thebigadventure.parser;

public class TokenException extends Exception { 
  
  /**
   * Serialization id
   */
  private static final long serialVersionUID = -6185913124558075239L;

  public TokenException(String errorMessage) {
      super(errorMessage);
  }
  
  public TokenException(String errorMessage, String file) {
    super("Error in " + file + ": " + errorMessage);
  }
  
  public TokenException(String errorMessage, String file, int line) {
    super("Error in " + file + " at line " + line + ": " + errorMessage);
  }
  
}
