package fr.uge.thebigadventure.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.Objects;

import fr.uge.thebigadventure.controller.TextController;

/**
 * Object to display text interface
 */
public class DrawText {

  // ------- Fields -------
  
  /**
   * Text controller to get text, page index, lines
   */
  private final TextController text;
  
  // ------- Constructor -------
  
  public DrawText(TextController text) {
    Objects.requireNonNull(text);
    this.text = text;
  }
  
  // ------- Getter -------
  
  /**
   * Get text controller
   * @return
   */
  public TextController text() {
    return text;
  }
  
  /**
   * Get longest on screen text line width 
   * @param graphics
   * @return
   */
  public int textWidth(Graphics2D graphics) {
    Objects.requireNonNull(graphics);
    return graphics.getFontMetrics().stringWidth(text.text().longestLine());
  }
  
  /**
   * Draw text interface at a margin of the top left corner
   * @param graphics current graphic context
   * @param xOffset
   * @param yOffset
   */
  public void drawText(Graphics2D graphics, int xOffset, int yOffset) {
    Objects.requireNonNull(graphics);
    
    AffineTransform save = graphics.getTransform();
    graphics.scale(1.5, 1.5);
    
    drawTextOutline(graphics, xOffset, yOffset);
    drawTextContent(graphics, xOffset, yOffset);
    
    graphics.setTransform(save);
  }
  
  /**
   * Draw text outline which consist in its background 
   * @param graphics current graphic context
   * @param xOffset
   * @param yOffset
   */
  private void drawTextOutline(Graphics2D graphics, int xOffset, int yOffset) {
    int height = 15*text.getLinePerPage(); 
    
    if (text.numberOfPages() != 0) {
      height += 20;
    }
    
    graphics.setColor(Color.LIGHT_GRAY);
    Rectangle2D.Double txt = new Rectangle2D.Double(xOffset, yOffset - 10, textWidth(graphics), height);
    graphics.fill(txt);
  }
  
  /**
   * Draw lines of text
   * @param graphics
   * @param xOffset
   * @param yOffset
   */
  private void drawTextContent(Graphics2D graphics, int xOffset, int yOffset) {
    int index = text.minLineIndex();
    int delta = text.maxLineIndex() - index;
    graphics.setColor(Color.BLACK);
    for (int y = 0; y < delta; y++, index++) {
      Draw.drawText(graphics, text.text().get(index), xOffset, yOffset + y*15);
    }
    
    if (text.numberOfPages() != 0) {
      String pages = "Page " + (text.page() + 1) + " / " + (text.numberOfPages() + 1);
      int x = xOffset + textWidth(graphics)/2 - graphics.getFontMetrics().stringWidth(pages)/2;
      
      Draw.drawText(graphics, pages, x, yOffset + 15*text.getLinePerPage() + 5);
    }
    
  }
  
}
