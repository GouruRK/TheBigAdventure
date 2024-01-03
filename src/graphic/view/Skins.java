package graphic.view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

import game.Game;
import game.entity.item.DroppedItem;
import game.entity.item.Item;
import game.entity.mob.Friend;
import game.entity.mob.Mob;
import game.entity.mob.Player;
import game.environnement.Environnement;
import util.PathCreator;

public class Skins {
  private static final HashMap<String, BufferedImage> SKINS = new HashMap<String, BufferedImage>();
  
  /**
   * load all skin of Environnement, DroppedItem, Mob to setup de map
   * 
   * @throws IOException
   */
  public static void loadSkinFromGame(Game game) throws IOException {
    loadEnvironnementSkin(game.field());
    loadDroppedItemSkin(game.items());
    loadMobsSkin(game.mobs());
    loadPlayerSkin(game.player());
  }
  
  private static void loadEnvironnementSkin(Environnement[][] field) throws IOException {
    loadSkin("box");
    for (var line: field) {
      for (Environnement env: line) {
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
      loadSkin("arrow");
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
    if (SKINS.get(skin) != null) {
      return;
    }
    File imagePath = new File(PathCreator.imagePath(skin.toLowerCase()));
    try {
      SKINS.put(skin, ImageIO.read(imagePath));
    } catch (IOException e) {
      throw new IOException("Cannot find image for skin '" + skin + "' (path is '" + imagePath + "')");
    }
  }
  
  public static BufferedImage getSkin(String skin) {
    return SKINS.get(skin);
  }
  
}
