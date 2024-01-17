package fr.uge.thebigadventure.util;

import java.util.Random;

public class Utils {

  private static final Random generator = new Random();
  
  public static int randomInt(int min, int max) {
    return generator.ints(min, max).findFirst().getAsInt();
  }
  
  public static boolean randomBoolean() {
    return randomInt(0, 2) == 1;
  }
  
  public static int abs(int a) {
    return a < 0 ? -a: a;
  }
  
  public static int abs(double a) {
    return abs((int)a);
  }
  
  public static int distance(Position a, Position b) {
    return abs(a.x() - b.x()) + abs(a.y() - b.y());
  }
  
}
