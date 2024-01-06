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
import game.entity.item.Item;
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
  private final DrawTrade trade;
  private final DrawText text;
  private final int windowWidth;
  private final int windowHeight;
  
  //------- Constructor -------
  
  public Draw(ApplicationContext context, Game game, GeneralController controller, Map<String, BufferedImage> skinMap) {
    // Basic checks
    Objects.requireNonNull(context);
    Objects.requireNonNull(game);
    Objects.requireNonNull(controller);
    Objects.requireNonNull(skinMap);
    // Fields assignation
    this.context = context;
    this.game = game;
    this.controller = controller;
    ScreenInfo screenInfo = context.getScreenInfo();
    this.windowWidth = (int) screenInfo.getWidth();
    this.windowHeight = (int) screenInfo.getHeight();
    // Creation of submodules to draw
    this.inventory = new DrawInventory(game.inventory(), controller.inventoryController());
    this.player = new DrawPlayer(game.player());
    this.text = new DrawText(controller.textController());
    this.trade = new DrawTrade(controller.tradeController(), inventory, text, windowWidth, windowHeight);
  }
  
  //------- Main function -------
  
  public void drawGame() {
    context.renderFrame(graphics -> {
      clearWindow(graphics);
      drawMap(graphics);
      player.drawPlayer(graphics, controller.hasItemBeenUsed());
      DrawMobs.drawMobs(graphics, game.mobs());
      drawDroppedItems(graphics);
      if (controller.tradeController().isTradeInterfaceShow()) {
        trade.drawTrade(graphics);
      } else if (controller.inventoryController().isInventoryDisplay()) {
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
  
  // ------- Draw images -------
  
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
  
  // ------- Draw images -------
  
  
  public static void drawItem(Graphics2D graphics, Item item, int x, int y) {
    drawImage(graphics, item.skin(), x, y);
    if (item.hasName()) {
      drawText(graphics, item.name(), x - item.name().length() / 2, y + View.IMAGESIZE);
    }
  }
  
  public static void drawItem(Graphics2D graphics, Item item, int x, int y, Color color) {
    drawImage(graphics, item.skin(), x, y);
    if (item.hasName()) {
      drawText(graphics, item.name(), x - item.name().length() / 2, y + View.IMAGESIZE, color);
    }
  }
  
  public static void drawItem(Graphics2D graphics, Item item, double x, double y) {
    drawItem(graphics, item, (int)x, (int)y);
  }
  
  public static void drawItem(Graphics2D graphics, Item item, double x, double y, Color color) {
    drawItem(graphics, item, (int)x, (int)y, color);
  }
  
  // ------- Draw Text -------
  
  public static void drawText(Graphics2D graphics, String text, int x, int y) {
    graphics.drawString(text, x, y);
  }
  
  public static void drawText(Graphics2D graphics, String text, double x, double y) {
    graphics.drawString(text, (int)x, (int)y);
  }
  
  public static void drawCenteredText(Graphics2D graphics, String text, int x, int y) {
    graphics.drawString(text, x - text.length()/2, y);
  }
  
  public static void drawCenteredText(Graphics2D graphics, String text, double x, double y) {
    drawCenteredText(graphics, text, (int)x, (int)y);
  }
  
  public static void drawText(Graphics2D graphics, String text, int x, int y, Color color) {
    graphics.setColor(color);
    graphics.drawString(text, x, y);
  }
  
  public static void drawCenteredText(Graphics2D graphics, String text, int x, int y, Color color) {
    graphics.setColor(color);
    drawCenteredText(graphics, text, x, y);
  }
  
  public static void drawText(Graphics2D graphics, String text, double x, double y, Color color) {
    graphics.setColor(color);
    graphics.drawString(text, (int)x, (int)y);
  }
  
  public static void drawCenteredText(Graphics2D graphics, String text, double x, double y, Color color) {
    graphics.setColor(color);
    drawCenteredText(graphics, text, (int)x, (int)y);
  }
  
  // ------- Environment related -------
 
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
