package fr.uge.thebigadventure.game.entity.mob;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Behaviour {
  SHY,
  STROLL,
  AGRESSIVE,
  UNKNOWN;
  
  private static final Map<String, Behaviour> mapName = List.of(Behaviour.values()).stream()
      .collect(Collectors.toUnmodifiableMap(Behaviour::name, Function.identity()));
  
  public static Behaviour get(String name) {
    return mapName.getOrDefault(name.toUpperCase(Locale.ROOT), UNKNOWN);
  }
  
}
