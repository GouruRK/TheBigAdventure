package graphic.controller;

import game.Game;
import game.entity.item.Food;
import game.entity.item.Thing;
import game.entity.item.Weapon;
import game.entity.mob.Friend;
import game.entity.mob.Mob;
import game.entity.mob.Player;
import game.environnement.Environnement;
import game.environnement.Gate;
import graphic.view.View;
import util.Direction;
import util.Position;

public class GeneralController {
  
  private final InventoryController inventory;
  private final TradeController trade;
  private final Game game;
  private boolean hasItemBeenUsed;
  
  public GeneralController(Game game) {
    inventory = new InventoryController(game.inventory(), game.player());
    trade = new TradeController(inventory);
    this.game = game;
    hasItemBeenUsed = false;
  }
  
  public InventoryController inventory() {
    return inventory;
  }
  
  public TradeController trade() {
    return trade;
  }
  
  public boolean hasItemBeenUsed() {
    return hasItemBeenUsed;
  }
  
  private void setHasItemBeenUsed(boolean status) {
    hasItemBeenUsed = status;
  }
  
  public boolean interpretCommand(KeyOperation key) {
    setHasItemBeenUsed(false);
    
    if (key == KeyOperation.NONE)  {
      return false;
    }
    switch (key) {
    case KeyOperation.INVENTORY -> toggleInterfaces();
    case KeyOperation.DROP -> dropItem();
    case KeyOperation.UP, KeyOperation.DOWN, KeyOperation.LEFT, KeyOperation.RIGHT -> move(View.keyToDirection(key));
    case KeyOperation.ACTION -> action();
    default -> {}
    }
    return true;
  }
  
  private void toggleInterfaces() {
    if (trade.isTradeInterfaceShow()) {
      trade.toggleIsTradeInterfaceShow();
    } else {
      inventory.toggleInventoryDisplay(); 
    }
  }
  
  private void action() {
    if (trade.isTradeInterfaceShow()) {
      trade.tradeItem();
    } else if (inventory.isInventoryDisplay()) {
      inventory.exchangeItem();
    } else {
      playerAction();
      setHasItemBeenUsed(true);
    }
  }
  
  private void move(Direction dir) {
    if (trade.isTradeInterfaceShow()) {
      trade.moveCursor(dir);
    } else if (inventory.isInventoryDisplay()) {
      inventory.moveCursor(dir);
    } else {
      game.move(game.player(), dir, 1);
    }
  }
  
  private void dropItem() {
    Player player = game.player();
    if (player.hold() != null) {
      Position facing = player.pos().facingDirection(player.facing());
      Environnement env = game.searchEnvironnement(facing);
      if (env != null && !env.standable()) {
        facing = player.pos();
      }
      game.addDroppedItem(player.hold(), facing);
      player.removeHeldItem();
    }
  }
 
  public void playerAction() {
    Player player = game.player();
    Position facing = player.pos().facingDirection(player.facing());
    Environnement env = game.searchEnvironnement(facing);
    Mob mob = game.searchMob(facing);
    boolean exit = false;
    
    
    if (mob != null) {
      switch (mob) {
      case Friend friend -> {
        if (friend.trade() != null) {
          trade.setTrade(friend.trade());
          trade.toggleIsTradeInterfaceShow();
          exit = true;
        }
      }
      default -> {}
      }
    }
    
    if (exit) {
      return;
    }
    
    if (player.hold() == null) {
      return;
    }
    
    switch (player.hold()) {
      case Weapon weapon -> game.attackMob(player, game.searchMob(facing));
      case Food food -> player.eat();
      case Thing thing -> {
        if (env == null) {
          return;
        }
        switch (env) {
          case Gate gate -> gate.open(player.hold());
          default -> {}
        }
      }
      default -> {}
    }
  }
  
  public boolean entityUpdate(long frames) {
    if (trade.isTradeInterfaceShow() || inventory.isInventoryDisplay()) {
      return false;
    }
    if (frames % 10 == 0) {
      game.moveEnemies();
      game.agressiveMob();
      return true;
    }
    return false;
  }
  
}
