import java.io.IOException;
import java.nio.file.Path;

import game.Game;
import graph.Window;
import parser.Parser;
import parser.TokenException;

public class Main {
  public static void main(String[] args) throws IOException,
                                                TokenException {
    Path mapPath = Path.of("map/monster_house.map");
    Parser parser = new Parser(mapPath);
    Game game = parser.parseMap();
    
    Window win = new Window(game);
    win.play();
  }
}
