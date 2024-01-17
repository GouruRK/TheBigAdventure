package fr.uge.thebigadventure.parser;

@SuppressWarnings("serial")
public class TokenException extends Exception { 
  public TokenException(String errorMessage) {
      super(errorMessage);
  }
}
