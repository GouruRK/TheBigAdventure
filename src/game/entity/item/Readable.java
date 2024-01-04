package game.entity.item;

import java.util.List;
import java.util.Objects;

import game.GameObjectID;

public record Readable(String skin, String name, List<String> text) implements Item {

  //------- Constructors -------
  
  public Readable {
    Objects.requireNonNull(skin);
  }
  
  public Readable(String skin, String name) {
    this(skin, name, null);
  }
  
  // ------- Getter -------
  
  public GameObjectID id() {
    return GameObjectID.READABLE;
  }
  
  // ------- Other -------
  
  @Override
  public boolean equals(Object obj) {
    return obj instanceof Readable read
        && read.skin().equals(skin)
        && (read.text() == null ? read.text() == text: read.text().equals(text))
        && (read.name() == null ? read.name() == name: read.text().equals(name))
        && read.hashCode() == hashCode();
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(skin, text, name);
  }
}
