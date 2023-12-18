package util;

public class PathCreator {

  public static String imagePath(String imageName) {
    return "images/" + imageName + ".png"; 
  }
  
  public static String mapPath(String mapName) {
    return "map/" + mapName + ".map";
  }
  
}
