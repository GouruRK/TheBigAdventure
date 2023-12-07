package game;

import java.util.List;
import java.util.Objects;

import game.entity.item.DroppedItem;
import game.entity.mob.Mob;
import game.entity.mob.Player;
import game.environnement.Environnement;
import graph.KeyOperation;
import util.Direction;
import util.Position;

public class Game {

  private final Position size;
  private final Environnement[][] field;
  private final List<Mob> mobs;
  private final List<DroppedItem> items;
  private final Player player;

  public Game(Position size, Environnement[][] field, List<Mob> mobs, List<DroppedItem> items, Player player) {
    Objects.requireNonNull(size);
    Objects.requireNonNull(field);
    Objects.requireNonNull(mobs);
    Objects.requireNonNull(items);
    Objects.requireNonNull(player);
    this.size = size;
    this.field = field;
    this.mobs = mobs;
    this.items = items;
    this.player = player;
  }

  @Override
  public String toString() {
    return size.toString() + "\n"
        + player.toString() + "\n"
        + "Mobs: " + mobs + "\n"
        + "Items: " + items;
  }

  public Player player() {
    return player;
  }

  public Environnement[][] field() {
    return field;
  }

  public List<Mob> mobs() {
    return mobs;
  }

  public List<DroppedItem> items() {
    return items;
  }

  public void move(Mob mob, Direction dir, double step) {
    // ici, faire un switch ou je ne sais quoi pour savoir dans quelle dir tu vas
    Position nextPos = switch (dir) {
    case NORTH -> mob.pos().addY(-step);
    case WEST -> mob.pos().addX(-step);
    case SOUTH -> mob.pos().addY(step);
    case EAST -> mob.pos().addX(step);
    default -> 	null;
    };
    if (mob.isMoveInZonePossible(nextPos) && isMoveInGamePossible(nextPos)) {
      mob.setPos(nextPos);
    }
  }

  public Mob searchMob(Position pos) {
    for (Mob mob : mobs) {
        if (mob.pos().equals(pos)) {
            return mob;
        }
    }
    return null;
}
  
  public Environnement searchEnvironnement(Position pos) {
    return this.field[(int)pos.y()][(int)pos.x()];
  }

  public boolean isMoveInGamePossible(Position pos) {
    Environnement env = searchEnvironnement(pos);
    Mob mob = searchMob(pos);
    
    return (env == null || env.standable() || env.isOpen()) && mob == null;
  }

}
