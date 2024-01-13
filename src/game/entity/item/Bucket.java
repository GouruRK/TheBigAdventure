package game.entity.item;

import java.util.Objects;

import game.GameObjectID;
import game.environnement.Environnement;

public class Bucket implements Item {

  // ------- Fields -------
  
  private final String skin;
  private final String name;
  private Environnement content;
  
  // ------- Constructors -------
  
  public Bucket(String skin, String name, Environnement content) {
    Objects.requireNonNull(skin);
    this.skin = skin;
    this.name = name;
    this.content = content;
  }

  public Bucket(String skin, String name) {
    this(skin, name, null);
  }
  
  public Bucket(String skin) {
    this(skin, null, null);
  }
    
  // ------- Constructors -------
  
  @Override
  public String skin() {
    return skin;
  }
  
  @Override
  public String name() {
    return name;
  }
  
  public boolean isEmpty() {
    return content == null;
  }
  
  public Environnement getContent() {
    return content;
  }
  
  public void fillBucket(Environnement env) {
    Objects.requireNonNull(env);
    this.content = env;
  }
  
  public void pourBucket() {
    this.content = null;
  }
  
  @Override
  public GameObjectID id() {
    return GameObjectID.CONTAINER;
  }
  
  @Override
  public String toString() {
    return "Bucket[skin: " + skin
        + ", name: " + name
        + ", content: " + content
        + "]";
  }
  
}
