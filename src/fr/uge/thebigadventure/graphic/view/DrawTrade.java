package fr.uge.thebigadventure.graphic.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.Objects;

import fr.uge.thebigadventure.game.entity.item.Item;
import fr.uge.thebigadventure.graphic.controller.TradeController;

/**
 * Display trade interfaces and the other used interfaces 
 * such as Text and Inventory
 */
public class DrawTrade {

  // ------- Fields -------
  
  /**
   * Trade controller to get visual cursor position, intels on trades, ...
   */
  private final TradeController controller;
  
  /**
   * Class to draw associated inventory
   */
  private final DrawInventory inventory;
  
  /**
   * Class to draw associated text 
   */
  private final DrawText text;
  
  /**
   * Window X margin
   */
  private final int tradeTopX;
  
  /**
   * Window Y margin
   */
  private final int tradeTopY;
  
  /**
   * Inventory X margin
   */
  private final int inventoryTopX;
  
  /**
   * Inventory Y margin
   */
  private final int inventoryTopY;
  
  /**
   * Trade interface width
   */
  private final int tradeWidth = Draw.IMAGESIZE*6;
  
  /**
   * Trade interface height
   */
  private int tradeHeight;
  
  /**
   * Number of trade to display at the same time
   */
  private int minSize;
  
  // ------- Constructor -------
  
  public DrawTrade(TradeController controller, DrawInventory inventory, DrawText text, int windowWidth, int windowHeight) {
    Objects.requireNonNull(controller);
    Objects.requireNonNull(inventory);
    Objects.requireNonNull(text);
    
    this.controller = controller;
    this.inventory = inventory;
    this.text = text;
    
    tradeTopX = windowWidth/4;
    tradeTopY = windowHeight/4;
    
    inventoryTopX = windowWidth * 3/4;
    inventoryTopY = windowHeight/4;
  }
  
  /**
   * Draw trade interfaces and the other used one
   * @param graphics current graphic context
   */
  public void drawTrade(Graphics2D graphics) {
    Objects.requireNonNull(graphics);
    inventory.drawInventoryWithoutCursor(graphics, inventoryTopX, inventoryTopY);
    
    this.minSize = Math.min(controller.totalLength(), 10);
    this.tradeHeight = Draw.IMAGESIZE*(minSize)*2;
    
    if (controller.hasTrade()) {
      drawTradeLayout(graphics);
      drawCursor(graphics);
      drawTradeItems(graphics);
      drawTradeItemNames(graphics);      
    }
    
    if (controller.hasText()) {
      text.drawText(graphics, tradeTopX, tradeTopY);
    }
  }
  
  /**
   * Draw trade interface layout, which consist of a background and and 
   * lines to create separation between trades
   * @param graphics current graphic context
   */
  private void drawTradeLayout(Graphics2D graphics) {
    graphics.setColor(Color.LIGHT_GRAY);
    Rectangle2D.Double inv = new Rectangle2D.Double(tradeTopX, tradeTopY, tradeWidth, tradeHeight);
    graphics.fill(inv);
    
    graphics.setColor(Color.WHITE);
    for (int y = 0; y < tradeHeight; y += Draw.IMAGESIZE*2) {
      graphics.drawLine(tradeTopX, tradeTopY + y, tradeTopX + tradeWidth, tradeTopY + y);
    }
  }
  
  /**
   * Draw the visual cursor on the trade interface
   * @param graphics current graphic context
   */
  private void drawCursor(Graphics2D graphics) {
    int cursor = controller.visualCursor();
    
    graphics.setColor(Color.BLACK);
    graphics.drawLine(tradeTopX, tradeTopY + cursor*Draw.IMAGESIZE*2, tradeTopX + tradeWidth, tradeTopY + cursor*Draw.IMAGESIZE*2);
    graphics.drawLine(tradeTopX, tradeTopY + (cursor + 1)*Draw.IMAGESIZE*2, tradeTopX + tradeWidth, tradeTopY + (cursor + 1)*Draw.IMAGESIZE*2);
    
    graphics.setColor(new Color(255, 0, 0, 100));
    Rectangle2D.Double cur = new Rectangle2D.Double(tradeTopX, tradeTopY + cursor*Draw.IMAGESIZE*2, tradeWidth, Draw.IMAGESIZE*2);
    graphics.fill(cur);
  }
  
  /**
   * Draw items used on the trade
   * @param graphics current graphic context
   */
  private void drawTradeItems(Graphics2D graphics) {
    int y = 0, index = 0;
    
    AffineTransform save = graphics.getTransform();
    graphics.scale(2, 2);
    
    for (var entry: controller.trade().entrySet()) {
      for (Item item: entry.getValue()) {
        if (index < controller.minIndex() || controller.maxIndex() <= index) {
          index++;
          continue;
        }
        drawTradeItem(graphics, entry.getKey().skin(), item.skin(), y);
        y++;
        index++;
      }
    }
    graphics.setTransform(save);
  }
  
  /**
   * Draw items names on the trade interface
   * @param graphics current graphic context
   */
  private void drawTradeItemNames(Graphics2D graphics) {
    int y = 0, index = 0;
    for (var entry: controller.trade().entrySet()) {
      if (entry.getKey().hasName()) {
        Draw.drawText(graphics, entry.getKey().name(),
            tradeTopX + Draw.IMAGESIZE,
            tradeTopY + y*Draw.IMAGESIZE*2 + Draw.IMAGESIZE*2,
            Color.BLACK);
      }
      
      for (Item item: entry.getValue()) {
        if (index < controller.minIndex() || controller.maxIndex() <= index) {
          index++;
          continue;
        }
        if (item.hasName()) {
          Draw.drawText(graphics, item.name(),
              tradeTopX + Draw.IMAGESIZE*4,
              tradeTopY + y*Draw.IMAGESIZE*2 + Draw.IMAGESIZE*2,
              Color.BLACK);
        }
        y++;
        index++;
      }
    }
  }
  
  /**
   * Draw trade row with the item required to trade and the selling one
   * @param graphics current graphic context
   * @param wanted wanted item to trade
   * @param toSell item the player's getting if he trades
   * @param y ordinate on the trade interface to draw the row
   */
  private void drawTradeItem(Graphics2D graphics, String wanted, String toSell, int y) {
    y = tradeTopY/2 + y*Draw.IMAGESIZE;
    Draw.drawImage(graphics, wanted, tradeTopX/2,  y);
    Draw.drawImage(graphics, "arrow", tradeTopX/2 + Draw.IMAGESIZE, y);
    Draw.drawImage(graphics, toSell, tradeTopX/2 + Draw.IMAGESIZE*2, y);
  }
 
}
