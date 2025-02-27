package fr.uge.thebigadventure.view;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.imageio.ImageIO;

import fr.uge.thebigadventure.game.Game;
import fr.uge.thebigadventure.game.entity.item.DroppedItem;
import fr.uge.thebigadventure.game.entity.item.Item;
import fr.uge.thebigadventure.game.entity.mob.Friend;
import fr.uge.thebigadventure.game.entity.mob.Mob;
import fr.uge.thebigadventure.game.entity.mob.Player;
import fr.uge.thebigadventure.game.environment.Environment;
import fr.uge.thebigadventure.util.PathCreator;

/**
 * Manage skins for the game
 */
public class Skins {
  
  // ------- Fields -------
  
  /**
   * Map that associate skins with images
   */
  private static final Map<String, BufferedImage> SKINS = new HashMap<String, BufferedImage>();
  
  //------- Methods -------
  
  /**
   * load all skin of Environment, DroppedItem, Mob to setup de map
   * 
   * @throws IOException
   */
  public static void loadSkinFromGame(Game game) throws IOException {
    loadEnvironmentSkin(game.field());
    loadDroppedItemSkin(game.items());
    loadMobsSkin(game.mobs());
    loadPlayerSkin(game.player());
    loadSkin("box");
    loadSkin("arrow");
    loadSkin("fire");
    loadSkin("sprout");
  }
  
  /**
   * Load all images for skin that comes from the field (obstacles, scenery, gates, ...)
   * @param field
   * @throws IOException
   */
  private static void loadEnvironmentSkin(Environment[][] field) throws IOException {
    for (var line: field) {
      for (Environment env: line) {
        if (env != null) {
          loadSkin(env.skin());          
        }
      }
    }
  }
  
  /**
   * Load the player skin
   * @param player
   * @throws IOException
   */
  private static void loadPlayerSkin(Player player) throws IOException {
    loadSkin(player.skin());
  }
  
  /**
   * Load skins from mobs
   * @param mobs
   * @throws IOException
   */
  private static void loadMobsSkin(List<Mob> mobs) throws IOException {
    for (Mob mob: mobs) {
      loadSkin(mob.skin());
      switch (mob) {
      case Friend friend -> loadTrade(friend);
      default -> {}
      }
    }
  }
  
  /**
   * Load skins used for trade
   * @param friend
   * @throws IOException
   */
  private static void loadTrade(Friend friend) throws IOException {
    if (friend.trade() != null) {
      for (var entry: friend.trade().entrySet()) {
        loadSkin(entry.getKey().skin());
        for (Item item: entry.getValue()) {
          loadSkin(item.skin());
        }
      }
    }
  }
  
  /**
   * Load skin used for dropped item
   * @param items
   * @throws IOException
   */
  private static void loadDroppedItemSkin(List<DroppedItem> items) throws IOException {
    for (DroppedItem item: items) {
      loadSkin(item.skin());
    }
  }
  
  /**
   * Load an image based on a skin
   * 
   * @param skin
   * @throws IOException
   */
  private static void loadSkin(String skin) throws IOException {
    skin = skin.toUpperCase(Locale.ROOT);
    if (SKINS.get(skin) != null) {
      return;
    }
    
    BufferedImage image;
    try (var input = Skins.class.getResourceAsStream(PathCreator.imagePath(skin))) {
      if (input == null) {
        throw new IOException("Cannot load image + " + PathCreator.imagePath(skin));
      }
      image = ImageIO.read(input);
    }
    SKINS.put(skin, image);
  }
  
  /**
   * Get image from skin
   * @param skin
   * @return
   */
  public static BufferedImage getSkin(String skin) {
    Objects.requireNonNull(skin);
    return SKINS.get(skin.toUpperCase(Locale.ROOT));
  }
  
}
