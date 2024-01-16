package graphic.controller;

import java.util.Objects;

import util.Direction;
import util.Position;
import util.Zone;

public class WindowController {

  private static final int maxWidth = 40;
  private static final int maxHeight = maxWidth/2;
  
  private final Zone game;
  private final Zone onScreen;
  private int windowWidth;
  private int windowHeight;
  
  public WindowController(Zone game, Position player) {
    Objects.requireNonNull(game);
    this.game = game;
    Position topLeft = new Position(Math.max(0, player.x() - maxWidth), Math.max(0, player.y() - maxHeight));
    Position bottomRight = new Position(Math.min(game.bottomRight().x(), player.x() + maxWidth) + 3,
                                        Math.min(game.bottomRight().y(), player.y() + maxHeight) + 3);    
    
    this.onScreen = new Zone(topLeft, bottomRight);
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
    if (game.width() < maxWidth) {
      return -(maxWidth/2 - game.width()/2);
    }
    return (int)onScreen.topLeft().x();
  }
  
  public int yOffset() {
    if (game.height() < maxHeight) {
      return -(maxHeight/2 - game.height()/2);
    }
    return (int)onScreen.topLeft().y();
  }
  
  public void moveWindow(Direction dir, Position player) {
    Objects.requireNonNull(dir);
    
    if (dir == Direction.NORTH) {
      if (onScreen.topLeft().y() != game.topLeft().y()) {
        if (player.y() == onScreen.middleY()) {
          onScreen.topLeft().addLocalY(-1);
          onScreen.bottomRight().addLocalY(-1);          
        }
      }
    } else if (dir == Direction.SOUTH) {
      if (onScreen.bottomRight().y() != game.bottomRight().y()) {
        if (player.y() == onScreen.middleY()) {
          onScreen.topLeft().addLocalY(1);
          onScreen.bottomRight().addLocalY(1);          
        }
      }
    } else if (dir == Direction.WEST) {
      if (onScreen.topLeft().x() != game.topLeft().x()) {
        if (player.x() == onScreen.middleX()) {
          onScreen.topLeft().addLocalX(-1);
          onScreen.bottomRight().addLocalX(-1);          
        }
      }
    } else if (dir == Direction.EAST) {
      if (onScreen.bottomRight().x() != game.bottomRight().x()) {
        if (player.x() == onScreen.middleX()) {
          onScreen.topLeft().addLocalX(1);
          onScreen.bottomRight().addLocalX(1);
        }
      }
    }
  }
  
}
