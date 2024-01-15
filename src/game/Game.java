package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import game.entity.item.DroppedItem;
import game.entity.item.Item;
import game.entity.mob.Behaviour;
import game.entity.mob.Mob;
import game.entity.mob.Player;
import game.environment.Environment;
import game.environment.GameEnvironment;
import util.Direction;
import util.Position;
import util.Utils;
import util.Zone;

public class Game {

  //------- Constants -------
  
  private static final int FIRE_SPREADING_PERCENTAGE = 20;
  
  //------- Fields -------
  
  private static final Inventory inventory = new Inventory();
  
  private final Zone zone;
  private final Environment[][] field;
  private final ArrayList<Mob> mobs;
  private final ArrayList<DroppedItem> items;
  private final Position startingPosition;
  private final Map<Position, Game> teleports;
  private final Position backPos;
  private Player player = null;

  
  // -------- Constructor --------
  
  /***
   * define a Game
   * 
   * @param size
   * @param field
   * @param mobs
   * @param items
   * @param player
   */
  public Game(Zone zone, Environment[][] field, ArrayList<Mob> mobs, ArrayList<DroppedItem> items,
              Position startingPosition, Map<Position, Game> teleports, Position backPos, Player player) {
    Objects.requireNonNull(zone);
    Objects.requireNonNull(field);
    Objects.requireNonNull(mobs);
    Objects.requireNonNull(items);
    Objects.requireNonNull(startingPosition);
    Objects.requireNonNull(teleports);
    this.zone = zone;
    this.field = field;
    this.mobs = mobs;
    this.items = items;
    this.startingPosition = startingPosition;
    this.teleports = teleports;
    this.backPos = backPos;
    this.player = player;
  }

  @Override
  public String toString() {
    return zone.toString() + "\n"
        + player.toString() + "\n"
        + "Mobs: " + mobs + "\n"
        + "Items: " + items;
  }

  //------- Getter -------
  
  /**
   * Get the player instance
   * @return player
   */
  public Player player() {
    return player;
  }

  /***
   * Get the environment that contains all obstacles and scenery of the map
   * @return Environment[][]
   */
  public Environment[][] field() {
    return field;
  }

  /***
   * Get the list of all mobs of the game
   * @return List<Mob>
   */
  public List<Mob> mobs() {
    return List.copyOf(mobs);
  }

  /***
   * Get a list of DroppedItem
   * @return List<DroppedItem>
   */
  public List<DroppedItem> items() {
    return List.copyOf(items);
  }
  
  /**
   * Get the inventory instance
   * @return
   */
  public Inventory inventory() {
    return inventory;
  }
  
  public Position startingPosition() {
    return startingPosition;
  }
  
  public Map<Position, Game> teleports() {
    return teleports;
  }
  
  public Position back() {
    return backPos;
  }
  
  public void setPlayer(Player player) {
    Objects.requireNonNull(player);
    this.player = player;
    this.player.setPos(startingPosition);
  }
  
  /**
   * Search if there is an item on the ground at given position
   * @param pos position
   * @return the item if its on the ground, else null
   */
  public DroppedItem searchItem(Position pos) {
    Objects.requireNonNull(pos);
    return items.stream().filter(item -> item.pos().equals(pos)).findFirst().orElse(null);
  }
  
  /**
   * Search if there is a mob at given position
   * @param pos
   * @return the mob if its at the position, else null
   */
  public Mob searchMob(Position pos) {
    Objects.requireNonNull(pos);
    return mobs.stream().filter(mob -> mob.pos().equals(pos)).findFirst().orElse(null);
  }
  
  /***
   * Search an Environment with a certain Pos in the List of Environment
   * 
   * @param pos
   * @return Environment
   */
  public Environment searchEnvironment(Position pos) {
    Objects.requireNonNull(pos);
    if (zone.isInside(pos)) {
      return field[(int)pos.y()][(int)pos.x()];      
    }
    return null;
  }
  
  /**
   * Remove an item from the DroppedItem list instance
   * @param item to remove
   */
  public void removeDroppedItem(DroppedItem item) {
    Objects.requireNonNull(item);
    items.removeIf(i -> i.equals(item));
  }


  // ------- Movement related -------
  
  /**
   * Move all mobs of the map
   */
  public void moveMobs() {
    // here, all mobs moved of one square
    mobs.forEach(mob -> moveMob(mob));
  }

  private void moveMob(Mob mob) {
    switch (mob.behaviour()) {
    case Behaviour.AGRESSIVE -> moveAgressiveMob(mob);
    case Behaviour.SHY -> move(mob, player.facing(), 1);
    default -> move(mob, Direction.randomDirection(), 1);
    }
  }
  
  /**
   * Agressive mobs attack the player if he is next to a mob
   */
  private void moveAgressiveMob(Mob mob) {
    move(mob, Direction.randomDirection(), 1);
    if (Utils.distance(player.pos(), mob.pos()) == 1 && Utils.randomBoolean()) {
      attackMob(mob, player);
    }
  }
  
