package graph;

import java.awt.Color;
import java.awt.Graphics2D;
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
import game.entity.item.DroppedItem;
import game.entity.mob.Mob;
import game.entity.mob.Player;
import game.environnement.Environnement;
import util.Direction;
import util.Position;

public class Window {

  private final int IMAGESIZE = 24;
  private final int FPS = 60;
  private final int MOBUPDATE = 60;

  private final Game game;
  private final HashMap<String, BufferedImage> skinMap;
  private int windowWidth;
  private int windowHeight;
  private long totalFrame;

  public Window(Game game) throws IOException {
    Objects.requireNonNull(game);
    this.game = game;
    skinMap = new HashMap<String, BufferedImage>();
    loadSkin();
    totalFrame = 0;
  }

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
    File imagePath = new File("images/" + skin.toLowerCase() + ".png");
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

  class Area {

    private ApplicationContext context;

    /*
     * Create Area context
     */
    public Area(ApplicationContext context) {
      this.context = context;
    }

    /**
     * Clear the window
     * 
     * @param graphics
     */
    void clearWindow(Graphics2D graphics) {
      graphics.setColor(Color.BLACK);
      graphics.fill(new Rectangle2D.Float(0, 0, windowWidth, windowHeight));
    }

    /**
     * draw all mobs
     * 
     * @param graphics
     */
    void drawMobs(Graphics2D graphics) {
      game.mobs().forEach(mob -> {
        drawImage(graphics, mob.pos(), mob.skin());
        drawHealthBar(graphics, mob);
      });
    }

    /**
     * draw health bar's Mobs
     * 
     * @param graphics
     * @param mob
     */
    void drawHealthBar(Graphics2D graphics, Mob mob) {
      graphics.setColor(Color.RED);
      Rectangle2D.Double rectMaxHealth = new Rectangle2D.Double(mob.pos().x()*IMAGESIZE + 4, mob.pos().y()*IMAGESIZE + 1, 16, 4);
      graphics.fill(rectMaxHealth);
      
      graphics.setColor(Color.GREEN);    	
      Rectangle2D.Double rectCurrentHealth = new Rectangle2D.Double(mob.pos().x()*IMAGESIZE + 4, mob.pos().y()*IMAGESIZE + 1, 16*(mob.health()/mob.maxHealth()), 4);
      graphics.fill(rectCurrentHealth);
    }

    void drawImage(Graphics2D graphics, Position pos, String skin) {
      graphics.drawImage(skinMap.get(skin), (int) (pos.x()*IMAGESIZE), (int) (pos.y()*IMAGESIZE), null);
    }

    void drawEnvironnement(Graphics2D graphics, Environnement env) {
      drawImage(graphics, env.pos(), env.skin());
    }


    void drawPlayer(Graphics2D graphics) {
      drawImage(graphics, game.player().pos(), game.player().skin());
      drawHealthBar(graphics, game.player());
    }

    void drawDroppedItems(Graphics2D graphics) {
      game.items().forEach(item -> drawImage(graphics, item.pos(), item.skin()));
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
        graphics.dispose();
      });
    }
  }

  /**
   * Convert an Event into a KeyOperation if a like (ZQSD) and (UP/RIGHT/DOWN/LEFT Arrows) are pressed
   * 
   * @param context
   * @param player
   * @return KeyOperation
   */
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
    case KeyboardKey.UNDEFINED -> KeyOperation.EXIT;
    default -> KeyOperation.NONE;
    };
  }

  /**
   * Init a window screen depending of the screenInfo's user
   * 
   * @param context
   */
  private void initWindowSize(ApplicationContext context) {
    ScreenInfo screenInfo = context.getScreenInfo();
    windowWidth = (int) screenInfo.getWidth();
    windowHeight = (int) screenInfo.getHeight();
  }

  private void moveMobs() {
    game.mobs().forEach(mob -> game.move(mob, Direction.randomDirection(), 1));
  }
  
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
      boolean needUpdate = false;

      initWindowSize(context);

      Area area = new Area(context);
      KeyOperation key;

      area.drawGame();
      while ((key = controller(context, game.player())) != KeyOperation.EXIT) {

        startTime = System.nanoTime();

        if (key == KeyOperation.UP || key == KeyOperation.RIGHT || key == KeyOperation.DOWN || key == KeyOperation.LEFT) {
          game.move(game.player(), Window.keyToDirection(key),  1);
          needUpdate = true;
        }

        if (totalFrame % MOBUPDATE == 0) {
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
