package graphic.controller;

import java.util.Objects;

import game.Game;
import game.entity.item.Food;
import game.entity.item.GameItems;
import game.entity.item.Item;
import game.entity.item.Readable;
import game.entity.item.Thing;
import game.entity.item.Weapon;
import game.entity.mob.Friend;
import game.entity.mob.Mob;
import game.entity.mob.Player;
import game.environnement.Environnement;
import game.environnement.GameEnvironnement;
import game.environnement.Gate;
import graphic.view.View;
import parser.commandline.Arguments;
import util.Direction;
import util.Position;

public class GeneralController {
  
  // ------- Fields -------
  
  private final InventoryController inventory;
  private final TradeController trade;
  private final Game game;
  private final TextController text;
  private boolean hasItemBeenUsed = false;
  
  // ------- Constructor -------
  
  public GeneralController(Game game) {
    Objects.requireNonNull(game);
    inventory = new InventoryController(game.inventory(), game.player());
    text = new TextController();
    trade = new TradeController(inventory, text);
    this.game = game;
  }
  
  // ------- Getter -------
  
  public InventoryController inventoryController() {
    return inventory;
  }
  
  public TradeController tradeController() {
    return trade;
  }
  
  public TextController textController() {
    return text;
  }
  
  public boolean hasItemBeenUsed() {
    return hasItemBeenUsed;
  }
  
  private void setHasItemBeenUsed(boolean status) {
    hasItemBeenUsed = status;
  }
  
  // ------- Commands -------
  
  /**
   * Determine an action based on the given keyOperation
   * @param key
   * @return true if the game'graphics need to be updated, else false
   */
  public boolean interpretCommand(KeyOperation key) {
    Objects.requireNonNull(key);
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
  
  // ------- Generic -------
  
  /**
   * Toggle interfaces display
   */
  private void toggleInterfaces() {
    if (trade.isTradeInterfaceShow()) {
      trade.toggleIsTradeInterfaceShow();
    } else if (text.isTextInterfaceShow()) {
      text.toggleIsTextInterfaceShow();
    } else {
      inventory.toggleIsInventoryInterfaceShow();
    }
  }
  
  private boolean areInterfacesShow() {
    return trade.isTradeInterfaceShow() 
        || text.isTextInterfaceShow() 
        || inventory.isInventoryInterfaceShow();
  }
  
  /**
   * If the player hold an item, drop it at its facing direction.
   * If the next block is an obstacle, drop it at the current player position
   */
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
  
  /**
   * Interpret movements, according to which interface is currently displayed
   * @param dir
   */
  private void move(Direction dir) {
    if (trade.isTradeInterfaceShow()) {
      trade.moveCursor(dir);
    } else if (inventory.isInventoryInterfaceShow()) {
      inventory.moveCursor(dir);
    } else if (text.isTextInterfaceShow()) {
      text.changePage(dir);
    } else {
      game.move(game.player(), dir, 1);
    }
  }
  
  //------- Actions -------
  
  /**
   * Manage player action on the map and on the interfaces
   */
  private void action() {
    if (trade.isTradeInterfaceShow()) {
      trade.tradeItem();
    } else if (inventory.isInventoryInterfaceShow()) {
      inventory.exchangeItem();
    } else {
      Position facing = game.player().pos().facingDirection(game.player().facing());
      Mob mob = game.searchMob(facing);
      Environnement env = game.searchEnvironnement(facing);
      if (!playerAction(mob, env)) {
        useHeldItem(mob, env);
        setHasItemBeenUsed(true);
      }
    }
  }
  
  /**
   * Manage player action to trade items
   * @return true if an action has been done, else false
   */
  public boolean playerAction(Mob mob, Environnement env) {
    if (mob != null) {
      switch (mob) {
      case Friend friend -> {
        if (friend.hasTrade() || friend.hasText()) {
          trade.setFriend(friend);
          trade.toggleIsTradeInterfaceShow();
          return true;
        }
      }
      default -> {}
      }
      
    }
    return false;
  }
  
  /**
   * Use the current held item
   */
  private void useHeldItem(Mob mob, Environnement env) {
    Player player = game.player();
    if (player.hold() == null) {
      return;
    }

    switch (player.hold()) {
      case Weapon weapon -> useWeapon(mob, env);
      case Food food -> player.eat();
      case Thing thing -> useItem(mob, env);
      case Readable item -> read(item); 
      default -> {}
    }
  }
  
  private void useItem(Mob mob, Environnement env) {
    if (env == null) {
      return;
    }
    switch (env) {
      case Gate gate -> gate.open(game.player().hold());
      default -> {}
    }
  }
  
  private void read(Readable item) {
    text.toggleIsTextInterfaceShow();
    text.setText(item.text());
  }
  
  private void useWeapon(Mob mob, Environnement env) {
    if (mob != null) {
      game.attackMob(game.player(), mob);
    } else {
      if (env != null && env.getEnvironnement() == GameEnvironnement.TREE
          && game.player().hold().getItem() == GameItems.SWORD) {
        Position pos = env.pos();
        game.removeEnvironnement(pos);
        game.addDroppedItem(Item.createItem("box"), pos);
      }
    }
  }
  
  /**
   * Update entities according to the number of frames 
   * @param frames
   * @return
   */
  public boolean entityUpdate(long frames) {
    if (areInterfacesShow() || Arguments.dryRun()) {
      return false;
    }
    if (frames % 10 == 0) {
      game.moveMobs();
      return true;
    }
    return false;
  }
  
}
