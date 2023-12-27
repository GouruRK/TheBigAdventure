package graphic.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.Objects;

import game.entity.mob.Mob;

public class DrawMobs {
  /**
   * draw all mobs
   * 
   * @param graphics
   */
  public static void drawMobs(Graphics2D graphics, List<Mob> mobs) {
    Objects.requireNonNull(mobs);
    mobs.forEach(mob -> drawMob(graphics, mob));
  }
  
  public static void drawMob(Graphics2D graphics, Mob mob) {
    Draw.drawImage(graphics, mob.skin(), mob.pos());
    drawHealthBar(graphics, mob);
    drawMobName(graphics, mob);
  }
  
  /**
   * draw health bar's Mobs
   * 
   * @param graphics
   * @param mob
   */
  private static void drawHealthBar(Graphics2D graphics, Mob mob) {
    int size = View.IMAGESIZE;
    
    graphics.setColor(Color.RED);
    Rectangle2D.Double rectMaxHealth = new Rectangle2D.Double(mob.pos().x()*size + 4, mob.pos().y()*size + 1, 16, 4);
    graphics.fill(rectMaxHealth);
    
    double healthRatio = mob.health() / ((double)mob.maxHealth());
    graphics.setColor(Color.GREEN);
    Rectangle2D.Double rectCurrentHealth = new Rectangle2D.Double(mob.pos().x()*size + 4, mob.pos().y()*size + 1, 16 * healthRatio, 4);
    graphics.fill(rectCurrentHealth);
  }
  
  private static void drawMobName(Graphics2D graphics, Mob mob) {
    if (mob.hasName()) {
      graphics.setColor(Color.WHITE);
      graphics.drawString(mob.name(), (int)(mob.pos().x()*View.IMAGESIZE) - 3, (int)(mob.pos().y()*View.IMAGESIZE) + View.IMAGESIZE+ 5);
    }
  }
  
}
