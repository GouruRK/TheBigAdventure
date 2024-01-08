import java.io.IOException;

import game.Game;
import graphic.view.View;
import parser.Parser;
import parser.TokenException;
import parser.commandline.Arguments;
import parser.commandline.CommandLineException;

public class Main {
  public static void main(String[] args) {
    try {
      Arguments.parseArguments(args);      
    } catch (CommandLineException e) {
      System.err.println(e.getMessage());
      return;
    }
    
    Parser parser;
    Game game;
    View win = null;
    
    try {
      parser = new Parser(Arguments.level());
      game = parser.parseMap();
     
      if (Arguments.validate()) {
        return;
      }
      
      win = new View(game);
      win.play();
    } catch (TokenException e) {
      System.err.println(e.getMessage());
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
    
  }
}
