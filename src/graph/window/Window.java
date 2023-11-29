package graph.window;

import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import fr.umlv.zen5.Application;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.Event.Action;
import fr.umlv.zen5.ScreenInfo;

public class Window {
  static class Area {
    private Ellipse2D.Float ellipse = new Ellipse2D.Float(0, 0, 0, 0);
    private Rectangle2D.Float rect = new Rectangle2D.Float(0, 0, 0, 0);
    
    
    void draw(ApplicationContext context, float x, float y, String name) {
      context.renderFrame(graphics -> {
        // hide the previous rectangle
        graphics.setColor(Color.BLACK);
        graphics.fill(rect);
        
        // show a new ellipse at the position of the pointer
        graphics.setColor(Color.BLUE);
        //ellipse = new Ellipse2D.Float(x - 20, y - 20, 40, 40);
        // rect = new Rectangle2D.Float(x - 10, y - 10, 20, 20);
        // graphics.fill(rect);
        
        // marche
        BufferedImage img = null;
        String file = "images/npc/" + name + ".png";
        try{
            img = ImageIO.read(new File(file));
        } catch (IOException e) {
        }
        graphics.drawImage(img,(int) x,(int) y, null);
        graphics.dispose();
      });
    }
  }
  
  public static void main(String[] args) {
    Application.run(Color.BLACK, context -> {
      
      // get the size of the screen
      ScreenInfo screenInfo = context.getScreenInfo();
      float width = screenInfo.getWidth();
      float height = screenInfo.getHeight();
      System.out.println("size of the screen (" + width + " x " + height + ")");
      
      // créer un background pour tout "clear"
      context.renderFrame(graphics -> {
        graphics.setColor(Color.BLACK);
        graphics.fill(new  Rectangle2D.Float(0, 0, width, height));
      });
      
      Area area = new Area();
      String player = "ghost";
      float rect_x = width/2;
      float rect_y = height/2;
      area.draw(context, rect_x, rect_y, player);
        
        
        for(;;) {
        Event event = context.pollOrWaitEvent(10);
        if (event == null) {  // no event
          continue;
        }
        Action action = event.getAction();
        Object key = event.getKey();
        if (action == Action.KEY_PRESSED) { // || action == Action.KEY_RELEASED) {
            if (key.toString().equals("UP") || key.toString().equals("Z")) {
                rect_y -= 12; // les images font 24x24
            System.out.println(key);
            }
            if (key.toString().equals("RIGHT") || key.toString().equals("D")) {
                rect_x += 12;
                System.out.println(key);
            }
            if (key.toString().equals("DOWN") || key.toString().equals("S")) {
                rect_y += 12;
                System.out.println(key);
            }
            if (key.toString().equals("LEFT") || key.toString().equals("Q")) {
                rect_x -= 12;
                System.out.println(key);
            }
            // VK_ESCAPE         = 0x1B;
            if (key.toString().equals("P")) {
                System.out.println(key);
                context.exit(0);
                return;
            }
            context.renderFrame(graphics -> {
            graphics.setColor(Color.BLACK);
            graphics.fill(new  Rectangle2D.Float(0, 0, width, height));
          });
            System.out.println(rect_x + " " + rect_y);
            area.draw(context, rect_x, rect_y, player);
            continue;
        }
        
        System.out.println(event);
        // Pint avec le clic souris
//        Point2D.Float location = event.getLocation();
//        area.draw(context, location.x, location.y, player);
      }
    });
  }
}
