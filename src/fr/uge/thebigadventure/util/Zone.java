package fr.uge.thebigadventure.util;

import java.util.Objects;

/**
 * Class that represent a zone element bounded by two positions at its top left corner
 * and its bottom right one
 */
public record Zone(Position topLeft, Position bottomRight) {

  //------- Constructor -------
  
  public Zone {
    Objects.requireNonNull(topLeft);
    Objects.requireNonNull(bottomRight);
  }

  //------- Getters -------
  
  /**
   * Get the width of a zone
   * @return
   */
  public int width() {
    return (int)(bottomRight.x() - topLeft.x());
  }
  
  /**
   * Get the height of a zone
   * @return
   */
  public int height() {
    return (int)(bottomRight.y() - topLeft.y());
  }
  
  /**
   * Get real the middle abscissa of the zone
   * @return
   */
  public int middleX() {
    return (int) (topLeft.x() + width()/2);
  }
  
  /**
   * Get real the middle ordinate of the zone
   * @return
   */
  public int middleY() {
    return (int) (topLeft.y() + height()/2);
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
  
  //------- Others -------
  
  @Override
  public String toString() {
    return "Zone(topLeft: " + topLeft.toString()
        + ", bottomRight: " + bottomRight.toString()
        + ")";
  }
  
}
