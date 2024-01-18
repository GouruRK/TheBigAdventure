package fr.uge.thebigadventure.graphic.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.Objects;

import fr.uge.thebigadventure.game.entity.mob.Mob;

/**
 * Main object to draw mobs and their name
 * This class only contains static methods
 */
public class DrawMobs {
  
  /**
   * Draw all mobs
   * @param graphics
   * @param mobs
   */
  public static void drawMobs(Graphics2D graphics, List<Mob> mobs) {
    Objects.requireNonNull(graphics);
    Objects.requireNonNull(mobs);
    
    mobs.forEach(mob -> drawMob(graphics, mob));
  }
  
  /**
   * Draw one mob
   * @param graphics
   * @param mob
   */
  public static void drawMob(Graphics2D graphics, Mob mob) {
    Objects.requireNonNull(graphics);
    Objects.requireNonNull(mob);
    
    Draw.drawImage(graphics, mob.skin(), mob.pos());
    drawHealthBar(graphics, mob);
    drawMobName(graphics, mob);
  }
  
  /**
   * Draw a mob health bar
   * 
   * @param graphics
   * @param mob
   */
  private static void drawHealthBar(Graphics2D graphics, Mob mob) {
    int size = Draw.IMAGESIZE;
    
    graphics.setColor(Color.RED);
    Rectangle2D.Double rectMaxHealth = new Rectangle2D.Double(mob.pos().x()*size + 4, mob.pos().y()*size + 1, 16, 4);
    graphics.fill(rectMaxHealth);
    
    double healthRatio = mob.health() / ((double)mob.maxHealth());
    graphics.setColor(Color.GREEN);
    Rectangle2D.Double rectCurrentHealth = new Rectangle2D.Double(mob.pos().x()*size + 4, mob.pos().y()*size + 1, 16 * healthRatio, 4);
    graphics.fill(rectCurrentHealth);
  }
  
  /**
   * Draw a mob name
   * @param graphics
   * @param mob
   */
  private static void drawMobName(Graphics2D graphics, Mob mob) {
    if (mob.hasName()) {
      graphics.setColor(Color.WHITE);
      graphics.drawString(mob.name(), (int)(mob.pos().x()*Draw.IMAGESIZE) - 3, (int)(mob.pos().y()*Draw.IMAGESIZE) + Draw.IMAGESIZE+ 5);
    }
  }
  
}
