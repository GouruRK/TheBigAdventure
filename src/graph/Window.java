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
import util.Position;

public class Window {

  private final int IMAGESIZE = 24;
  private final int FPS = 60;

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
  
  class Area {

    private ApplicationContext context;
    
    public Area(ApplicationContext context) {
      this.context = context;
    }
    
    void clearWindow(Graphics2D graphics) {
      graphics.setColor(Color.BLACK);
      graphics.fill(new  Rectangle2D.Float(0, 0, windowWidth, windowHeight));
    }
    
    void drawMobs(Graphics2D graphics) {
    	for (var mob : game.mobs()) {
    	    drawImage(graphics, mob.pos(), mob.skin());
    		drawHealthBar(graphics, mob);
    	}
    	drawImage(graphics, game.player().pos(), game.player().skin());
    	drawHealthBar(graphics, game.player());
    }
    
    void drawHealthBar(Graphics2D graphics, Mob mob) {
    	graphics.setColor(Color.RED);
    	final Rectangle2D.Double rectMaxHealth = new Rectangle2D.Double(((mob.pos().x())*IMAGESIZE)+4, ((mob.pos().y())*IMAGESIZE)+1, 16, 4);
    	graphics.fill(rectMaxHealth);
    	graphics.setColor(Color.GREEN);    	
    	final Rectangle2D.Double rectCurrentHealth = new Rectangle2D.Double(((mob.pos().x())*IMAGESIZE)+4, ((mob.pos().y())*IMAGESIZE)+1, 16*(mob.health()/mob.maxHealth()), 4);
    	graphics.fill(rectCurrentHealth);
    }
    
    void clearWindow() {
      context.renderFrame(graphics -> {
        clearWindow(graphics);
      });
    }

    void drawImage(Graphics2D graphics, Position pos, String skin) {
      graphics.drawImage(skinMap.get(skin), (int) (pos.x()*IMAGESIZE), (int) (pos.y()*IMAGESIZE), null);
    }
    
    void drawImage(Position pos, String skin) {
      context.renderFrame(graphics -> {
        drawImage(graphics, pos, skin);
      });
    }
    
    
    void drawEnvironnement(Graphics2D graphics, Environnement env) {
      drawImage(graphics, env.pos(), env.skin());
    }
    
    void drawEnvironnement(Environnement env) {
      drawImage(env.pos(), env.skin());
    }

    void drawPlayer(Graphics2D graphics) {
      drawImage(graphics, game.player().pos(), game.player().skin());
    }
    
    void drawPlayer() {
      drawImage(game.player().pos(), game.player().skin());
    }
    
    void drawDroppedItems(Graphics2D graphics) {
      for (DroppedItem item: game.items()) {
        drawImage(graphics, item.pos(), item.skin());
      }
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
    
    void drawMap() {
      for (var line: game.field()) {
        for (Environnement env: line) {
          if (env != null) {
            drawEnvironnement(env);            
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

  public KeyOperation controller(ApplicationContext context, Player player) {
    Event event = context.pollEvent();
    if (event == null || event.getAction() == Action.KEY_RELEASED) {
      return KeyOperation.NONE;
    }
    // Action action = event.getAction();
    KeyboardKey key = event.getKey();
    
    return switch (key) {
      case KeyboardKey.UP, KeyboardKey.Z -> KeyOperation.MOVE_UP;
      case KeyboardKey.RIGHT, KeyboardKey.D -> KeyOperation.MOVE_RIGHT;
      case KeyboardKey.DOWN, KeyboardKey.S -> KeyOperation.MOVE_DOWN;
      case KeyboardKey.LEFT, KeyboardKey.Q -> KeyOperation.MOVE_LEFT;
      case KeyboardKey.UNDEFINED -> KeyOperation.EXIT;
      default -> KeyOperation.NONE;
    };
  }

  private void initWindowSize(ApplicationContext context) {
    ScreenInfo screenInfo = context.getScreenInfo();
    windowWidth = (int) screenInfo.getWidth();
    windowHeight = (int) screenInfo.getHeight();
  }
  
  public void play() {
    
    Application.run(Color.BLACK, context -> {
      long start_time, end_time, time_diff;
      double extra_time;
      
      initWindowSize(context);
      
      Area area = new Area(context);
      KeyOperation op;
        
      area.drawGame();
      while ((op = controller(context, game.player())) != KeyOperation.EXIT) {
        
        start_time = System.nanoTime();
        
        if (op == KeyOperation.MOVE_UP || op == KeyOperation.MOVE_RIGHT || op == KeyOperation.MOVE_DOWN || op == KeyOperation.MOVE_LEFT) {
          game.move(game.player(), game.moveToDirection(op),  0.5);
          area.drawGame();
        }
        
        end_time = System.nanoTime();
        time_diff = end_time - start_time;
        extra_time = 1.0 / FPS - (time_diff / 1.0e9);
        if (extra_time > 0) {
          try {
            TimeUnit.MILLISECONDS.sleep((int)(extra_time * 1000));
          } catch (InterruptedException e) {
          }
        }
        totalFrame++;
      }
      context.exit(0);
    });
  }
}
