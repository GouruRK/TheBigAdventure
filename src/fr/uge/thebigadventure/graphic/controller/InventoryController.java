package fr.uge.thebigadventure.graphic.controller;

import java.util.Objects;

import fr.uge.thebigadventure.game.Inventory;
import fr.uge.thebigadventure.game.entity.item.Item;
import fr.uge.thebigadventure.game.entity.mob.Player;
import fr.uge.thebigadventure.util.Direction;
import fr.uge.thebigadventure.util.Position;

public class InventoryController {
  
  // ------- Fields -------
  
  private final Inventory inventory;
  private final Player player;
  private boolean isInventoryInterfaceShow;
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
  
  public Position cursor() {
    return cursor;
  }
  
  public Inventory inventory() {
    return inventory;
  }
  
  public Player player() {
    return player;
  }
  
  public boolean isInventoryInterfaceShow() {
    return isInventoryInterfaceShow;
  }
  
  //------- Setter -------
  
  public void toggleIsInventoryInterfaceShow() {
    isInventoryInterfaceShow = !isInventoryInterfaceShow;
  }
  
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
