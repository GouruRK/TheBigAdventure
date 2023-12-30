package graphic.view;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import fr.umlv.zen5.Application;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.Event.Action;
import fr.umlv.zen5.KeyboardKey;
import game.Game;
import graphic.controller.GeneralController;
import graphic.controller.KeyController;
import graphic.controller.KeyOperation;
import util.Direction;

public class View {

  // ------- Constant -------
   
  private static final int FPS = 60;
  public static final int IMAGESIZE = 24;

  // ------- General -------
  
  private final Game game;
  private final HashMap<String, BufferedImage> skinMap;
  private long totalFrame;
  
  //------- Constructor -------
  
  public View(Game game) throws IOException {
    Objects.requireNonNull(game);
    skinMap = new HashMap<String, BufferedImage>();
    this.game = game;
    totalFrame = 0;
    Skins.loadSkinFromGame(game);
  }
  
  /**
   * Convert a KeyOperation into a Direction
   * 
   * @param key
   * @return Direction
   */
  public static Direction keyToDirection(KeyOperation key) {
    return switch (key) {
    case KeyOperation.UP -> Direction.NORTH;
    case KeyOperation.RIGHT -> Direction.EAST;
    case KeyOperation.DOWN -> Direction.SOUTH; 
    case KeyOperation.LEFT -> Direction.WEST;
    default -> Direction.NONE;
    };
  }

  //------- Time related -------
  
  /**
   * Clock for the entire game / and regulate the Frame rate
   * 
   * @param startTime
   * @param endTime
   */
  private void computeTimeDelay(long startTime, long endTime) {
    long timeDiff = endTime - startTime;
    double delay = 1.0 / FPS - (timeDiff / 1.0e9);
    
    if (delay > 0) {
      try {
        TimeUnit.MILLISECONDS.sleep((int)(delay * 1000));
      } catch (InterruptedException e) {
      }
    }  
  }
  
  public KeyOperation getOperation(ApplicationContext context) {
    Event event = context.pollEvent();
    if (event == null || event.getAction() == Action.KEY_RELEASED) {
      return KeyOperation.NONE;
    }
    KeyboardKey key = event.getKey();
    
    return key == null ? KeyOperation.NONE: KeyController.getOperation(key);
  }
  
  public void play() {
    Application.run(Color.BLACK, context -> {
      long startTime;
      GeneralController controller = new GeneralController(game);
      Draw draw = new Draw(context, game, controller, skinMap);
      KeyOperation key;

      draw.drawGame();
      while ((key = getOperation(context)) != KeyOperation.EXIT) {
        startTime = System.nanoTime();
        
        if (controller.interpretCommand(key) || controller.entityUpdate(totalFrame)) {
          draw.drawGame();
        }
        
        computeTimeDelay(startTime, System.nanoTime());
        totalFrame++;
      }
      context.exit(0);
    });
  }
}
