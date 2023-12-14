package graph;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import fr.umlv.zen5.Application;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.Event.Action;
import fr.umlv.zen5.KeyboardKey;
import fr.umlv.zen5.ScreenInfo;
import game.Game;
import game.Inventory;
import game.entity.item.DroppedItem;
import game.entity.item.Item;
import game.entity.mob.Mob;
import game.entity.mob.Player;
import game.environnement.Environnement;
import util.Direction;
import util.Position;

public class Window {

  // ------- Constant -------
   
  private static final int IMAGESIZE = 24;
  private static final int FPS = 60;
  private static final int MOBUPDATE = 10;

  // ------- General -------
  
  private final Game game;
  private final HashMap<String, BufferedImage> skinMap;
  private int windowWidth;
  private int windowHeight;
  private long totalFrame;
  
  // ------- Inventory related -------
  
  private static final int inventoryWidth = IMAGESIZE*Inventory.NB_COLS*3;
  private static final int inventoryHeight = IMAGESIZE*Inventory.NB_ROWS*2;
  private boolean isInventoryShow;
  private Position cursor;

  public Window(Game game) throws IOException {
    Objects.requireNonNull(game);
    this.game = game;
    skinMap = new HashMap<String, BufferedImage>();
    loadSkin();
    totalFrame = 0;
    isInventoryShow = false;
    cursor = new Position(0, 0);
  }

  private void loadSkin(String skin) throws IOException {
    if (skinMap.get(skin) != null) {
      return;
    }
    File imagePath = new File("images/" + skin.toLowerCase() + ".png");
    try {
      skinMap.put(skin, ImageIO.read(imagePath));
    } catch (IOException e) {
      throw new IOException("Cannot find image for skin '" + skin + "' (path is '" + imagePath + "')");
    }
  }

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

  public static Direction keyToDirection(KeyOperation key) {
    return switch (key) {
    case KeyOperation.UP -> Direction.NORTH;
    case KeyOperation.RIGHT -> Direction.EAST;
    case KeyOperation.DOWN -> Direction.SOUTH; 
    case KeyOperation.LEFT -> Direction.WEST;
    default -> Direction.NONE;
    };
  }

  class Area {

    private ApplicationContext context;

    public Area(ApplicationContext context) {
      this.context = context;
    }

    void clearWindow(Graphics2D graphics) {
      graphics.setColor(Color.BLACK);
      graphics.fill(new Rectangle2D.Float(0, 0, windowWidth, windowHeight));
    }

    void drawMobs(Graphics2D graphics) {
      game.mobs().forEach(mob -> {
        drawImage(graphics, mob.skin(), mob.pos());
        drawHealthBar(graphics, mob);
      });
    }

    void drawHoldedItem(Graphics2D graphics) {
      Player player = game.player();
      if (player.hold() == null) {
        return;
      }
      AffineTransform saveAT = graphics.getTransform();
      
      Position pos = player.pos().addY(0.3).addX(0.8);
      graphics.scale(0.8, 0.8);
      drawImage(graphics, player.hold().skin(), pos, 1.25); // 1.25 because 0.8*1.25 = 1
      
      graphics.setTransform(saveAT);
    }
    
    void drawHealthBar(Graphics2D graphics, Mob mob) {
      graphics.setColor(Color.RED);
      Rectangle2D.Double rectMaxHealth = new Rectangle2D.Double(mob.pos().x()*IMAGESIZE + 4, mob.pos().y()*IMAGESIZE + 1, 16, 4);
      graphics.fill(rectMaxHealth);
      
      graphics.setColor(Color.GREEN);    	
      Rectangle2D.Double rectCurrentHealth = new Rectangle2D.Double(mob.pos().x()*IMAGESIZE + 4, mob.pos().y()*IMAGESIZE + 1, 16*(mob.health()/mob.maxHealth()), 4);
      graphics.fill(rectCurrentHealth);
    }
    
    void drawImage(Graphics2D graphics, String skin, int x, int y) {
      graphics.drawImage(skinMap.get(skin), x, y, null);
    }
    
    void drawImage(Graphics2D graphics, String skin, double x, double y) {
      drawImage(graphics, skin, (int)x, (int)y);
    }

    void drawImage(Graphics2D graphics, String skin, Position pos) {
      drawImage(graphics, skin, pos.x()*IMAGESIZE, pos.y()*IMAGESIZE);
    }
    
    void drawImage(Graphics2D graphics, String skin, Position pos, double factor) {
      drawImage(graphics, skin, pos.x()*IMAGESIZE*factor, pos.y()*IMAGESIZE*factor);
    }

    void drawEnvironnement(Graphics2D graphics, Environnement env) {
      drawImage(graphics, env.skin(), env.pos());
    }

    void drawPlayer(Graphics2D graphics) {
      drawImage(graphics, game.player().skin(), game.player().pos());
      drawHealthBar(graphics, game.player());
    }

    void drawDroppedItems(Graphics2D graphics) {
      game.items().forEach(item -> drawImage(graphics, item.skin(), item.pos()));
    }
    
