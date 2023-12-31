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
  private final int tradeTopX;
  private final int inventoryTopX;
  private final int inventoryTopY;
  private final int tradeWidth;
  private final int tradeTopY;
  private int tradeHeight;
  
  private static final int consecutiveTradeLength = 10;
  
  public DrawTrade(TradeController controller, DrawInventory inventory, int windowWidth, int windowHeight) {
    Objects.requireNonNull(controller);
    Objects.requireNonNull(inventory);
    
    this.controller = controller;
    this.inventory = inventory;
    
    tradeTopX = windowWidth/4;
    tradeTopY = windowHeight/4;
    
    tradeWidth = View.IMAGESIZE*6;
    tradeHeight = View.IMAGESIZE*consecutiveTradeLength;
    // tradeHeight = View.IMAGESIZE*(controller.trade().size() % consecutiveTradeLength);
    
    inventoryTopX = windowWidth * 3/4;
    inventoryTopY = windowHeight/4;
  }
  
  
  public void drawTrade(Graphics2D graphics) {
    inventory.drawInventoryWithoutCursor(graphics, inventoryTopX, inventoryTopY);
    
    tradeHeight = View.IMAGESIZE*(controller.totalLength() % consecutiveTradeLength)*2;
    
    drawTradeLayout(graphics);
    drawCursor(graphics);
    drawTradeItems(graphics);
    drawTradeItemNames(graphics);
  }
  
  private void drawTradeLayout(Graphics2D graphics) {
    graphics.setColor(Color.LIGHT_GRAY);
    Rectangle2D.Double inv = new Rectangle2D.Double(tradeTopX, tradeTopY, tradeWidth, tradeHeight);
    graphics.fill(inv);
    
    graphics.setColor(Color.WHITE);
    for (int y = 0; y < tradeHeight; y += View.IMAGESIZE*2) {
      graphics.drawLine(tradeTopX, tradeTopY + y, tradeTopX + tradeWidth, tradeTopY + y);
    }
  }
  
  private void drawCursor(Graphics2D graphics) {
    graphics.setColor(Color.BLACK);
    graphics.drawLine(tradeTopX, tradeTopY + controller.cursor()*View.IMAGESIZE*2, tradeTopX + tradeWidth, tradeTopY + controller.cursor()*View.IMAGESIZE*2);
    graphics.drawLine(tradeTopX, tradeTopY + (controller.cursor() + 1)*View.IMAGESIZE*2, tradeTopX + tradeWidth, tradeTopY + (controller.cursor() + 1)*View.IMAGESIZE*2);
    
    graphics.setColor(new Color(255, 0, 0, 100));
    Rectangle2D.Double cur = new Rectangle2D.Double(tradeTopX, tradeTopY + controller.cursor()*View.IMAGESIZE*2, tradeWidth, View.IMAGESIZE*2);
    graphics.fill(cur);
  }
  
  private void drawTradeItems(Graphics2D graphics) {
    int y = 0;
    
    AffineTransform save = graphics.getTransform();
    graphics.scale(2, 2);
    
    for (var entry: controller.trade().entrySet()) {
      for (Item item: entry.getValue()) {
        drawTradeItem(graphics, entry.getKey().skin(), item.skin(), y);
        y += 1;
      }
    }
    graphics.setTransform(save);
  }
  
  private void drawTradeItemNames(Graphics2D graphics) {
    int y = 0;
    for (var entry: controller.trade().entrySet()) {
      for (Item item: entry.getValue()) {
        if (item.hasName()) {
          Draw.drawText(graphics, item.name(),
              tradeTopX + View.IMAGESIZE*4,
              tradeTopY + y*View.IMAGESIZE + View.IMAGESIZE*2,
              Color.BLACK);
        }
        y += 1;
      }
    }
  }
  
  private void drawTradeItem(Graphics2D graphics, String wanted, String toSell, int y) {
    y = tradeTopY/2 + y*View.IMAGESIZE;
    Draw.drawImage(graphics, wanted, tradeTopX/2,  y);
    Draw.drawImage(graphics, "arrow", tradeTopX/2 + View.IMAGESIZE, y);
    Draw.drawImage(graphics, toSell, tradeTopX/2 + View.IMAGESIZE*2, y);
  }
  
  
  
}
