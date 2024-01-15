package game.entity.item;

import java.util.Objects;

import game.GameObjectID;
import game.environment.Environment;

public class Bucket implements Item {

  // ------- Fields -------
  
  /**
   * Bucket skin
   */
  private final String skin;
  
  /**
   * Bucket name
   */
  private final String name;
  
  /**
   * Environment that the bucket contains
   */
  private Environment content;
  
  // ------- Constructors -------
  
  public Bucket(String skin, String name, Environment content) {
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
    
  //------- Getter -------
  
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
  
  /**
   * Get bucket content. Content can be null
   * @return
   */
  public Environment getContent() {
    return content;
  }

  @Override
  public GameObjectID id() {
    return GameObjectID.CONTAINER;
  }
  
  //------- Modifiers -------
  
  /**
   * Replace current bucket content by the given one
   * @param env
   */
  public void fillBucket(Environment env) {
    Objects.requireNonNull(env);
    this.content = env;
  }
  
  /**
   * Pour bucket content
   */
  public void pourBucket() {
    this.content = null;
  }
  
  //------- Other -------
  
  @Override
  public String toString() {
    return "Bucket[skin: " + skin
        + ", name: " + name
        + ", content: " + content
        + "]";
  }
  
}
