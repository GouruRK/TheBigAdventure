package fr.uge.thebigadventure.util;

import java.util.Objects;

public record Zone(Position topLeft, Position bottomRight) {

  public Zone {
    Objects.requireNonNull(topLeft);
    Objects.requireNonNull(bottomRight);
  }
  
  
  /**
   * Check if the position is insane the Game Window (just to make sure that nothing can go through)
   * 
   * @param pos
   * @return boolean
   */
  public boolean isInside(Position pos) {
    Objects.requireNonNull(topLeft);
    return (topLeft.x() <= pos.x() && pos.x() < bottomRight.x())
        && (topLeft.y() <= pos.y() && pos.y() < bottomRight.y());
  }
  
  @Override
  public String toString() {
    return "Zone(topLeft: " + topLeft.toString()
        + ", bottomRight: " + bottomRight.toString()
        + ")";
  }
  
  public int width() {
    return (int)(bottomRight.x() - topLeft.x());
  }
  
  public int height() {
    return (int)(bottomRight.y() - topLeft.y());
  }
  
  public int middleX() {
    return (int) (topLeft.x() + width()/2);
  }
  
  public int middleY() {
    return (int) (topLeft.y() + height()/2);
  }
}
