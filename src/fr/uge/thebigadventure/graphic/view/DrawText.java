package fr.uge.thebigadventure.graphic.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.Objects;

import fr.uge.thebigadventure.graphic.controller.TextController;

public class DrawText {

  private final TextController text;
  
  public DrawText(TextController text) {
    Objects.requireNonNull(text);
    this.text = text;
  }
  
  public TextController text() {
    return text;
  }
  
  public int textWidth(Graphics2D graphics) {
    return graphics.getFontMetrics().stringWidth(text.text().longestLine());
  }
  
  public void drawText(Graphics2D graphics, int xOffset, int yOffset) {
    AffineTransform save = graphics.getTransform();
    graphics.scale(1.5, 1.5);
    
    drawTextOutline(graphics, xOffset, yOffset);
    drawTextContent(graphics, xOffset, yOffset);
    
    graphics.setTransform(save);
  }
  
  private void drawTextOutline(Graphics2D graphics, int xOffset, int yOffset) {
    int height = 15*text.getLinePerPage(); 
    
    if (text.numberOfPages() != 0) {
      height += 20;
    }
    
    graphics.setColor(Color.LIGHT_GRAY);
    Rectangle2D.Double txt = new Rectangle2D.Double(xOffset, yOffset - 10, textWidth(graphics), height);
    graphics.fill(txt);
  }
  
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
