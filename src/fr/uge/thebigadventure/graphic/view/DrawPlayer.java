package fr.uge.thebigadventure.graphic.view;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Objects;

import fr.uge.thebigadventure.game.entity.item.GameItems;
import fr.uge.thebigadventure.game.entity.mob.Player;
import fr.uge.thebigadventure.util.Direction;
import fr.uge.thebigadventure.util.Position;
import fr.uge.thebigadventure.util.Utils;

/**
 * Object to draw player and its held item
 */
public class DrawPlayer {

  // ------- Fields -------
  
  /**
   * Player to draw
   */
  private final Player player;
  
  //------- Constructor -------
  
  public DrawPlayer(Player player) {
    Objects.requireNonNull(player);
    this.player = player;
  }
  
  //------- Methods -------
  
  /**
   * Main method to draw the player
   * @param graphics
   * @param hasItemBeenUsed
   */
  public void drawPlayer(Graphics2D graphics, boolean hasItemBeenUsed) {
    Objects.requireNonNull(graphics);
    
    DrawMobs.drawMob(graphics, player);
    
    if (!hasItemBeenUsed) {
      drawHeldItem(graphics);
    } else {
      Position pos = player.pos();
      Direction facing = player.facing();
      double angle = facingToAngle(facing);
      
      AffineTransform saveAT = graphics.getTransform();

      graphics.rotate(Utils.degToRad(angle), (pos.x() + 0.5)*Draw.IMAGESIZE, (pos.y() + 0.5)*Draw.IMAGESIZE);
      drawHeldItem(graphics, hasItemBeenUsed);
      graphics.setTransform(saveAT);
    }
  }
  
  /**
   * Draw held item that hasn't been used
   * @param graphics
   */
  private void drawHeldItem(Graphics2D graphics) {
    drawHeldItem(graphics, false);
  }
  
  /**
   * Draw held item. If hasItemBeenUsed is true, display it at a different angle that 
   * the player is looking at 
   * @param graphics graphic context
   * @param hasItemBeenUsed tells if the held item has been used
   */
  private void drawHeldItem(Graphics2D graphics, boolean hasItemBeenUsed) {
    if (player.hold() == null) {
      return;
    }
    AffineTransform saveAT = graphics.getTransform();
    
    Position pos = player.pos().addY(0.3).addX(0.8);
    graphics.scale(0.8, 0.8);
    Draw.drawImage(graphics, player.hold().skin(), pos, 1.25); // 1.25 because 0.8*1.25 = 1
    
    if (player.hold().getItem() == GameItems.BOLT && hasItemBeenUsed) {
      Draw.drawImage(graphics, player.hold().skin(), pos.addX(1), 1.25);
      Draw.drawImage(graphics, player.hold().skin(), pos.addX(2), 1.25);
    }
    graphics.setTransform(saveAT);
  }
  
  /**
   * Get angle its facing
   * @param facing
   * @return
   */
  private double facingToAngle(Direction facing) {
    return switch (facing) {
    case Direction.WEST -> 180;
    case Direction.EAST -> 0;
    case Direction.NORTH -> -90;
    case Direction.SOUTH -> 90;
    default -> 0;
    };
  }
}
