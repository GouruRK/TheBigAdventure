package parser;

@SuppressWarnings("serial")
public class TokenException extends Exception { 
  public TokenException(String errorMessage) {
      super(errorMessage);
  }
}
