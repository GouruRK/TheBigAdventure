package util;

import java.util.Locale;

public class PathCreator {

  public static String imagePath(String imageName) {
    return "/images/" + imageName.toLowerCase(Locale.ROOT) + ".png"; 
  }
  
  public static String mapPath(String mapName) {
    return "map/" + mapName + ".map";
  }
  
}
