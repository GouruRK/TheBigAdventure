package fr.uge.thebigadventure.util;

import java.util.Locale;

/**
 * Manage path for each type of data a path is required
 */
public class PathCreator {

  /**
   * Get path for an image
   * @param imageName
   * @return
   */
  public static String imagePath(String imageName) {
    return "/images/" + imageName.toLowerCase(Locale.ROOT) + ".png"; 
  }
  
  
  /**
   * Get path for a map
   * @param mapName
   * @return
   */
  public static String mapPath(String mapName) {
    return "map/" + mapName + ".map";
  }
  
}
