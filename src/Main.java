import java.io.IOException;
import java.nio.file.Path;

import game.Game;
import graphic.view.View;
import parser.Parser;
import parser.TokenException;

public class Main {
  public static void main(String[] args) {
    
    Path mapPath = Path.of("map/fun.map");
    Parser parser;
    Game game;
    View win = null;
    
    try {
      parser = new Parser(mapPath);
      game = parser.parseMap();
     
      game.player().takeDamage(3);
      
      win = new View(game);
    } catch (TokenException e) {
      System.err.println(e.getMessage());
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
    
    win.play();
  }
}
