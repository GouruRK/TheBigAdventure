package fr.uge.thebigadventure;

import java.io.IOException;

import fr.uge.thebigadventure.game.Game;
import fr.uge.thebigadventure.graphic.controller.GeneralController;
import fr.uge.thebigadventure.graphic.view.Skins;
import fr.uge.thebigadventure.parser.Parser;
import fr.uge.thebigadventure.parser.TokenException;
import fr.uge.thebigadventure.parser.commandline.Arguments;
import fr.uge.thebigadventure.parser.commandline.CommandLineException;

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
