package parser;

import java.util.ArrayList;
import java.util.stream.Collectors;

@SuppressWarnings("serial")
public class TokenException extends Exception { 
  
  private static final ArrayList<String> MESSAGE_STACK = new ArrayList<String>();
  
  public static void addMessage(String message) {
    MESSAGE_STACK.add(message);
  }
  
  public TokenException() {
    super(MESSAGE_STACK.stream().collect(Collectors.joining("\n")));
  }
  
  public TokenException(String errorMessage) {
      super(errorMessage);
  }
}
