package game;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Kind {
  
  FRIEND,
  ENEMY,
  ITEM,
  OBSTACLE,
  UNKNOWN;
  
	/**
	 * return a map of all Kind types
	 */
  private static final Map<String, Kind> mapName = List.of(Kind.values()).stream()
      .collect(Collectors.toUnmodifiableMap(Kind::name, Function.identity()));
  
  /**
   * returns us a name in the Kind map, UNKNOWN for default value
   * 
   * @param name
   * @return
   */
  public static Kind get(String name) {
    return mapName.getOrDefault(name.toUpperCase(), UNKNOWN);
  }
  
}
