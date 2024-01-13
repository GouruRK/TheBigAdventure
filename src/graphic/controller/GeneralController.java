package graphic.controller;

import java.awt.Color;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import fr.umlv.zen5.Application;
import game.Game;
import game.entity.item.Bucket;
import game.entity.item.Food;
import game.entity.item.GameItems;
import game.entity.item.Item;
import game.entity.item.Readable;
import game.entity.item.Thing;
import game.entity.item.Weapon;
import game.entity.mob.Friend;
import game.entity.mob.Mob;
import game.entity.mob.Player;
import game.environment.Environment;
import game.environment.GameEnvironment;
import game.environment.Gate;
import graphic.view.Draw;
import graphic.view.View;
import parser.commandline.Arguments;
import util.Direction;
import util.Position;

public class GeneralController {
  
  // ------- Fields -------
  
  private final InventoryController inventoryController;
  private final TradeController tradeController;
  private final Game game;
  private final TextController textController;
  private boolean hasItemBeenUsed = false;
  
  private static final int FPS = 60;
  private long totalFrame;
  
  // ------- Constructor -------
  
  public GeneralController(Game game) {
    Objects.requireNonNull(game);
    inventoryController = new InventoryController(game.inventory(), game.player());
    textController = new TextController();
    tradeController = new TradeController(inventoryController, textController);
    this.game = game;
  }
  
  // ------- Getter -------
  
  /**
   * Get controller for inventory
   * @return
   */
  public InventoryController inventoryController() {
    return inventoryController;
  }
  
  /**
   * Get controller for trade
   * @return
   */
  public TradeController tradeController() {
    return tradeController;
  }
  
  /**
   * Get controller for text
   * @return
   */
  public TextController textController() {
    return textController;
  }
  
  /**
   * Return if the item currently held by the player has been used
   * @return
   */
  public boolean hasItemBeenUsed() {
    return hasItemBeenUsed;
  }
  
  /**
   * Get game framerate
   * @return
   */
  public static int getFramerate() {
    return FPS;
  }
  
  // ------- Commands -------  
  
