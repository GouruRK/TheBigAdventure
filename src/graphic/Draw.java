package graphic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Objects;

import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.ScreenInfo;
import game.Game;
import game.Inventory;
import game.entity.item.Item;
import game.entity.mob.Mob;
import game.entity.mob.Player;
import game.environnement.Environnement;
import util.Direction;
import util.Position;

public class Draw {
  
  //------- Constant -------
  
  private static final int IMAGESIZE = 24;
  private static final int inventoryWidth = IMAGESIZE*Inventory.NB_COLS*3;
  private static final int inventoryHeight = IMAGESIZE*Inventory.NB_ROWS*2;
  
  //------- Fields -------
  
  private final Map<String, BufferedImage> skinMap;
  private final ApplicationContext context;
  private final Game game;
  private final int windowWidth;
  private final int windowHeight;
  private final int inventoryTopX;
  private final int inventoryTopY;
  
  //------- Constructor -------
  
  public Draw(ApplicationContext context, Game game, Map<String, BufferedImage> skinMap) {
    Objects.requireNonNull(context);
    Objects.requireNonNull(game);
    Objects.requireNonNull(skinMap);
    this.context = context;
    this.game = game;
    this.skinMap = skinMap;
    ScreenInfo screenInfo = context.getScreenInfo();
    this.windowWidth = (int) screenInfo.getWidth();
    this.windowHeight = (int) screenInfo.getHeight();
    this.inventoryTopX = windowWidth / 2 - inventoryWidth / 2;
    this.inventoryTopY = windowHeight / 2 - inventoryHeight / 2;
  }
  
  //------- Main function -------
  
  public void drawGame(boolean isInventoryShow, Position cursor, boolean useItem) {
    context.renderFrame(graphics -> {
      clearWindow(graphics);
      drawMap(graphics);
      drawPlayer(graphics, useItem);
      drawMobs(graphics);
      drawDroppedItems(graphics);
      if (isInventoryShow) {
        drawInventory(graphics, cursor);
      }
    });
  }
  
  /**
   * Clear the window
   * 
   * @param graphics
   */
  private void clearWindow(Graphics2D graphics) {
    graphics.setColor(Color.BLACK);
    graphics.fill(new Rectangle2D.Float(0, 0, windowWidth, windowHeight));
  }
  
  //------- Draw images -------
  
  private void drawImage(Graphics2D graphics, String skin, int x, int y) {
    graphics.drawImage(skinMap.get(skin), x, y, null);
  }
  
  private void drawImage(Graphics2D graphics, String skin, double x, double y) {
    drawImage(graphics, skin, (int)x, (int)y);
  }

  private void drawImage(Graphics2D graphics, String skin, Position pos) {
    drawImage(graphics, skin, pos.x()*IMAGESIZE, pos.y()*IMAGESIZE);
  }
  
  private void drawImage(Graphics2D graphics, String skin, Position pos, double factor) {
    drawImage(graphics, skin, pos.x()*IMAGESIZE*factor, pos.y()*IMAGESIZE*factor);
  }
  
  //------- Mob related -------
  
  /**
   * draw all mobs
   * 
   * @param graphics
   */
  private void drawMobs(Graphics2D graphics) {
    game.mobs().forEach(mob -> {
      drawImage(graphics, mob.skin(), mob.pos());
      drawHealthBar(graphics, mob);
    });
  }
  
  /**
   * draw health bar's Mobs
   * 
   * @param graphics
   * @param mob
   */
  private void drawHealthBar(Graphics2D graphics, Mob mob) {
    graphics.setColor(Color.RED);
    Rectangle2D.Double rectMaxHealth = new Rectangle2D.Double(mob.pos().x()*IMAGESIZE + 4, mob.pos().y()*IMAGESIZE + 1, 16, 4);
    graphics.fill(rectMaxHealth);
    
    double healthRatio = mob.health() / ((double)mob.maxHealth());
    graphics.setColor(Color.GREEN);
    Rectangle2D.Double rectCurrentHealth = new Rectangle2D.Double(mob.pos().x()*IMAGESIZE + 4, mob.pos().y()*IMAGESIZE + 1, 16 * healthRatio, 4);
    graphics.fill(rectCurrentHealth);
  }

