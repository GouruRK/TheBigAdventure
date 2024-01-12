package graphic.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.Objects;

import game.entity.item.Item;
import graphic.controller.TradeController;

public class DrawTrade {

  private final TradeController controller;
  private final DrawInventory inventory;
  private final DrawText text;
  private final int tradeTopX;
  private final int inventoryTopX;
  private final int inventoryTopY;
  private final int tradeWidth = Draw.IMAGESIZE*6;
  private final int tradeTopY;
  private int tradeHeight;
  private int minSize;
  
  public DrawTrade(TradeController controller, DrawInventory inventory, DrawText text, int windowWidth, int windowHeight) {
    Objects.requireNonNull(controller);
    Objects.requireNonNull(inventory);
    Objects.requireNonNull(text);
    
    this.controller = controller;
    this.inventory = inventory;
    this.text = text;
    
    tradeTopX = windowWidth/4;
    tradeTopY = windowHeight/4;
    
    // tradeHeight = View.IMAGESIZE*consecutiveTradeLength;
    // tradeHeight = View.IMAGESIZE*(controller.trade().size() % consecutiveTradeLength);
    
    inventoryTopX = windowWidth * 3/4;
    inventoryTopY = windowHeight/4;
  }
  
  
  public void drawTrade(Graphics2D graphics) {
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
  
  private void drawTradeLayout(Graphics2D graphics) {
    graphics.setColor(Color.LIGHT_GRAY);
    Rectangle2D.Double inv = new Rectangle2D.Double(tradeTopX, tradeTopY, tradeWidth, tradeHeight);
    graphics.fill(inv);
    
    graphics.setColor(Color.WHITE);
    for (int y = 0; y < tradeHeight; y += Draw.IMAGESIZE*2) {
      graphics.drawLine(tradeTopX, tradeTopY + y, tradeTopX + tradeWidth, tradeTopY + y);
    }
  }
  
  private void drawCursor(Graphics2D graphics) {
    int cursor = controller.visualCursor();
    
    graphics.setColor(Color.BLACK);
    graphics.drawLine(tradeTopX, tradeTopY + cursor*Draw.IMAGESIZE*2, tradeTopX + tradeWidth, tradeTopY + cursor*Draw.IMAGESIZE*2);
    graphics.drawLine(tradeTopX, tradeTopY + (cursor + 1)*Draw.IMAGESIZE*2, tradeTopX + tradeWidth, tradeTopY + (cursor + 1)*Draw.IMAGESIZE*2);
    
    graphics.setColor(new Color(255, 0, 0, 100));
    Rectangle2D.Double cur = new Rectangle2D.Double(tradeTopX, tradeTopY + cursor*Draw.IMAGESIZE*2, tradeWidth, Draw.IMAGESIZE*2);
    graphics.fill(cur);
  }
  
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
  
  private void drawTradeItem(Graphics2D graphics, String wanted, String toSell, int y) {
    y = tradeTopY/2 + y*Draw.IMAGESIZE;
    Draw.drawImage(graphics, wanted, tradeTopX/2,  y);
    Draw.drawImage(graphics, "arrow", tradeTopX/2 + Draw.IMAGESIZE, y);
    Draw.drawImage(graphics, toSell, tradeTopX/2 + Draw.IMAGESIZE*2, y);
  }
 
}
