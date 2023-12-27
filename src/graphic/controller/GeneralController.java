package graphic.controller;

import game.Game;
import game.entity.item.Item;
import graphic.view.View;

public class GeneralController {
  
  private final InventoryController inventory;
  private final Game game;
  private boolean hasItemBeenUsed;
  
  public GeneralController(Game game) {
    inventory = new InventoryController();
    this.game = game;
    hasItemBeenUsed = false;
  }
  
  public InventoryController inventory() {
    return inventory;
  }
  
  public boolean hasItemBeenUsed() {
    return hasItemBeenUsed;
  }
  
  private void setHasItemBeenUsed(boolean status) {
    hasItemBeenUsed = status;
  }
  
  public boolean doAction(KeyOperation key) {
    setHasItemBeenUsed(false);
    
    if (key == KeyOperation.NONE)  {
      return false;
    }
    switch (key) {
    case KeyOperation.INVENTORY -> inventory.toggleInventoryDisplay();
    case KeyOperation.DROP -> game.dropItem();
    case KeyOperation.UP, KeyOperation.DOWN, KeyOperation.LEFT, KeyOperation.RIGHT -> {
      if (inventory.isInventoryDisplay()) {
        inventory.moveCursor(View.keyToDirection(key));
      } else {
        game.move(game.player(), View.keyToDirection(key), 1);
      }
    }
    case KeyOperation.ACTION -> {
      if (inventory.isInventoryDisplay()) {
        exchangeItem();
      } else {
        game.action();
        setHasItemBeenUsed(true);
      }
    }
    default -> {}
    }
    return true;
  }
 
  private final void exchangeItem() {
    Item held = game.player().removeHeldItem();
    Item fromInventory = game.inventory().remove(inventory.cursor());
    if (fromInventory != null) {
      game.player().setHold(fromInventory);
    }
    if (held != null) {              
      game.inventory().add(held);
    }
    inventory.toggleInventoryDisplay();
  }
  
  public boolean entityUpdate(long frames) {
    if (frames % 10 == 0) {
      game.moveMobs();
      return true;
    }
    return false;
  }
  
}