  /**
   * Move the given mob one step on the given direction if the movement is possible
   * @param mob mob to move. If mob is the player, it can pick up item from the ground
   * @param dir direction to move
   * @param step number (or proportion) of square to move
   */
  public void move(Mob mob, Direction dir, double step) {
    Objects.requireNonNull(mob);
    Objects.requireNonNull(dir);
    
    Position nextPos = mob.pos().computeDirection(dir, step);    
    if (nextPos == null) {
      return;
    }
    if (mob.isMoveInZonePossible(nextPos) && isMoveInGamePossible(mob, nextPos)) {
      mob.setPos(nextPos);
      
      switch (mob) {
        case Player player -> pickUpItem(player);
        default -> {}
      }
      
    }
    mob.setFacing(dir);
  }
  
  /**
   * Check if the move is possible
   * 
   * @param pos
   * @return boolean
   */  
  private boolean isMoveInGamePossible(Mob mobToMove, Position pos) {
    Environment env = searchEnvironment(pos);
    Mob mob = searchMob(pos);
    
    return switch (mobToMove) {
      case Player p -> (env == null || env.standable() || env.isOpen()) && mob == null;
      default -> (env == null || env.standable() || env.isOpen()) && !pos.equals(player.pos());
    };
  }
  
  //------- Modifiers -------
  
  /**
   * Remove all dead mobs
   */
  private void removeDeadMob() {
    mobs.removeIf(Mob::isDead);
  }
  
  /**
   * If there is an item on the ground at the same position of the player and
   * the player doesn't already hold an item, it can pick it up and put it as
   * a hold item
   * @param player
   */
  private void pickUpItem(Player player) {
    if (player.hold() == null) {
      DroppedItem item = searchItem(player.pos());
      if (item != null) {
        player.setHold(item.item());
        removeDroppedItem(item);
      }
    }
  }
  
  /**
   * Add a dropped item on the map
   * @param item
   */
  public void addDroppedItem(DroppedItem item) {
    Objects.requireNonNull(item);
    
    items.add(item);
  }
  
  /**
   * Create a dropped item at given position and add it to the map
   * @param item
   * @param pos
   */
  public void addDroppedItem(Item item, Position pos) {
    Objects.requireNonNull(item);
    Objects.requireNonNull(pos);
    
    items.add(new DroppedItem(pos, item));
  }
  
  /**
   * Replace current environment store at 'env.pos()' and set it at its coordinates
   * @param env
   */
  public void setEnvironment(Environment env) {
    Objects.requireNonNull(env);
    
    if (zone.isInside(env.pos())) {
      field[(int)env.pos().y()][(int)env.pos().x()] = env;
    }
  }
  
  /**
   * Create an environment of given skin and at given position, and replace the current
   * stored environment at position
   * @param skin
   * @param pos
   */
  public void setEnvironment(String skin, Position pos) {
    Objects.requireNonNull(skin);
    Objects.requireNonNull(pos);
    
    Environment env = Environment.createEnvironment(skin, pos);
    if (env != null) {
      setEnvironment(env);
    }
  }
  
  /**
   * Manage conflict between two mobs
   * @param attacker
   * @param victim
   */
  public void attackMob(Mob attacker, Mob victim) {
    // we manually check if instances aren't null because this method is 
    // generic, and be called directly after a 'searchMob' which can return null
    if (attacker != null && victim != null) {
      victim.takeDamage(attacker.damage());
      removeDeadMob();      
    }
  }
  
  /**
   * Set current environment stored at a given position to null
   * @param pos
   */
  public void removeEnvironment(Position pos) {
    Objects.requireNonNull(pos);
    
    if (zone.isInside(pos)) {
      field[(int)pos.y()][(int)pos.x()] = null;
    }
  }

  // ------- Fire -------
  
  /**
   * Spread fire around the given fire
   * @param env
   */
  private void spreadFire(Environment env) {
    // fire as 20% chance to spread
    if (Utils.randomInt(0, 100) <= FIRE_SPREADING_PERCENTAGE) {
      List<Position> around = env.pos().getAround();
      Position posToSpread = around.get(Utils.randomInt(0, around.size() - 1));
      Environment tile = searchEnvironment(posToSpread);
      if (tile == null) {
        setEnvironment("fire", posToSpread);
      } else if (tile.getEnvironment() == GameEnvironment.TREE) {
        removeEnvironment(posToSpread);
        setEnvironment("fire", posToSpread);
      }
    }
  }
  
  /**
   * Search for fires on the entire map and spread them
   */
  public void spreadFire() {
    for (int y = 0; y < zone.height(); y++) {
      for (int x = 0; x < zone.width(); x++) {
        Environment env = field[y][x]; 
        if (env != null && env.getEnvironment() == GameEnvironment.FIRE) {
          spreadFire(env);
        }
      }
    }
  }
  
}
