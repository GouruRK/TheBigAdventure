package fr.uge.thebigadventure.controller;

import java.util.Objects;

import fr.uge.thebigadventure.game.Inventory;
import fr.uge.thebigadventure.game.entity.item.Item;
import fr.uge.thebigadventure.game.entity.mob.Player;
import fr.uge.thebigadventure.util.Direction;
import fr.uge.thebigadventure.util.Position;

/**
 * Controller for player's input in the inventory
 */
public class InventoryController {
  
  // ------- Fields -------
  
  /**
   * Inventory object
   */
  private final Inventory inventory;
  
  /**
   * Player with its held item
   */
  private final Player player;
  
  /**
   * Give intel if the inventory interface is currently displayed
   */
  private boolean isInventoryInterfaceShow;
  
  /**
   * Inventory cursor to select items
   */
  private Position cursor;
  
  // ------- Constructor -------
  
  public InventoryController(Inventory inventory, Player player) {
    Objects.requireNonNull(inventory);
    Objects.requireNonNull(player);
    
    this.inventory = inventory;
    this.player = player;
    cursor = new Position(0, 0);
    isInventoryInterfaceShow = false;
  }
  
  // ------- Getter -------
  
  /**
   * Get cursor position
   * @return
   */
  public Position cursor() {
    return cursor;
  }
  
  /**
   * Get inventory
   * @return
   */
  public Inventory inventory() {
    return inventory;
  }
  
  /**
   * Get player
   * @return
   */
  public Player player() {
    return player;
  }
  
  /**
   * Get intel if the inventory interface is currently displayed
   * @return
   */
  public boolean isInventoryInterfaceShow() {
    return isInventoryInterfaceShow;
  }
  
  //------- Setter -------
  
  /**
   * Toggle inventory interface
   */
  public void toggleIsInventoryInterfaceShow() {
    isInventoryInterfaceShow = !isInventoryInterfaceShow;
  }
  
  /**
   * Exchange item from the player's hand and the inventory
   */
  public void exchangeItem() {
    Item held = player.removeHeldItem();
    Item fromInventory = inventory.remove(cursor());
    if (fromInventory != null) {
      player.setHold(fromInventory);
    }
    if (held != null) {              
      inventory.add(held);
    }
    toggleIsInventoryInterfaceShow();
  }
  
  /**
   * Inventory controller to move the cursor
   * @param dir
   */
  public void moveCursor(Direction dir) {
    Objects.requireNonNull(dir);
    
    int x = (int)cursor.x();
    int y = (int)cursor.y();
    if (x == Inventory.NB_COLS - 1 && dir == Direction.EAST) return;
    if (x == 0 && dir == Direction.WEST) return;
    if (y == Inventory.NB_ROWS - 1 && dir == Direction.SOUTH) return;
    if (y == 0 && dir == Direction.NORTH) return;
    cursor = cursor.computeDirection(dir);
  }
  
}