  /**
   * Change status of an used item
   * @param status
   */
  private void setHasItemBeenUsed(boolean status) {
    hasItemBeenUsed = status;
  }
  
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
    if (tradeController.isTradeInterfaceShow()) {
      tradeController.toggleIsTradeInterfaceShow();
    } else if (textController.isTextInterfaceShow()) {
      textController.toggleIsTextInterfaceShow();
    } else {
      inventoryController.toggleIsInventoryInterfaceShow();
    }
  }
  
  /**
   * Check if any of the interfaces are displayed
   * @return
   */
  private boolean areInterfacesShow() {
    return tradeController.isTradeInterfaceShow() 
        || textController.isTextInterfaceShow() 
        || inventoryController.isInventoryInterfaceShow();
  }
  
  /**
   * If the player hold an item, drop it at its facing direction.
   * If the next block is an obstacle, drop it at the current player position
   */
  private void dropItem() {
    Player player = game.player();
    if (player.hold() != null) {
      Position facing = player.pos().facingDirection(player.facing());
      Environment env = game.searchEnvironment(facing);
      if (env != null && !env.standable()) {
        facing = player.pos();
      }

      if (env != null && env.getEnvironment() == GameEnvironment.FIRE) {
        game.addDroppedItem(Item.setFire(player.hold()), facing);
      } else {
        game.addDroppedItem(player.hold(), facing);
      }
      
      player.removeHeldItem();
    }
  }
  
  /**
   * Interpret movements, according to which interface is currently displayed
   * @param dir
   */
  private void move(Direction dir) {
    if (tradeController.isTradeInterfaceShow()) {
      tradeController.moveCursor(dir);
    } else if (inventoryController.isInventoryInterfaceShow()) {
      inventoryController.moveCursor(dir);
    } else if (textController.isTextInterfaceShow()) {
      textController.changePage(dir);
    } else {
      game.move(game.player(), dir, 1);
    }
  }
  
  //------- Actions -------
  
  /**
   * Manage player action on the map and on the interfaces
   */
  private void action() {
    if (tradeController.isTradeInterfaceShow()) {
      tradeController.tradeItem();
    } else if (inventoryController.isInventoryInterfaceShow()) {
      inventoryController.exchangeItem();
    } else {
      Position facing = game.player().pos().facingDirection(game.player().facing());
      Mob mob;
      Environment env;
      
      if (game.player().hold() != null && game.player().hold().getItem() == GameItems.BOLT) {
        for (int i = 0; i < 3; i++) {
          mob = game.searchMob(facing);
          env = game.searchEnvironment(facing);
          if (!playerAction(mob, env)) {
            useHeldItem(mob, env);
            setHasItemBeenUsed(true);
          }
          facing = facing.computeDirection(game.player().facing(), 1);
        }
      } else {
        mob = game.searchMob(facing);
        env = game.searchEnvironment(facing);
        if (!playerAction(mob, env)) {
          useHeldItem(mob, env);
          setHasItemBeenUsed(true);
        }
      }
    }
  }
  
  /**
   * Manage player action to trade items
   * @return true if an action has been done, else false
   */
  public boolean playerAction(Mob mob, Environment env) {
    if (mob != null) {
      switch (mob) {
      case Friend friend -> {
        if (friend.hasTrade() || friend.hasText()) {
          tradeController.setFriend(friend);
          tradeController.toggleIsTradeInterfaceShow();
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
  private void useHeldItem(Mob mob, Environment env) {
    Player player = game.player();
    if (player.hold() == null) {
      return;
    }
    System.out.println(player.hold());

    switch (player.hold()) {
      case Weapon weapon -> useWeapon(weapon, mob, env);
      case Food food -> player.eat();
      case Thing thing -> useItem(thing, mob, env);
      case Readable item -> read(item);
      case Bucket bucket -> useBucket(bucket, env);
      default -> {}
    }
  }
  
  private void useItem(Thing item, Mob mob, Environment env) {
    if (env == null) {
      return;
    }
    switch (env) {
      case Gate gate -> gate.open(item);
      default -> {}
      
    }
  }
  
  /**
   * Change controllers to display the textInterface
   * @param item
   */
  private void read(Readable item) {
    textController.toggleIsTextInterfaceShow();
    textController.setText(item.text());
  }
  
  /**
   * Use the given weapon on either mob or environment
   * @param weapon
   * @param mob
   * @param env
   */
  private void useWeapon(Weapon weapon, Mob mob, Environment env) {
    if (mob != null) {
      game.attackMob(game.player(), mob);
    } 
    if (env != null) {
      Position pos = env.pos();
      if (env.getEnvironment() == GameEnvironment.TREE) {
        if (weapon.getItem() == GameItems.SWORD) {
          game.removeEnvironment(pos);
          game.addDroppedItem(Item.createItem("box"), pos);           
        } else if (weapon.getItem() == GameItems.BOLT) {
          game.removeEnvironment(pos);
          game.setEnvironment("fire", pos);
        }
      } else if (env.getEnvironment() == GameEnvironment.GRASS) {
        if (weapon.getItem() == GameItems.SHOVEL && game.inventory().contains(new Thing("seed"))) {
          game.removeEnvironment(pos);
          game.setEnvironment("sprout", pos);
          game.inventory().remove(new Thing("seed"));
        }
      }
    }
  }
  
  /**
   * Use the given bucket on environment
   * @param bucket
   * @param env
   */
  private void useBucket(Bucket bucket, Environment env) {
    if (bucket.isEmpty()) {
      if (env.getEnvironment() == GameEnvironment.WATER) {
        bucket.fillBucket(env);
      }
    } else {
      if (env != null) {
        if (env.getEnvironment() == GameEnvironment.FIRE) {
          game.removeEnvironment(env.pos());
          bucket.pourBucket();
        } else if (env.getEnvironment() == GameEnvironment.SPROUT) {
          game.removeEnvironment(env.pos());
          game.setEnvironment(Environment.createEnvironment("tree", env.pos()));
          bucket.pourBucket();
        }
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
      // game.moveMobs();
      return true;
    }
    return false;
  }
  
  /**
   * Compute time delay to respect the framerate
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
  
  /**
   * Main function
   */
  public void run() {
    Application.run(Color.BLACK, context -> {
      long startTime;
      Draw draw = new Draw(context, game, this);
      KeyOperation key;

      draw.drawGame();
      while ((key = View.getOperation(context)) != KeyOperation.EXIT) {
        startTime = System.nanoTime();
        
        if (interpretCommand(key) || entityUpdate(totalFrame)) {
          draw.drawGame();
        }
        
        computeTimeDelay(startTime, System.nanoTime());
        totalFrame++;
      }
      context.exit(0);
    });
  }
  
}
