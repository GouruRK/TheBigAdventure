package graphic.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Objects;

import graphic.controller.TradeController;

public class DrawTrade {

  private final TradeController controller;
  private final DrawInventory inventory;
  private final int tradeTopX;
  private final int tradeTopY;
  private final int inventoryTopX;
  private final int inventoryTopY;
  private final int tradeWidth;
  private final int tradeHeight;
  
  private static final int consecutiveTradeLength = 10;
  
  public DrawTrade(TradeController controller, DrawInventory inventory, int windowWidth, int windowHeight) {
    Objects.requireNonNull(controller);
    Objects.requireNonNull(inventory);
    
    this.controller = controller;
    this.inventory = inventory;
    
    tradeTopX = windowWidth/4;
    tradeTopY = windowHeight/4;
    
    tradeWidth = View.IMAGESIZE*5;
    tradeHeight = View.IMAGESIZE*consecutiveTradeLength;
    // tradeHeight = View.IMAGESIZE*(controller.trade().size() % consecutiveTradeLength);
    
    inventoryTopX = windowWidth * 3/4;
    inventoryTopY = windowHeight/4;
  }
  
  
  public void drawTrade(Graphics2D graphics) {
    inventory.drawInventory(graphics, inventoryTopY, inventoryTopX);
    drawTradeLayout(graphics);
  }
  
  private void drawTradeLayout(Graphics2D graphics) {
    graphics.setColor(Color.LIGHT_GRAY);
    Rectangle2D.Double inv = new Rectangle2D.Double(tradeTopX, tradeTopY, tradeWidth, tradeHeight);
    graphics.fill(inv);
    
    graphics.setColor(Color.WHITE);
    for (int y = 0; y < tradeHeight; y += View.IMAGESIZE) {
      graphics.drawLine(tradeTopX, tradeTopY + y, tradeTopX + tradeWidth, tradeTopY + y);
    }
    
  }
  
}
