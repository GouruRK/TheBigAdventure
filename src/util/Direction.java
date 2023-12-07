package util;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Direction {
  NORTH,
  SOUTH,
  EAST,  
  WEST,
  NONE,
  NORTHEAST,
  NORTHWEST,
  SOUTHEAST,
  SOUTHWEST,
  UNKNOWN;
  
  private static final Random DirGenerator = new Random();
  
  private static final Map<String, Direction> mapName = List.of(Direction.values()).stream()
      .collect(Collectors.toUnmodifiableMap(Direction::name, Function.identity()));
  
  public static Direction get(String name) {
    return mapName.getOrDefault(name.toUpperCase(), UNKNOWN);
  }
  
  public static Direction randomDirection() {
    return switch (DirGenerator.ints(0, 4).findFirst().getAsInt()) {
    case 0 -> Direction.EAST;
    case 1 -> Direction.WEST;
    case 2 -> Direction.NORTH;
    case 3 -> Direction.WEST;
    default -> Direction.NONE;
    };
  }
  
}
