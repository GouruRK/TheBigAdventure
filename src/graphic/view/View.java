package graphic.view;

import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.Event.Action;
import fr.umlv.zen5.KeyboardKey;
import graphic.controller.KeyController;
import graphic.controller.KeyOperation;
import util.Direction;

public class View {

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
  
  public static KeyOperation getOperation(ApplicationContext context) {
    Event event = context.pollEvent();
    if (event == null || event.getAction() == Action.KEY_RELEASED) {
      return KeyOperation.NONE;
    }
    KeyboardKey key = event.getKey();
    
    return key == null ? KeyOperation.NONE: KeyController.getOperation(key);
  }
}
