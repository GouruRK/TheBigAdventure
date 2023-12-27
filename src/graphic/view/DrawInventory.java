package graphic.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.Objects;

import game.Inventory;
import game.entity.item.Item;
import graphic.controller.InventoryController;

public class DrawInventory {
  
  private final Inventory inventory;
  private final InventoryController controller;
  
  private static final int inventoryWidth = View.IMAGESIZE*Inventory.NB_COLS*3;
  private static final int inventoryHeight = View.IMAGESIZE*Inventory.NB_ROWS*2;
  
  public DrawInventory(Inventory inventory, InventoryController controller) {
    Objects.requireNonNull(inventory);
    Objects.requireNonNull(controller);
    this.inventory = inventory;
    this.controller = controller;
  }
  
  public void drawInventory(Graphics2D graphics, int xMargin, int yMargin) {
    xMargin -= inventoryWidth/2;
    yMargin -= inventoryHeight/2;
    
    drawInventoryLayout(graphics, xMargin, yMargin);
    drawInventoryCursor(graphics, xMargin, yMargin);
    drawInventoryItems(graphics, xMargin, yMargin);
    drawInventoryItemNames(graphics, xMargin, yMargin);
  }
  
  private void drawInventoryLayout(Graphics2D graphics, int xMargin, int yMargin) {
    // inventory shape
    graphics.setColor(Color.LIGHT_GRAY);
    Rectangle2D.Double inv = new Rectangle2D.Double(xMargin, yMargin, inventoryWidth, inventoryHeight);
    graphics.fill(inv);
    
    // horizontal lines
    graphics.setColor(Color.WHITE);
    for (int y = 0; y < Inventory.NB_ROWS + 1; y++) {
      graphics.drawLine(xMargin, yMargin + y*View.IMAGESIZE*2, xMargin + inventoryWidth, yMargin + y*View.IMAGESIZE*2);
    }
    
    // vertical lines
    for (int x = 0; x < Inventory.NB_COLS + 1; x++) {
      graphics.drawLine(xMargin + x*View.IMAGESIZE*3, yMargin, xMargin + x*View.IMAGESIZE*3, yMargin + inventoryHeight);
    }
  }
  
  private void drawInventoryCursor(Graphics2D graphics, int xMargin, int yMargin) {
    int x = (int)controller.cursor().x();
    int y = (int)controller.cursor().y();
    int size = View.IMAGESIZE;
    
    graphics.setColor(Color.BLACK);
    graphics.drawLine(xMargin + x*size*3,       yMargin + y*size*2,
                      xMargin + (x + 1)*size*3, yMargin + y*size*2);
    graphics.drawLine(xMargin + x*size*3,       yMargin + (y + 1)*size*2,
                      xMargin + (x + 1)*size*3, yMargin + (y + 1)*size*2);
    graphics.drawLine(xMargin + x*size*3,       yMargin + y*size*2,
                      xMargin + x*size*3,       yMargin + (y + 1)*size*2);
    graphics.drawLine(xMargin + (x + 1)*size*3, yMargin + y*size*2,
                      xMargin + (x + 1)*size*3, yMargin + (y + 1)*size*2);
  }
  
  private int xItemTransform(int x) {
    return (int)(x*View.IMAGESIZE*1.5 + View.IMAGESIZE/3);
  }
  
  private int yItemTransform(int y) {
    return (int)(y*View.IMAGESIZE);
  }
  
  private final void drawInventoryItems(Graphics2D graphics, int xMargin, int yMargin) {
    Item item;
    
    AffineTransform saveAT = graphics.getTransform();
    graphics.scale(2, 2);
    for (int y = 0; y < Inventory.NB_ROWS; y++) {
      for (int x = 0; x < Inventory.NB_COLS; x++) {
        if ((item = inventory.get(x, y)) != null) {
          Draw.drawImage(graphics, item.skin(),
              xMargin/2 + xItemTransform(x),
              yMargin/2 + yItemTransform(y));
        }
      }
    }
    graphics.setTransform(saveAT);
  }
  
  private final void drawInventoryItemNames(Graphics2D graphics, int xMargin, int yMargin) {
    Item item;
    
    for (int y = 0; y < Inventory.NB_ROWS; y++) {
      for (int x = 0; x < Inventory.NB_COLS; x++) {
        if (((item = inventory.get(x, y)) != null) && (item.hasName())) {
          graphics.drawString(item.name(),
              (xMargin/2 + xItemTransform(x))*2 - item.name().length() / 2,
              (yMargin/2 + yItemTransform(y) + View.IMAGESIZE)*2);
        }
      }
    }
  }
  
}
