package fr.uge.thebigadventure.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.Objects;

import fr.uge.thebigadventure.controller.GeneralController;
import fr.uge.thebigadventure.game.Game;
import fr.uge.thebigadventure.game.entity.item.Item;
import fr.uge.thebigadventure.game.environment.Environment;
import fr.uge.thebigadventure.util.Position;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.ScreenInfo;

/**
 * Main class to interact with the window context
 * Provide basic toolbase methods to draw things
 */
public class Draw {
  
  /**
   * Dimensions of images used
   */
  public static final int IMAGESIZE = 24;
  
  //------- Fields -------
  
  /**
   * On screen application context
   */
  private final ApplicationContext context;
  
  /**
   * Game to draw
   */
  private final Game game;
  
  /**
   * Controller to handle player's interaction
   */
  private final GeneralController controller;
  
  /**
   * Display of the inventory interface
   */
  private final DrawInventory inventory;
  
  /**
   * Display the player
   */
  private final DrawPlayer player;
  
  /**
   * Display of the trade interface
   */
  private final DrawTrade trade;
  
  /**
   * Display of the text interface
   */
  private final DrawText text;
  
  /**
   * Window width
   */
  private final int windowWidth;
  
  /**
   * Window height
   */
  private final int windowHeight;
  
  //------- Constructor -------
  
  public Draw(ApplicationContext context, Game game, GeneralController controller) {
    // Basic checks
    Objects.requireNonNull(context);
    Objects.requireNonNull(game);
    Objects.requireNonNull(controller);
    
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
    
    controller.windowController().setWindowDimensions(windowWidth, windowHeight);
  }
  
  //------- Getter -------
  
  public static int getImageSize() {
    return IMAGESIZE;
  }
  
  //------- Main function -------
  
  /**
   * Display game
   */
  public void drawGame() {
    double x = controller.windowController().scaleX();
    double y = controller.windowController().scaleY();
    context.renderFrame(graphics -> {
      clearWindow(graphics);
      
      AffineTransform saveAT = graphics.getTransform();
      // The sliding window works by translating the game with offsets
      graphics.scale(x / IMAGESIZE, y / IMAGESIZE);
      graphics.translate(-controller.windowController().xOffset()*IMAGESIZE, -controller.windowController().yOffset()*IMAGESIZE);
      
      drawMap(graphics);
      player.drawPlayer(graphics, controller.hasItemBeenUsed());
      DrawMobs.drawMobs(graphics, game.mobs());
      drawDroppedItems(graphics);
      
      graphics.setTransform(saveAT);
      
      drawInterfaces(graphics);
    });
  }
  
  /**
   * Display interfaces
   * @param graphics current context
   */
  private void drawInterfaces(Graphics2D graphics) {
    if (controller.tradeController().isTradeInterfaceShow()) {
      trade.drawTrade(graphics);
    } else if (controller.inventoryController().isInventoryInterfaceShow()) {
      inventory.drawInventory(graphics, windowWidth/2, windowHeight/2);
    } else if (controller.textController().isTextInterfaceShow()) {
      text.drawText(graphics, windowWidth/4, windowHeight/4);
    }
  }
  
  /**
   * Clear the window
   * 
   * @param graphics current context
   */
  private void clearWindow(Graphics2D graphics) {
    graphics.setColor(Color.BLACK);
    graphics.fill(new Rectangle2D.Float(0, 0, windowWidth, windowHeight));
  }
  
  // ------- Draw images -------
  
  /**
   * Draw image with integer coordinates
   * @param graphics current context
   * @param skin skin to draw
   * @param x
   * @param y
   */
  public static void drawImage(Graphics2D graphics, String skin, int x, int y) {
    Objects.requireNonNull(graphics);
    Objects.requireNonNull(skin);
    graphics.drawImage(Skins.getSkin(skin), x, y, null);
  }
  
  /**
   * Draw image with double coordinates
   * @param graphics current context
   * @param skin skin to draw
   * @param x
   * @param y
   */
  public static void drawImage(Graphics2D graphics, String skin, double x, double y) {
    Objects.requireNonNull(graphics);
    Objects.requireNonNull(skin);
    drawImage(graphics, skin, (int)x, (int)y);
  }

  /***
   * Draw image at position
   * @param graphics current context
   * @param skin skin to draw
   * @param pos position to draw
   */
  public static void drawImage(Graphics2D graphics, String skin, Position pos) {
    Objects.requireNonNull(graphics);
    Objects.requireNonNull(skin);
    Objects.requireNonNull(pos);
    drawImage(graphics, skin, pos.x()*IMAGESIZE, pos.y()*IMAGESIZE);
  }
  
  /**
   * Draw image at position and apply a factor to the coordinates
   * @param graphics current context
   * @param skin skin to draw
   * @param pos position to draw
   * @param factor factor to apply
   */
  public static void drawImage(Graphics2D graphics, String skin, Position pos, double factor) {
    Objects.requireNonNull(graphics);
    Objects.requireNonNull(skin);
    Objects.requireNonNull(pos);
    drawImage(graphics, skin, pos.x()*IMAGESIZE*factor, pos.y()*IMAGESIZE*factor);
  }
  
  // ------- Draw images -------
  
  /**
   * Draw item at coordiantes
   * @param graphics current context
   * @param item item to display
   * @param x
   * @param y
   */
  public static void drawItem(Graphics2D graphics, Item item, int x, int y) {
    Objects.requireNonNull(graphics);
    Objects.requireNonNull(item);
    drawImage(graphics, item.skin(), x, y);
    if (item.hasName()) {
      drawText(graphics, item.name(), x - item.name().length() / 2, y + IMAGESIZE);
    }
  }
  
