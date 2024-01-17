package fr.uge.thebigadventure.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import fr.uge.thebigadventure.game.entity.item.DroppedItem;
import fr.uge.thebigadventure.game.entity.item.Item;
import fr.uge.thebigadventure.game.entity.mob.Behaviour;
import fr.uge.thebigadventure.game.entity.mob.Mob;
import fr.uge.thebigadventure.game.entity.mob.Player;
import fr.uge.thebigadventure.game.environment.Environment;
import fr.uge.thebigadventure.game.environment.GameEnvironment;
import fr.uge.thebigadventure.util.Direction;
import fr.uge.thebigadventure.util.Position;
import fr.uge.thebigadventure.util.Utils;
import fr.uge.thebigadventure.util.Zone;

/**
 * Main game structure to create interfaces between the current game and datas
 */
public class Game {

  //------- Constants -------
  
  private static final int FIRE_SPREADING_PERCENTAGE = 20;
  
  //------- Fields -------
  
  private static final Inventory inventory = new Inventory();
  
  /**
   * Map zone
   */
  private final Zone zone;
  
  /**
   * Contains map element like obstacles and scenery
   */
  private final Environment[][] field;
  
  /**
   * Contains mobs
   */
  private final ArrayList<Mob> mobs;
  
  /**
   * Array of dropped item
   */
  private final ArrayList<DroppedItem> items;
  
  /**
   * Position where the players appears in the map
   * Very useful when the players come from an 
   * other map
   */
  private final Position startingPosition;
  
  /**
   * Position where the players needs to be to be teleported back
   */
  private final Position backPos;
  
  /**
   * Player field. Not final in case of teleportation, because a map
   * when teleported, the player come from another map
   */
  private Player player = null;

  
  // -------- Constructor --------
  
  /***
   * Creates a game
   * 
   * @param size field size
   * @param field field that contains obstacles and scenery
   * @param mobs arrays of mobs
   * @param items array of dropped item
   * @param backPos position where the players needs to be to leave the current game
   */
  public Game(Zone zone, Environment[][] field, ArrayList<Mob> mobs, ArrayList<DroppedItem> items,
              Position startingPosition, Position backPos, Player player) {
    Objects.requireNonNull(zone);
    Objects.requireNonNull(field);
    Objects.requireNonNull(mobs);
    Objects.requireNonNull(items);
    Objects.requireNonNull(startingPosition);
    this.zone = zone;
    this.field = field;
    this.mobs = mobs;
    this.items = items;
    this.startingPosition = startingPosition;
    this.backPos = backPos;
    this.player = player;
  }

  @Override
  public String toString() {
    return zone.toString() + "\n"
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
  
  /**
   * Get position when the player appears on the map
   * @return
   */
  public Position startingPosition() {
    return startingPosition;
  }
  
  /**
   * Get position the player needs to be to go back the previous map
   * @return
   */
  public Position back() {
    return backPos;
  }
  
  public Zone zone() {
    return zone;
  }
  
  /**
   * 
   * @param player
   */
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
    case Behaviour.SHY -> move(mob, player.facing());
    default -> move(mob, Direction.randomDirection());
    }
  }
  
  /**
   * Agressive mobs attack the player if he is next to a mob
   */
  private void moveAgressiveMob(Mob mob) {
    move(mob, Direction.randomDirection());
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
  public boolean move(Mob mob, Direction dir) {
    Objects.requireNonNull(mob);
    Objects.requireNonNull(dir);
    
    Position nextPos = mob.pos().computeDirection(dir);    
    if (nextPos == null) {
      return false;
    }
    if (mob.isMoveInZonePossible(nextPos) && isMoveInGamePossible(mob, nextPos)) {
      mob.setPos(nextPos);
      
      switch (mob) {
        case Player player -> pickUpItem(player);
        default -> {}
      }
      return true;
    }
    mob.setFacing(dir);
    return false;
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
