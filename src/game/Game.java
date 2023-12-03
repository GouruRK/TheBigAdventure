package game;

import java.util.List;
import java.util.Objects;

import game.entity.item.DroppedItem;
import game.entity.mob.Mob;
import game.entity.mob.Player;
import game.environnement.Environnement;
import util.Position;

public class Game {

  private final Position size;
  public final Environnement[][] field;
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
  
}
