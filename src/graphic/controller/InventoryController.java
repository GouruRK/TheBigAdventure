package graphic.controller;

import game.Inventory;
import util.Direction;
import util.Position;

public class InventoryController {
  
  // ------- Fields -------
  
  private boolean isInventoryDisplay;
  private Position cursor;
  
  // ------- Constructor -------
  
  public InventoryController() {
    cursor = new Position(0, 0);
    isInventoryDisplay = false;
  }
  
  // ------- Getter -------
  
  public Position cursor() {
    return cursor;
  }
  
  public boolean isInventoryDisplay() {
    return isInventoryDisplay;
  }
  
  //------- Setter -------
  
  public void toggleInventoryDisplay() {
    isInventoryDisplay = !isInventoryDisplay;
  }
  
  public void moveCursor(Direction dir) {
    int x = (int)cursor.x();
    int y = (int)cursor.y();
    if (x == Inventory.NB_COLS - 1 && dir == Direction.EAST) return;
    if (x == 0 && dir == Direction.WEST) return;
    if (y == Inventory.NB_ROWS - 1 && dir == Direction.SOUTH) return;
    if (y == 0 && dir == Direction.NORTH) return;
    cursor = cursor.computeDirection(dir, 1);
  }
  
}
