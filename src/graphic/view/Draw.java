package graphic.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Objects;

import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.ScreenInfo;
import game.Game;
import game.environnement.Environnement;
import graphic.controller.GeneralController;
import util.Position;

public class Draw {
  
  //------- Fields -------
  
  private final ApplicationContext context;
  private final Game game;
  private final GeneralController controller;
  private final DrawInventory inventory;
  private final DrawPlayer player;
  private final int windowWidth;
  private final int windowHeight;
  
  //------- Constructor -------
  
  public Draw(ApplicationContext context, Game game, GeneralController controller, Map<String, BufferedImage> skinMap) {
    Objects.requireNonNull(context);
    Objects.requireNonNull(game);
    Objects.requireNonNull(controller);
    Objects.requireNonNull(skinMap);
    this.context = context;
    this.game = game;
    this.controller = controller;
    ScreenInfo screenInfo = context.getScreenInfo();
    this.windowWidth = (int) screenInfo.getWidth();
    this.windowHeight = (int) screenInfo.getHeight();
    this.inventory = new DrawInventory(game.inventory(), controller.inventory());
    this.player = new DrawPlayer(game.player());
  }
  
  //------- Main function -------
  
  public void drawGame() {
    context.renderFrame(graphics -> {
      clearWindow(graphics);
      drawMap(graphics);
      player.drawPlayer(graphics, controller.hasItemBeenUsed());
      DrawMobs.drawMobs(graphics, game.mobs());
      drawDroppedItems(graphics);
      if (controller.inventory().isInventoryDisplay()) {
        inventory.drawInventory(graphics, windowWidth/2, windowHeight/2);
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
  
  public static void drawImage(Graphics2D graphics, String skin, int x, int y) {
    graphics.drawImage(Skins.getSkin(skin), x, y, null);
  }
  
  public static void drawImage(Graphics2D graphics, String skin, double x, double y) {
    drawImage(graphics, skin, (int)x, (int)y);
  }

  public static void drawImage(Graphics2D graphics, String skin, Position pos) {
    drawImage(graphics, skin, pos.x()*View.IMAGESIZE, pos.y()*View.IMAGESIZE);
  }
  
  public static void drawImage(Graphics2D graphics, String skin, Position pos, double factor) {
    drawImage(graphics, skin, pos.x()*View.IMAGESIZE*factor, pos.y()*View.IMAGESIZE*factor);
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
  
}
