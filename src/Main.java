import java.io.IOException;

import game.Game;
import graphic.controller.GeneralController;
import graphic.view.Skins;
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
    
    try {
      parser = new Parser(Arguments.level());
      game = parser.parseMap();
     
      if (Arguments.validate()) {
        return;
      }
      
      Skins.loadSkinFromGame(game);
      
      GeneralController controller = new GeneralController(game);
      controller.run();
    } catch (TokenException e) {
      System.err.println(e.getMessage());
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
    
  }
}
