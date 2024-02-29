package fr.uge.thebigadventure;

import java.io.IOException;

import fr.uge.thebigadventure.controller.GeneralController;
import fr.uge.thebigadventure.game.Game;
import fr.uge.thebigadventure.parser.Parser;
import fr.uge.thebigadventure.parser.TokenException;
import fr.uge.thebigadventure.parser.commandline.Arguments;
import fr.uge.thebigadventure.parser.commandline.CommandLineException;
import fr.uge.thebigadventure.view.Skins;

public class Main {
  public static void main(String[] args) {
    try {
      Arguments.parseArguments(args);
    
      if (Arguments.help()) {
        System.out.println(Arguments.getHelp());
        return;
      }

      Parser parser;
      Game game;
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
    } catch (CommandLineException e) {
      System.err.println(e.getMessage());
    }
  }
}
