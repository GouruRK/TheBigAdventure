package graph;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

import javax.imageio.ImageIO;

import fr.umlv.zen5.Application;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.Event.Action;
import fr.umlv.zen5.KeyboardKey;
import fr.umlv.zen5.ScreenInfo;
import game.Game;
import game.entity.item.DroppedItem;
import game.entity.mob.Mob;
import game.entity.mob.Player;
import game.environnement.Environnement;
import util.Position;

public class Window {

  private final int IMAGESIZE = 24;

  private final Game game;
  private final HashMap<String, BufferedImage> skinMap;
  private int windowWidth;
  private int windowHeight;

  public Window(Game game) throws IOException {
    Objects.requireNonNull(game);
    this.game = game;
    skinMap = new HashMap<String, BufferedImage>();
    loadSkin();
  }
  
  private void loadSkin(String skin) throws IOException {
    if (skinMap.get(skin) != null) {
      return;
    }
    File imagePath = new File("images/" + skin.toLowerCase() + ".png");
    try {
      skinMap.put(skin, ImageIO.read(imagePath));
    } catch (IOException e) {
      throw new IOException("Cannot find image for skin '" + skin + "' (path is '" + imagePath + "')");
    }
  }
  
  private void loadSkin() throws IOException {
    for (var line: game.field()) {
      for (Environnement env: line) {
        if (env != null) {
          loadSkin(env.skin());          
        }
      }
    }
    
    for (DroppedItem item: game.items()) {
      loadSkin(item.skin());
    }
    for (Mob mob: game.mobs()) {
      loadSkin(mob.skin());
    }
    loadSkin(game.player().skin());
  }

  class Area {

    void clearWindow(ApplicationContext context) {
      context.renderFrame(graphics -> {
        graphics.setColor(Color.BLACK);
        graphics.fill(new  Rectangle2D.Float(0, 0, windowWidth, windowHeight));
      });
    }

    void drawImage(ApplicationContext context, Position pos, String skin) {
      context.renderFrame(graphics -> {
        graphics.drawImage(skinMap.get(skin), (int) pos.x()*IMAGESIZE, (int) pos.y()*IMAGESIZE, null);
        graphics.dispose();
      });
    }
    
    void drawEnvironnement(ApplicationContext context, Environnement env) {
      drawImage(context, env.pos(), env.skin());
    }

    void drawPlayer(ApplicationContext context) {
      drawImage(context, game.player().pos(), game.player().skin());
    }
    
    void drawMap(ApplicationContext context) {
      for (var line: game.field()) {
        for (Environnement env: line) {
          if (env != null) {
            drawEnvironnement(context, env);            
          }
        }
      }
    }
    
  }

  @SuppressWarnings("incomplete-switch")
  public boolean controller(ApplicationContext context, Player player) {
    Event event = context.pollOrWaitEvent(10);
    if (event == null) {  // no event
      return false;
    }
    Action action = event.getAction();
    KeyboardKey key = event.getKey();

    if (action == Action.KEY_PRESSED) { // || action == Action.KEY_RELEASED) {
      switch (key) {
      case KeyboardKey.UP, KeyboardKey.Z -> player.pos().addY(-1);
      case KeyboardKey.RIGHT, KeyboardKey.D -> player.pos().addX(1);
      case KeyboardKey.DOWN, KeyboardKey.S -> player.pos().addY(1);
      case KeyboardKey.LEFT, KeyboardKey.Q -> player.pos().addX(-1);
      }
      
      if (key == KeyboardKey.UNDEFINED) {
        return true;
      }
    }
    return false;
  }

  public void play() {
    Application.run(Color.BLACK, context -> {
      // get the size of the screen
      ScreenInfo screenInfo = context.getScreenInfo();
      windowWidth = (int) screenInfo.getWidth();
      windowHeight = (int) screenInfo.getHeight();
      
      Area area = new Area();

      while (!controller(context, game.player())) {
        area.clearWindow(context);
        area.drawMap(context);
        area.drawPlayer(context);
      }
      
      
      context.exit(0);
    });
  }
}
