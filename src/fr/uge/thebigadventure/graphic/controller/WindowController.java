package fr.uge.thebigadventure.graphic.controller;

import java.util.Objects;

import fr.uge.thebigadventure.util.Direction;
import fr.uge.thebigadventure.util.Position;
import fr.uge.thebigadventure.util.Zone;

public class WindowController {

  //------- Constants -------
  
  private final int maxWidth = 40;
  private final int maxHeight = maxWidth/2;
  
  //------- Fields -------
  
  /**
   * Top left corner of the slinding window
   */
  private final Position corner;
  
  /**
   * Game dimensions
   */
  private final Zone game;
  
  /**
   * Window width
   */
  private int windowWidth;
  
  /**
   * Window height
   */
  private int windowHeight;
  
  //------- Constructor -------
  
  public WindowController(Zone game, Position player) {
    Objects.requireNonNull(game);
    this.game = game;
    this.corner = new Position(Math.max(0, player.x() - maxWidth/2), Math.max(0, player.y() - maxHeight/2));
  }
  
  //------- Getters -------
  
  /**
   * Get scale factor for abscissa
   * @return
   */
  public double scaleX() {
    return windowWidth / maxWidth;
  }
  
  /**
   * Get scale factor for ordinates
   * @return
   */
  public double scaleY() {
    return windowHeight / maxHeight;
  }

  /**
   * Get abscissa offset (move the window on the x axe)
   * @return
   */
  public int xOffset() {
    return (int)corner.x();
  }
  
  /**
   * Get ordinate offset (move the window on the y axe)
   * @return
   */
  public int yOffset() {
    return (int)corner.y();
  }

  //------- Modifiers -------
  
  /**
   * Change window's onscreen dimensions
   * @param windowWidth
   * @param windowHeight
   */
  public void setWindowDimensions(int windowWidth, int windowHeight) {
    this.windowWidth = windowWidth;
    this.windowHeight = windowHeight;
  }
  
  /**
   * Controller for moving the window
   * @param dir
   * @param player
   */
  public void moveWindow(Direction dir, Position player) {
    Objects.requireNonNull(dir);
    Objects.requireNonNull(player);
    
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
