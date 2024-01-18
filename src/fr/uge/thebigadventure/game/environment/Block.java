package fr.uge.thebigadventure.game.environment;

import java.util.Objects;

import fr.uge.thebigadventure.game.GameObjectID;
import fr.uge.thebigadventure.util.Position;

public record Block(String skin, Position pos, boolean standable) implements Environment {
  
  //------- Constructors -------
  
  public Block {
    Objects.requireNonNull(skin);
    Objects.requireNonNull(pos);
  }
  
  //------- Getter -------
  
  @Override
  public boolean standable() {
    return standable;
  }

  @Override
  public GameObjectID id() {
    return standable ? GameObjectID.SCENERY: GameObjectID.OBSTACLE;
  }

  //------- Other -------
  
  @Override
  public String toString() {
    return "Block[skin: " + skin
            + " pos: " + pos
            + " standable: " + standable
            + "]";
  }
  
}