  private void drawHeldItem(Graphics2D graphics) {
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
  
  private void drawPlayer(Graphics2D graphics, boolean useItem) {
    drawImage(graphics, game.player().skin(), game.player().pos());
    drawHealthBar(graphics, game.player());
    if (!useItem) {
      drawHeldItem(graphics);
    } else {
      Position pos = game.player().pos();
      Direction facing = game.player().facing();
      double angle = facingToAngle(facing);
      
      AffineTransform saveAT = graphics.getTransform();

      graphics.rotate(degToRad(angle), (pos.x() + 0.5)*IMAGESIZE, (pos.y() + 0.5)*IMAGESIZE);
      drawHeldItem(graphics);
      graphics.setTransform(saveAT);
    }
  }
  
  //------- Environment related -------
 
  private void drawMap(Graphics2D graphics) {
    for (var line: game.field()) {
      for (Environnement env: line) {
        if (env != null) {
          drawEnvironnement(graphics, env);            
        }
      }
    }
  }
  
  private void drawEnvironnement(Graphics2D graphics, Environnement env) {
    drawImage(graphics, env.skin(), env.pos());
  }

  //------- Item related -------
  
  private void drawDroppedItems(Graphics2D graphics) {
    game.items().forEach(item -> drawImage(graphics, item.skin(), item.pos()));
  }
  
  //------- Inventory related -------
  
  private void drawInventory(Graphics2D graphics, Position cursor) {
    drawInventoryLayout(graphics);
    drawInventoryCursor(graphics, cursor);

    Item item;
    
    AffineTransform saveAT = graphics.getTransform();
    graphics.scale(2, 2);
    for (int y = 0; y < Inventory.NB_ROWS; y++) {
      for (int x = 0; x < Inventory.NB_COLS; x++) {
        if ((item = game.inventory().get(x, y)) != null) {
          drawImage(graphics, item.skin(), inventoryTopX / 2 + x*IMAGESIZE*1.5 + IMAGESIZE/3, inventoryTopY / 2 + y*IMAGESIZE);
        }
      }
    }
    graphics.setTransform(saveAT);
  }
  
  private void drawInventoryLayout(Graphics2D graphics) {
    // inventory shape
    graphics.setColor(Color.LIGHT_GRAY);
    Rectangle2D.Double inv = new Rectangle2D.Double(inventoryTopX, inventoryTopY, inventoryWidth, inventoryHeight);
    graphics.fill(inv);
    
    // horizontal lines
    graphics.setColor(Color.WHITE);
    for (int y = 0; y < Inventory.NB_ROWS + 1; y++) {
      graphics.drawLine(inventoryTopX, inventoryTopY + y*IMAGESIZE*2, inventoryTopX + inventoryWidth, inventoryTopY + y*IMAGESIZE*2);
    }
    
    // vertical lines
    for (int x = 0; x < Inventory.NB_COLS + 1; x++) {
      graphics.drawLine(inventoryTopX + x*IMAGESIZE*3, inventoryTopY, inventoryTopX + x*IMAGESIZE*3, inventoryTopY + inventoryHeight);
    }
  }
  
  private void drawInventoryCursor(Graphics2D graphics, Position cursor) {
    int x = (int)cursor.x();
    int y = (int)cursor.y();
    graphics.setColor(Color.BLACK);
    graphics.drawLine(inventoryTopX + x*IMAGESIZE*3,
                      inventoryTopY + y*IMAGESIZE*2,
                      inventoryTopX + (x + 1)*IMAGESIZE*3,
                      inventoryTopY + y*IMAGESIZE*2);
    graphics.drawLine(inventoryTopX + x*IMAGESIZE*3,
                      inventoryTopY + (y + 1)*IMAGESIZE*2,
                      inventoryTopX + (x + 1)*IMAGESIZE*3,
                      inventoryTopY + (y + 1)*IMAGESIZE*2);
    graphics.drawLine(inventoryTopX + x*IMAGESIZE*3,
                      inventoryTopY + y*IMAGESIZE*2,
                      inventoryTopX + x*IMAGESIZE*3,
                      inventoryTopY + (y + 1)*IMAGESIZE*2);
    graphics.drawLine(inventoryTopX + (x + 1)*IMAGESIZE*3,
                      inventoryTopY + y*IMAGESIZE*2,
                      inventoryTopX + (x + 1)*IMAGESIZE*3,
                      inventoryTopY + (y + 1)*IMAGESIZE*2);
  }
  
  private double degToRad(double angle) {
    return angle * Math.PI / 180;
  }
  
  private double facingToAngle(Direction facing) {
    return switch (facing) {
    case Direction.WEST -> 180;
    case Direction.EAST -> 0;
    case Direction.NORTH -> -90;
    case Direction.SOUTH -> 90;
    default -> 0;
    };
  }
  
}
