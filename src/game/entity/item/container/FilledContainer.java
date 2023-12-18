package game.entity.item.container;

import java.util.Objects;

import game.environnement.Environnement;

public record FilledContainer(String skin, Environnement contents, String name) implements Container {

  //------- Constructors -------
  
  public FilledContainer {
    Objects.requireNonNull(skin);
    Objects.requireNonNull(contents);
  }
  
  public FilledContainer(String skin, Environnement contents) {
    this(skin, contents, null);
  }
  
  //------- Getter -------
  
  @Override
  public boolean isEmpty() {
    return true;
  }
  
  
}
