package fr.uge.thebigadventure.util;

import java.util.Random;

/**
 * Contains numerous usefull function with no links together
 */
public class Utils {

  //------- Constant -------
  
  /**
   * Generator to have random events
   */
  private static final Random generator = new Random();
  
  //------- Methods -------
  
  /**
   * Get a random integer bounded by min and max (include)
   * @param min
   * @param max
   * @return
   */
  public static int randomInt(int min, int max) {
    return generator.ints(min, max).findFirst().getAsInt();
  }
  
  /**
   * Get a random boolean
   * @return
   */
  public static boolean randomBoolean() {
    return randomInt(0, 2) == 1;
  }
  
  /**
   * Compute distance between two positions
   * @param a
   * @param b
   * @return
   */
  public static int distance(Position a, Position b) {
    return (int)(Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y()));
  }
  
  /**
   * Convert an angle from degree to radian
   * @param angle
   * @return
   */
  public static double degToRad(double angle) {
    return angle * Math.PI / 180;
  }
  
}
