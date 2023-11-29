package util;

import java.util.List;
import java.util.Map;
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
  
  private static final Map<String, Direction> mapName = List.of(Direction.values()).stream()
      .collect(Collectors.toUnmodifiableMap(Direction::name, Function.identity()));
  
  public static Direction get(String name) {
    return mapName.getOrDefault(name.toUpperCase(), UNKNOWN);
  }
  
}
