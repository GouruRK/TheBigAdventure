package graph;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import fr.umlv.zen5.Application;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.Event.Action;
import fr.umlv.zen5.KeyboardKey;
import fr.umlv.zen5.ScreenInfo;
import game.Game;
import game.entity.mob.Player;
import util.Position;

public class Window {

  private final int IMAGESIZE = 24;
  private final int OFFSET = IMAGESIZE / 2;

  private final Game game;
  private int windowWidth;
  private int windowHeight;

  public Window(Game game) {
    this.game = game;
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

        clearWindow(context);

        try{
          BufferedImage img = ImageIO.read(new File("images/" + skin.toLowerCase() + ".png"));
          graphics.drawImage(img, (int) pos.x()*IMAGESIZE, (int) pos.y()*IMAGESIZE, null);
          graphics.dispose();
        } catch (IOException e) {
          
        }
      });
    }

    void drawPlayer(ApplicationContext context, Player player) {
      drawImage(context, player.pos(), player.skin());
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
        area.drawPlayer(context, game.player());
      }
      context.exit(0);
    });
  }
}
