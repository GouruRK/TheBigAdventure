package graphic;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import fr.umlv.zen5.Application;
import game.Game;
import game.Inventory;
import game.entity.item.DroppedItem;
import game.entity.item.Item;
import game.entity.mob.Mob;
import game.environnement.Environnement;
import util.Direction;
import util.PathCreator;
import util.Position;

public class Window {

  // ------- Constant -------
   
  private static final int FPS = 60;
  private static final int MOBUPDATE = 20;
  private static final double BLOCK_PER_MOVEMENT = 1;

  // ------- General -------
  
  private final Game game;
  private final HashMap<String, BufferedImage> skinMap;
  private long totalFrame;
  
  // ------- Inventory related -------
  
  private boolean isInventoryShow;
  private Position cursor;

  //------- Constructor -------
  
  public Window(Game game) throws IOException {
    Objects.requireNonNull(game);
    this.game = game;
    skinMap = new HashMap<String, BufferedImage>();
    loadSkin();
    totalFrame = 0;
    isInventoryShow = false;
    cursor = new Position(0, 0);
  }

  //------- Skin related -------
  
  /**
   * load skin on the screen
   * 
   * @param skin
   * @throws IOException
   */
  private void loadSkin(String skin) throws IOException {
    if (skinMap.get(skin) != null) {
      return;
    }
    File imagePath = new File(PathCreator.imagePath(skin.toLowerCase()));
    try {
      skinMap.put(skin, ImageIO.read(imagePath));
    } catch (IOException e) {
      throw new IOException("Cannot find image for skin '" + skin + "' (path is '" + imagePath + "')");
    }
  }
  
  /**
   * load all skin of Environnement, DroppedItem, Mob to setup de map
   * 
   * @throws IOException
   */
  private void loadSkin() throws IOException {
    for (var line: game.field()) {
      for (Environnement env: line) {
        if (env != null) {
          loadSkin(env.skin());          
        }
      }
    }

    for (DroppedItem item: game.items()) {
      loadSkin(item.skin());
    }
    for (Mob mob: game.mobs()) {
      loadSkin(mob.skin());
    }
    loadSkin(game.player().skin());
  }
  
  //------- Movement related -------

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

  private void moveMobs() {
    game.mobs().forEach(mob -> game.move(mob, Direction.randomDirection(), 1));
  }
    
  private void moveCursor(Direction dir) {
    int x = (int)cursor.x();
    int y = (int)cursor.y();
    if (x == Inventory.NB_COLS - 1 && dir == Direction.EAST) return;
    if (x == 0 && dir == Direction.WEST) return;
    if (y == Inventory.NB_ROWS - 1 && dir == Direction.SOUTH) return;
    if (y == 0 && dir == Direction.NORTH) return;
    cursor = cursor.computeDirection(dir, 1);
  }
  
  //------- Time related -------
  
  /**
   * Clock for the entire game / and regulate the Frame rate
   * 
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
  
  public void play() {
    Application.run(Color.BLACK, context -> {
      long startTime;
      boolean needUpdate = false, useItem = false;

      Draw window = new Draw(context, game, skinMap);
      Controller controller = new Controller(context);
      KeyOperation key;

      window.drawGame(isInventoryShow, cursor, false);
      while ((key = controller.getOperation()) != KeyOperation.EXIT) {

        startTime = System.nanoTime();
        
        if (key == KeyOperation.UP || key == KeyOperation.RIGHT || key == KeyOperation.DOWN || key == KeyOperation.LEFT) {
          if (isInventoryShow) {
            moveCursor(Window.keyToDirection(key));
          } else {
            game.move(game.player(), Window.keyToDirection(key), BLOCK_PER_MOVEMENT);
          }
          needUpdate = true;
        } else if (key == KeyOperation.INVENTORY) {
          isInventoryShow = !isInventoryShow;
          needUpdate = true;
        } else if (key == KeyOperation.ACTION) {
          if (isInventoryShow) {
            Item held = game.player().removeHeldItem();
            Item fromInventory = game.inventory().remove(cursor);
            if (fromInventory != null) {
              game.player().setHold(fromInventory);
            }
            if (held != null) {              
              game.inventory().add(held);
            }
            isInventoryShow = false;
          } else {
            useItem = true;
            game.action();
          }
          needUpdate = true;
        }
        
        if (totalFrame % MOBUPDATE == 0 && !isInventoryShow) {
          moveMobs();
          needUpdate = true;
        }
        
        if (needUpdate) {
          window.drawGame(isInventoryShow, cursor, useItem);
          useItem = false;
          needUpdate = false;
        }
        
        computeTimeDelay(startTime, System.nanoTime());
        totalFrame++;
      }
      context.exit(0);
    });
  }
}
