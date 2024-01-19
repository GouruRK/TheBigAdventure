package fr.uge.thebigadventure.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.Objects;

import fr.uge.thebigadventure.controller.InventoryController;
import fr.uge.thebigadventure.game.Inventory;
import fr.uge.thebigadventure.game.entity.item.Item;

/**
 * Object to draw the inventory and its content
 */
public class DrawInventory {

  //------- Contants -------
  
  /**
   * Compute inventory width based on the number of columns
   */
  private static final int inventoryWidth = Draw.IMAGESIZE*Inventory.NB_COLS*3;
  
  /**
   * Compute inventory height based on the number of rows
   */
  private static final int inventoryHeight = Draw.IMAGESIZE*Inventory.NB_ROWS*2;
  
  //------- Fields -------
  
  /**
   * Inventory to draw
   */
  private final Inventory inventory;
  
  /**
   * Inventory controler  
   */
  private final InventoryController controller;
   
  //------- Constructor -------
  
  public DrawInventory(Inventory inventory, InventoryController controller) {
    Objects.requireNonNull(inventory);
    Objects.requireNonNull(controller);
    this.inventory = inventory;
    this.controller = controller;
  }
  
  //------- Methods -------
  
  /**
   * Draw inventory at a given margin from the top left corner of the window
   * @param graphics current graphic context
   * @param xMargin
   * @param yMargin
   */
  public void drawInventory(Graphics2D graphics, int xMargin, int yMargin) {
    Objects.requireNonNull(graphics);
    
    xMargin -= inventoryWidth/2;
    yMargin -= inventoryHeight/2;
    
    drawInventoryLayout(graphics, xMargin, yMargin);
    drawInventoryCursor(graphics, xMargin, yMargin);
    drawInventoryItems(graphics, xMargin, yMargin);
    drawInventoryItemNames(graphics, xMargin, yMargin);
  }
  
  /**
   * Draw inventory without cursor at a given margin from the top left corner of the window 
   * @param graphics current graphic context
   * @param xMargin
   * @param yMargin
   */
  public void drawInventoryWithoutCursor(Graphics2D graphics, int xMargin, int yMargin) {
    Objects.requireNonNull(graphics);
    
    xMargin -= inventoryWidth/2;
    yMargin -= inventoryHeight/2;
    
    drawInventoryLayout(graphics, xMargin, yMargin);
    drawInventoryItems(graphics, xMargin, yMargin);
    drawInventoryItemNames(graphics, xMargin, yMargin);
  }
  
  /**
   * Draw inventory layout, composed by its background and verticals and horizontals lines
   * @param graphics current graphic context
   * @param xMargin
   * @param yMargin
   */
  private void drawInventoryLayout(Graphics2D graphics, int xMargin, int yMargin) {
    // inventory shape
    graphics.setColor(Color.LIGHT_GRAY);
    Rectangle2D.Double inv = new Rectangle2D.Double(xMargin, yMargin, inventoryWidth, inventoryHeight);
    graphics.fill(inv);
    
    // horizontal lines
    graphics.setColor(Color.WHITE);
    for (int y = 0; y < Inventory.NB_ROWS + 1; y++) {
      graphics.drawLine(xMargin, yMargin + y*Draw.IMAGESIZE*2, xMargin + inventoryWidth, yMargin + y*Draw.IMAGESIZE*2);
    }
    
    // vertical lines
    for (int x = 0; x < Inventory.NB_COLS + 1; x++) {
      graphics.drawLine(xMargin + x*Draw.IMAGESIZE*3, yMargin, xMargin + x*Draw.IMAGESIZE*3, yMargin + inventoryHeight);
    }
  }
  
  /**
   * Draw the player's cursor on the inventory
   * @param graphics current graphic context
   * @param xMargin
   * @param yMargin
   */
  private void drawInventoryCursor(Graphics2D graphics, int xMargin, int yMargin) {
    int x = (int)controller.cursor().x();
    int y = (int)controller.cursor().y();
    int size = Draw.IMAGESIZE;
    
    graphics.setColor(Color.BLACK);
    graphics.drawLine(xMargin + x*size*3,       yMargin + y*size*2,
                      xMargin + (x + 1)*size*3, yMargin + y*size*2);
    graphics.drawLine(xMargin + x*size*3,       yMargin + (y + 1)*size*2,
                      xMargin + (x + 1)*size*3, yMargin + (y + 1)*size*2);
    graphics.drawLine(xMargin + x*size*3,       yMargin + y*size*2,
                      xMargin + x*size*3,       yMargin + (y + 1)*size*2);
    graphics.drawLine(xMargin + (x + 1)*size*3, yMargin + y*size*2,
                      xMargin + (x + 1)*size*3, yMargin + (y + 1)*size*2);
    
    graphics.setColor(new Color(255, 0, 0, 100));
    Rectangle2D.Double cur = new Rectangle2D.Double(xMargin + x*size*3, yMargin + y*size*2, size*3, size*2);
    graphics.fill(cur);
    
  }
  
  /**
   * Transform function for abscissa when drawing an item on the inventory
   * @param x
   * @return transformed abscissa
   */
  private int xItemTransform(int x) {
    return (int)(x*Draw.IMAGESIZE*1.5 + Draw.IMAGESIZE/3);
  }

  /**
   * Transform function for ordinates when drawing an item on the inventory
   * @param y
   * @return transformed ordinate
   */
  private int yItemTransform(int y) {
    return (int)(y*Draw.IMAGESIZE);
  }
  
  /**
   * Draw items on the inventory
   * @param graphics
   * @param xMargin
   * @param yMargin
   */
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
  
  /**
   * Draw item names
   * @param graphics
   * @param xMargin
   * @param yMargin
   */
  private final void drawInventoryItemNames(Graphics2D graphics, int xMargin, int yMargin) {
    Item item;
    
    for (int y = 0; y < Inventory.NB_ROWS; y++) {
      for (int x = 0; x < Inventory.NB_COLS; x++) {
        if (((item = inventory.get(x, y)) != null) && (item.hasName())) {
          Draw.drawCenteredText(graphics, item.name(),
              (xMargin/2 + xItemTransform(x))*2, 
              (yMargin/2 + yItemTransform(y) + Draw.IMAGESIZE)*2, Color.BLACK);
        }
      }
    }
  }
}
