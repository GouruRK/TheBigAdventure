package graphic.view;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Objects;

import game.entity.item.GameItems;
import game.entity.mob.Player;
import util.Direction;
import util.Position;

public class DrawPlayer {

  private final Player player;
  
  public DrawPlayer(Player player) {
    Objects.requireNonNull(player);
    this.player = player;
  }
  
  public void drawPlayer(Graphics2D graphics, boolean hasItemBeenUsed) {
    DrawMobs.drawMob(graphics, player);
    
    if (!hasItemBeenUsed) {
      drawHeldItem(graphics);
    } else {
      Position pos = player.pos();
      Direction facing = player.facing();
      double angle = facingToAngle(facing);
      
      AffineTransform saveAT = graphics.getTransform();

      graphics.rotate(degToRad(angle), (pos.x() + 0.5)*Draw.IMAGESIZE, (pos.y() + 0.5)*Draw.IMAGESIZE);
      drawHeldItem(graphics, hasItemBeenUsed);
      graphics.setTransform(saveAT);
    }
  }
  
  private void drawHeldItem(Graphics2D graphics) {
    drawHeldItem(graphics, false);
  }
  
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
  
  private double degToRad(double angle) {
    return angle * Math.PI / 180;
  }
  
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
