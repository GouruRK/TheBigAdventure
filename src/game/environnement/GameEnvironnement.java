package game.environnement;

import java.util.List;
import java.util.Map;
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
  
  // Obstacle
  BED(GameObjectID.OBSTACLE),
  BOG(GameObjectID.OBSTACLE),
  BOMB(GameObjectID.OBSTACLE),
  BIRCK(GameObjectID.OBSTACLE),
  CHAIR(GameObjectID.OBSTACLE),
  CLIFF(GameObjectID.OBSTACLE),
  FENCE(GameObjectID.OBSTACLE),
  FORT(GameObjectID.OBSTACLE),
  HEDGE(GameObjectID.OBSTACLE),
  HUSK(GameObjectID.OBSTACLE),
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
  
  // Gate
  DOOR(GameObjectID.GATE),
  GATE(GameObjectID.GATE),
  HOUSE(GameObjectID.GATE),
  TOWER(GameObjectID.GATE),
  
  // Biome
  ICE(GameObjectID.ELEMENT),
  LAVA(GameObjectID.ELEMENT),
  WATER(GameObjectID.ELEMENT);
  
  private final GameObjectID id;
  
  public static final List<GameEnvironnement> BLOCKS = List.of(GameEnvironnement.values());
  public static final Map<String, GameObjectID> BLOCKSNAMES = BLOCKS.stream()
      .collect(Collectors.toUnmodifiableMap(GameEnvironnement::name, GameEnvironnement::id));
  
  
  public GameObjectID id() {
    return id;
  }
  
  public static GameObjectID getId(String name) {
    return BLOCKSNAMES.getOrDefault(name, GameObjectID.UNKNOWN);
  }
  
  GameEnvironnement(GameObjectID id) {
    this.id = id;
  }
  
}

