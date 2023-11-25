package game.entity.item;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import game.GameObject;
import game.GameObjectID;

public enum GameItems implements GameObject {  
  
  // readable
  BOOK(GameObjectID.READABLE),

  // items
  BOLD(GameObjectID.THING),
  BOX(GameObjectID.THING),
  CASH(GameObjectID.THING),
  CLOCK(GameObjectID.THING),
  COG(GameObjectID.THING),
  CRYSTAL(GameObjectID.THING),
  CUP(GameObjectID.THING),
  DRUM(GameObjectID.THING),
  FLAG(GameObjectID.THING),
  GEM(GameObjectID.THING),
  GUITAR(GameObjectID.THING),
  HIHAT(GameObjectID.THING),
  KEY(GameObjectID.THING),
  LAMP(GameObjectID.THING),
  LEAF(GameObjectID.THING),
  MIRROR(GameObjectID.THING),
  MOON(GameObjectID.THING),
  ORB(GameObjectID.THING),
  PANTS(GameObjectID.THING),
  PAPER(GameObjectID.THING),
  PLANET(GameObjectID.THING),
  RING(GameObjectID.THING),
  ROSE(GameObjectID.THING),
  SAX(GameObjectID.THING),
  SCISSORS(GameObjectID.THING),
  SEED(GameObjectID.THING),
  SHIRT(GameObjectID.THING),
  STAR(GameObjectID.THING),
  SUN(GameObjectID.THING),
  TRUMPET(GameObjectID.THING),
  VASE(GameObjectID.THING),

  // Weapon
  STICK(GameObjectID.WEAPON),
  SHOVEL(GameObjectID.WEAPON),
  SWORD(GameObjectID.WEAPON),
  
  // Food
  BANANA(GameObjectID.FOOD),
  BOBA(GameObjectID.FOOD),
  BOTTLE(GameObjectID.FOOD),
  BURGER(GameObjectID.FOOD),
  CAKE(GameObjectID.FOOD),
  CHEESE(GameObjectID.FOOD),
  DONUT(GameObjectID.FOOD),
  DRINK(GameObjectID.FOOD),
  EGG(GameObjectID.FOOD),
  FRUIT(GameObjectID.FOOD),
  FUNGUS(GameObjectID.FOOD),
  FUNGI(GameObjectID.FOOD),
  LOVE(GameObjectID.FOOD),
  PIZZA(GameObjectID.FOOD),
  POTATO(GameObjectID.FOOD),
  PUMPKIN(GameObjectID.FOOD),
  TURNIP(GameObjectID.FOOD),
  BUCKET(GameObjectID.FOOD);

  private final GameObjectID id;
  
  private static final List<GameItems> ITEMS = List.of(GameItems.values());
  private static final Map<String, GameObjectID> ITEMSNAMES = ITEMS.stream()
      .collect(Collectors.toUnmodifiableMap(GameItems::name, GameItems::id));
  
  public GameObjectID id() {
    return id;
  }
  
  public static GameObjectID getId(String name) {
    return ITEMSNAMES.getOrDefault(name, GameObjectID.UNKNOWN);
  }
  
  GameItems(GameObjectID id) {
    this.id = id;
  }
  
}
