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
    
    if (dir == Direction.NORTH && corner.y() != 0) {
      if (0 <= player.y() && player.y() <= maxHeight/2) {
        corner.addLocalY(-1);
      }
    } else if (dir == Direction.SOUTH && corner.y() + maxHeight != game.bottomRight().y()) {
      if (!(game.bottomRight().y() - maxHeight/2 <= player.y() && player.y() <= game.bottomRight().y())) {
        corner.addLocalY(1);
      }
    } else if (dir == Direction.WEST && corner.x() != 0) {
      if (0 <= player.x() && player.x() <= maxWidth/2) {
        corner.addLocalX(-1);
      }
    } else if (dir == Direction.EAST && corner.x() + maxWidth != game.bottomRight().x()) {
      if (!(game.bottomRight().x() - maxWidth/2 <= player.x() && player.x() <= game.bottomRight().x())) {
        corner.addLocalX(1);
      }
    }
    
//    if (dir == Direction.NORTH && corner.y() != 0) {
//      if (player.y() == corner.y() + maxHeight/2 || player.y() == corner.y() + maxHeight/2 + 1) {
//        corner.addLocalY(-1);
//      }
//    } else if (dir == Direction.SOUTH && corner.y() + maxHeight != game.bottomRight().y()) { 
//      if (player.y() == corner.y() + maxHeight/2 || player.y() == corner.y() + maxHeight/2 + 1) {
//        corner.addLocalY(1); 
//      }
//    } else if (dir == Direction.WEST && corner.x() != 0) {
//      if (player.x() == corner.x() + maxWidth/2 || player.x() == corner.x() + maxWidth/2 + 1) {
//        corner.addLocalX(-1);
//      }
//    } else if (dir == Direction.EAST && corner.x() + maxWidth != game.bottomRight().x()) {
//      if (player.x() == corner.x() + maxWidth/2 || player.x() == corner.x() + maxWidth/2 + 1)
//        corner.addLocalX(1);
//    }
  }
    
    
  
}
