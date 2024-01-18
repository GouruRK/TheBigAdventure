package fr.uge.thebigadventure.graphic.view;

import java.util.Objects;

import fr.uge.thebigadventure.graphic.controller.KeyOperation;
import fr.uge.thebigadventure.util.Direction;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.Event.Action;
import fr.umlv.zen5.KeyboardKey;

/**
 * Tools for View model
 */
public class View {

  /**
   * Convert a KeyOperation into a Direction
   * 
   * @param key
   * @return Direction
   */
  public static Direction keyToDirection(KeyOperation key) {
    Objects.requireNonNull(key);
    return switch (key) {
      case KeyOperation.UP -> Direction.NORTH;
      case KeyOperation.RIGHT -> Direction.EAST;
      case KeyOperation.DOWN -> Direction.SOUTH; 
      case KeyOperation.LEFT -> Direction.WEST;
      default -> Direction.NONE;
    };
  }
  
  /**
   * Get current action from user interactions
   * @param context
   * @return
   */
  public static KeyOperation getOperation(ApplicationContext context) {
    Objects.requireNonNull(context);
    Event event = context.pollEvent();
    if (event == null || event.getAction() == Action.KEY_RELEASED) {
      return KeyOperation.NONE;
    }
    KeyboardKey key = event.getKey();
    
    return key == null ? KeyOperation.NONE: KeyOperation.getOperation(key);
  }
}
