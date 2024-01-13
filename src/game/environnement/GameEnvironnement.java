package game.environnement;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import game.GameObject;
import game.GameObjectID;

public enum GameEnvironnement implements GameObject {

  // Scenery
  ALGAE(GameObjectID.SCENERY),
  CLOUD(GameObjectID.SCENERY),
  FLOWER(GameObjectID.SCENERY),
  FOLIAGE(GameObjectID.SCENERY),
  GRASS(GameObjectID.SCENERY),
  LADDER(GameObjectID.SCENERY),
  LILY(GameObjectID.SCENERY),
  PLANK(GameObjectID.SCENERY),
  REED(GameObjectID.SCENERY),
  ROAD(GameObjectID.SCENERY),
  SPROUT(GameObjectID.SCENERY),
  TILE(GameObjectID.SCENERY), 
  TRACK(GameObjectID.SCENERY), 
  VINE(GameObjectID.SCENERY),
  FIRE(GameObjectID.SCENERY),
  
  // Obstacle
  BED(GameObjectID.OBSTACLE),
  BOG(GameObjectID.OBSTACLE),
  BOMB(GameObjectID.OBSTACLE),
  BRICK(GameObjectID.OBSTACLE),
  CHAIR(GameObjectID.OBSTACLE),
  CLIFF(GameObjectID.OBSTACLE),
  FENCE(GameObjectID.OBSTACLE),
  FORT(GameObjectID.OBSTACLE),
  HEDGE(GameObjectID.OBSTACLE),
  HUSK(GameObjectID.OBSTACLE),
  HUSKS(GameObjectID.OBSTACLE),
  LOCK(GameObjectID.OBSTACLE),
  MONITOR(GameObjectID.OBSTACLE),
  PIANO(GameObjectID.OBSTACLE),
  PILLAR(GameObjectID.OBSTACLE),
  PIPE(GameObjectID.OBSTACLE),
  ROCK(GameObjectID.OBSTACLE),
  RUBBLE(GameObjectID.OBSTACLE),
  SHELL(GameObjectID.OBSTACLE),
  SIGN(GameObjectID.OBSTACLE),
  SPIKE(GameObjectID.OBSTACLE),
  STATUE(GameObjectID.OBSTACLE),
  STUMP(GameObjectID.OBSTACLE),
  TABLE(GameObjectID.OBSTACLE), 
  TREE(GameObjectID.OBSTACLE),
  TREES(GameObjectID.OBSTACLE),
  WALL(GameObjectID.OBSTACLE),
  ICE(GameObjectID.OBSTACLE),
  LAVA(GameObjectID.OBSTACLE),
  WATER(GameObjectID.OBSTACLE),
  
  // Gate
  DOOR(GameObjectID.GATE),
  GATE(GameObjectID.GATE),
  HOUSE(GameObjectID.GATE),
  TOWER(GameObjectID.GATE),
  
  // Effect
  BUBBLE(GameObjectID.EFFECT),
  DUST(GameObjectID.EFFECT),
  
  // Other
  WIND(GameObjectID.WIND);
  
  private final GameObjectID id;
  
  public static final Set<GameEnvironnement> BLOCKS = Set.of(GameEnvironnement.values());
  public static final Map<String, GameObjectID> BLOCKSNAMES = BLOCKS.stream()
      .collect(Collectors.toUnmodifiableMap(GameEnvironnement::name, GameEnvironnement::id));
  private static final Map<String, GameEnvironnement> TYPEBLOCKS = BLOCKS.stream()
      .collect(Collectors.toUnmodifiableMap(GameEnvironnement::name, Function.identity()));
  
  public GameObjectID id() {
    return id;
  }
  
  public static GameObjectID getId(String name) {
    return BLOCKSNAMES.getOrDefault(name.toUpperCase(), GameObjectID.UNKNOWN);
  }
  
  public static GameEnvironnement getEnvironnement(String name) {
    return TYPEBLOCKS.get(name.toUpperCase());
  }
  
  GameEnvironnement(GameObjectID id) {
    this.id = id;
  }
  
}

