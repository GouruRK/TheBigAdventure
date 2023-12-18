package graphic;

import java.util.Objects;

import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.KeyboardKey;
import fr.umlv.zen5.Event.Action;

public class Controller {

  private final ApplicationContext context;
  
  public Controller(ApplicationContext context) {
    Objects.requireNonNull(context);
    this.context = context;
  }
  
  /**
   * Convert an Event into a KeyOperation if a like (ZQSD) and (UP/RIGHT/DOWN/LEFT Arrows) are pressed
   * 
   * @param context
   * @return KeyOperation
   */
  public KeyOperation getOperation() {
    Event event = context.pollEvent();
    if (event == null || event.getAction() == Action.KEY_RELEASED) {
      return KeyOperation.NONE;
    }
    return getOperation(event.getKey());
  }
  
  private KeyOperation getOperation(KeyboardKey key) {
    return switch (key) {
    case KeyboardKey.UP, KeyboardKey.Z -> KeyOperation.UP;
    case KeyboardKey.RIGHT, KeyboardKey.D -> KeyOperation.RIGHT;
    case KeyboardKey.DOWN, KeyboardKey.S -> KeyOperation.DOWN;
    case KeyboardKey.LEFT, KeyboardKey.Q -> KeyOperation.LEFT;
    case KeyboardKey.I -> KeyOperation.INVENTORY;
    case KeyboardKey.SPACE -> KeyOperation.ACTION;
    case KeyboardKey.UNDEFINED -> KeyOperation.EXIT;
    default -> KeyOperation.NONE;
    };
  }
}