    void drawInventory(Graphics2D graphics) {
      graphics.setColor(Color.LIGHT_GRAY);
      
      int topX = windowWidth / 2 - inventoryWidth / 2;
      int topY = windowHeight / 2 - inventoryHeight / 2;
      Item item;
      
      Rectangle2D.Double inv = new Rectangle2D.Double(topX, topY, inventoryWidth, inventoryHeight);
      graphics.fill(inv);

      AffineTransform saveAT = graphics.getTransform();
      graphics.scale(2, 2);
      
      for (int y = 0; y < Inventory.NB_ROWS; y++) {
        for (int x = 0; x < Inventory.NB_COLS; x++) {
          if ((item = game.inventory().get(x, y)) != null) {
            drawImage(graphics, item.skin(), topX / 2 + x*IMAGESIZE*1.5 + IMAGESIZE/3, topY / 2 + y*IMAGESIZE);
          }
        }
      }
      graphics.setTransform(saveAT);
      
      graphics.setColor(Color.WHITE);
      for (int y = 0; y < Inventory.NB_ROWS + 1; y++) {
        graphics.drawLine(topX, topY + y*IMAGESIZE*2, topX + inventoryWidth, topY + y*IMAGESIZE*2);
      }
      
      for (int x = 0; x < Inventory.NB_COLS + 1; x++) {
        graphics.drawLine(topX + x*IMAGESIZE*3, topY, topX + x*IMAGESIZE*3, topY + inventoryHeight);
      }
      
      int x = (int)cursor.x();
      int y = (int)cursor.y();
      graphics.setColor(Color.BLACK);
      graphics.drawLine(topX + x*IMAGESIZE*3, topY + y*IMAGESIZE*2, topX + (x + 1)*IMAGESIZE*3, topY + y*IMAGESIZE*2);
      graphics.drawLine(topX + x*IMAGESIZE*3, topY + (y + 1)*IMAGESIZE*2, topX + (x + 1)*IMAGESIZE*3, topY + (y + 1)*IMAGESIZE*2);
      graphics.drawLine(topX + x*IMAGESIZE*3, topY + y*IMAGESIZE*2, topX + x*IMAGESIZE*3, topY + (y + 1)*IMAGESIZE*2);
      graphics.drawLine(topX + (x + 1)*IMAGESIZE*3, topY + y*IMAGESIZE*2, topX + (x + 1)*IMAGESIZE*3, topY + (y + 1)*IMAGESIZE*2);
    }

    void drawMap(Graphics2D graphics) {
      for (var line: game.field()) {
        for (Environnement env: line) {
          if (env != null) {
            drawEnvironnement(graphics, env);            
          }
        }
      }
    }

    void drawGame() {
      context.renderFrame(graphics -> {
        clearWindow(graphics);
        drawMap(graphics);
        drawPlayer(graphics);
        drawMobs(graphics);
        drawDroppedItems(graphics);
        drawHoldedItem(graphics);
        if (isInventoryShow) {
          drawInventory(graphics);
        }
      });
    }
  }

  public KeyOperation controller(ApplicationContext context, Player player) {
    Event event = context.pollEvent();
    if (event == null || event.getAction() == Action.KEY_RELEASED) {
      return KeyOperation.NONE;
    }
    // Action action = event.getAction();
    KeyboardKey key = event.getKey();

    if (key == null) {
      return KeyOperation.NONE;
    }
    
    return switch (key) {
    case KeyboardKey.UP, KeyboardKey.Z -> KeyOperation.UP;
    case KeyboardKey.RIGHT, KeyboardKey.D -> KeyOperation.RIGHT;
    case KeyboardKey.DOWN, KeyboardKey.S -> KeyOperation.DOWN;
    case KeyboardKey.LEFT, KeyboardKey.Q -> KeyOperation.LEFT;
    case KeyboardKey.I -> KeyOperation.INVENTORY;
    case KeyboardKey.UNDEFINED -> KeyOperation.EXIT;
    default -> KeyOperation.NONE;
    };
  }

  private void initWindowSize(ApplicationContext context) {
    ScreenInfo screenInfo = context.getScreenInfo();
    windowWidth = (int) screenInfo.getWidth();
    windowHeight = (int) screenInfo.getHeight();
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
      boolean needUpdate = false;

      initWindowSize(context);

      Area area = new Area(context);
      KeyOperation key;

      area.drawGame();
      while ((key = controller(context, game.player())) != KeyOperation.EXIT) {

        startTime = System.nanoTime();

        if (key == KeyOperation.UP || key == KeyOperation.RIGHT || key == KeyOperation.DOWN || key == KeyOperation.LEFT) {
          if (isInventoryShow) {
            moveCursor(Window.keyToDirection(key));
          } else {
            game.move(game.player(), Window.keyToDirection(key),  1);            
          }
          needUpdate = true;
        } else if (key == KeyOperation.INVENTORY) {
          isInventoryShow = !isInventoryShow;
          needUpdate = true;
        }
        
        if (totalFrame % MOBUPDATE == 0 && !isInventoryShow) {
          moveMobs();
          needUpdate = true;
        }
        
        if (needUpdate) {
          area.drawGame();
          needUpdate = false;
        }
        
        computeTimeDelay(startTime, System.nanoTime());
        totalFrame++;
      }
      context.exit(0);
    });
  }
}
