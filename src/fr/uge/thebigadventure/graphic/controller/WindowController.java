package fr.uge.thebigadventure.graphic.controller;

import java.util.Objects;

import fr.uge.thebigadventure.util.Direction;
import fr.uge.thebigadventure.util.Position;
import fr.uge.thebigadventure.util.Zone;

public class WindowController {

  private static final int maxWidth = 40;
  private static final int maxHeight = maxWidth/2;
  
  private final Position corner;
  private final Zone game;
  private int windowWidth;
  private int windowHeight;
  
  public WindowController(Zone game, Position player) {
    Objects.requireNonNull(game);
    this.game = game;
    this.corner = new Position(Math.max(0, player.x() - maxWidth/2), Math.max(0, player.y() - maxHeight/2));
  }
  
  public void setWindowDimensions(int windowWidth, int windowHeight) {
    this.windowWidth = windowWidth;
    this.windowHeight = windowHeight;
  }
  
  public double scaleX() {
    return windowWidth / maxWidth;
  }
  
  public double scaleY() {
    return windowHeight / maxHeight;
  }
  
  public int xOffset() {
    return (int)corner.x();
  }
  
  public int yOffset() {
    return (int)corner.y();
  }
  
  public void moveWindow(Direction dir, Position player) {
    Objects.requireNonNull(dir);
    
    if (dir == Direction.EAST || dir == Direction.WEST) {
      if (player.x() < maxWidth/2 || player.x() > game.bottomRight().x() - maxWidth/2) {
        return;
      }
    } else if (dir == Direction.NORTH || dir == Direction.SOUTH) {
      if (player.y() < maxHeight/2 || player.y() > game.bottomRight().y() - maxHeight/2) {
        return;
      }
    }
    
    if (dir == Direction.NORTH) {
      if (corner.y() == 0) {
        return;
      }
      corner.addLocalY(-1);        
    } else if (dir == Direction.SOUTH) {
      if (corner.y() + maxHeight == game.bottomRight().y()) {
        return;
      }
      corner.addLocalY(1);          
    } else if (dir == Direction.WEST) {
      if (corner.x() == 0) {
        return;
      }
      corner.addLocalX(-1);        
    } else if (dir == Direction.EAST) {
      if (corner.x() + maxWidth == game.bottomRight().x()) {
        return;
      }
      corner.addLocalX(1);
    }
  }
}
