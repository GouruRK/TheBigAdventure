package game.entity.item.container;

import java.util.Objects;

import game.environnement.Environnement;

public record FilledContainer(String skin, Environnement contents, String name) implements Container {

  @Override
  public boolean isEmpty() {
    return true;
  }
  
  public FilledContainer {
    Objects.requireNonNull(skin);
    Objects.requireNonNull(contents);
  }
  
  public FilledContainer(String skin, Environnement contents) {
    this(skin, contents, null);
  }
  
}