  /**
   * Draw item and its name at coordiantes 
   * @param graphics current context
   * @param item item to display
   * @param x
   * @param y
   * @param color item name color
   */
  public static void drawItem(Graphics2D graphics, Item item, int x, int y, Color color) {
    Objects.requireNonNull(graphics);
    Objects.requireNonNull(item);
    Objects.requireNonNull(color);
    drawImage(graphics, item.skin(), x, y);
    if (item.hasName()) {
      drawText(graphics, item.name(), x - item.name().length() / 2, y + IMAGESIZE, color);
    }
  }
  
  /**
   * Draw item at coordinates
   * @param graphics current context
   * @param item item to display
   * @param x
   * @param y
   */
  public static void drawItem(Graphics2D graphics, Item item, double x, double y) {
    Objects.requireNonNull(graphics);
    Objects.requireNonNull(item);
    drawItem(graphics, item, (int)x, (int)y);
  }
  
  /**
   * Draw item and its name at coordinates
   * @param graphics current context
   * @param item item to display
   * @param x
   * @param y
   * @param color item name color
   */
  public static void drawItem(Graphics2D graphics, Item item, double x, double y, Color color) {
    Objects.requireNonNull(graphics);
    Objects.requireNonNull(item);
    Objects.requireNonNull(color);
    drawItem(graphics, item, (int)x, (int)y, color);
  }
  
  // ------- Draw Text -------
  
  /**
   * Draw text at coordinates
   * @param graphics current context
   * @param text text to display
   * @param x
   * @param y
   */
  public static void drawText(Graphics2D graphics, String text, int x, int y) {
    Objects.requireNonNull(graphics);
    Objects.requireNonNull(text);
    graphics.drawString(text, x, y);
  }
  
  /**
   * Draw text at coordinates
   * @param graphics current context
   * @param text text to display
   * @param x
   * @param y
   */
  public static void drawText(Graphics2D graphics, String text, double x, double y) {
    Objects.requireNonNull(graphics);
    Objects.requireNonNull(text);
    graphics.drawString(text, (int)x, (int)y);
  }
  
  /**
   * Draw text centered at coordinates 
   * @param graphics current context
   * @param text text to display
   * @param x
   * @param y
   */
  public static void drawCenteredText(Graphics2D graphics, String text, int x, int y) {
    Objects.requireNonNull(graphics);
    Objects.requireNonNull(text);
    graphics.drawString(text, x - graphics.getFontMetrics().stringWidth(text)/2, y);
  }
  
  /**
   * Draw text centered at coordinates 
   * @param graphics current context
   * @param text text to display
   * @param x
   * @param y
   */
  public static void drawCenteredText(Graphics2D graphics, String text, double x, double y) {
    Objects.requireNonNull(graphics);
    Objects.requireNonNull(text);
    drawCenteredText(graphics, text, (int)x, (int)y);
  }
  
  /**
   * Draw text centered at coordinates with a color
   * @param graphics current context
   * @param text text to display
   * @param x
   * @param y
   * @param color
   */
  public static void drawText(Graphics2D graphics, String text, int x, int y, Color color) {
    Objects.requireNonNull(graphics);
    Objects.requireNonNull(text);
    Objects.requireNonNull(color);
    graphics.setColor(color);
    graphics.drawString(text, x, y);
  }

  /**
   * Draw text centered at coordinates with given color
   * @param graphics current context
   * @param text text to display
   * @param x
   * @param y
   * @param color
   */
  public static void drawCenteredText(Graphics2D graphics, String text, int x, int y, Color color) {
    Objects.requireNonNull(graphics);
    Objects.requireNonNull(text);
    graphics.setColor(color);
    drawCenteredText(graphics, text, x, y);
  }
  
  /**
   * Draw text at coordinates with given color
   * @param graphics current context
   * @param text text to display
   * @param x
   * @param y
   * @param color
   */
  public static void drawText(Graphics2D graphics, String text, double x, double y, Color color) {
    Objects.requireNonNull(graphics);
    Objects.requireNonNull(text);
    Objects.requireNonNull(color);
    graphics.setColor(color);
    graphics.drawString(text, (int)x, (int)y);
  }
  
  /**
   * Draw centered text at coordiantes with given color
   * @param graphics current context
   * @param text text to display
   * @param x
   * @param y
   * @param color
   */
  public static void drawCenteredText(Graphics2D graphics, String text, double x, double y, Color color) {
    Objects.requireNonNull(graphics);
    Objects.requireNonNull(text);
    Objects.requireNonNull(color);
    graphics.setColor(color);
    drawCenteredText(graphics, text, (int)x, (int)y);
  }
  
  // ------- Environment related -------
 
  /**
   * Draw the map composed by obstacles and scenery
   * @param graphics
   */
  private void drawMap(Graphics2D graphics) {
    for (var line: game.field()) {
      for (Environment env: line) {
        if (env != null) {
          drawEnvironment(graphics, env);
        }
      }
    }
  }
  
  /**
   * Draw environnemnt
   * @param graphics
   * @param env
   */
  private void drawEnvironment(Graphics2D graphics, Environment env) {
    drawImage(graphics, env.skin(), env.pos());
  }

  //------- Item related -------
  
  /**
   * Draw a dropped item
   * @param graphics
   */
  private void drawDroppedItems(Graphics2D graphics) {
    game.items().forEach(item -> drawImage(graphics, item.skin(), item.pos()));
  }
  
}
