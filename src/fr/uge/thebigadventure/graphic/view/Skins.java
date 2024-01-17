package fr.uge.thebigadventure.graphic.view;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.imageio.ImageIO;

import fr.uge.thebigadventure.game.Game;
import fr.uge.thebigadventure.game.entity.item.DroppedItem;
import fr.uge.thebigadventure.game.entity.item.Item;
import fr.uge.thebigadventure.game.entity.mob.Friend;
import fr.uge.thebigadventure.game.entity.mob.Mob;
import fr.uge.thebigadventure.game.entity.mob.Player;
import fr.uge.thebigadventure.game.environment.Environment;
import fr.uge.thebigadventure.util.PathCreator;

public class Skins {
  private static final HashMap<String, BufferedImage> SKINS = new HashMap<String, BufferedImage>();
  
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
  
  private static void loadEnvironmentSkin(Environment[][] field) throws IOException {
    for (var line: field) {
      for (Environment env: line) {
        if (env != null) {
          loadSkin(env.skin());          
        }
      }
    }
  }
  
  private static void loadPlayerSkin(Player player) throws IOException {
    loadSkin(player.skin());
  }
  
  private static void loadMobsSkin(List<Mob> mobs) throws IOException {
    for (Mob mob: mobs) {
      loadSkin(mob.skin());
      switch (mob) {
      case Friend friend -> loadTrade(friend);
      default -> {}
      }
    }
  }
  
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
  
  private static void loadDroppedItemSkin(List<DroppedItem> items) throws IOException {
    for (DroppedItem item: items) {
      loadSkin(item.skin());
    }
  }
  
  /**
   * load skin on the screen
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
  
  public static BufferedImage getSkin(String skin) {
    return SKINS.get(skin.toUpperCase(Locale.ROOT));
  }
  
}
