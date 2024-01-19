package fr.uge.thebigadventure.game.entity.item;

import java.util.Objects;

import fr.uge.thebigadventure.game.GameObjectID;
import fr.uge.thebigadventure.util.Text;

public record Readable(String skin, String name, Text text) implements Item {

  //------- Constructors -------
  
  public Readable {
    Objects.requireNonNull(skin);
  }
  
  public Readable(String skin, String name) {
    this(skin, name, null);
  }
    
  // ------- Getter -------
  
  /**
   * Check if item has text
   * @return
   */
  public boolean hasText() {
    return text != null;
  }
  
  @Override
  public GameObjectID id() {
    return GameObjectID.READABLE;
  }
}
