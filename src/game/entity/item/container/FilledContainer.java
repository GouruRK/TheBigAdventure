package game.entity.item.container;

import java.util.Objects;

import game.environnement.Environnement;

public record FilledContainer(String skin, String name, Environnement contents) implements Container {

  @Override
  public boolean isEmpty() {
    return true;
  }
  
  public FilledContainer {
    Objects.requireNonNull(skin);
    Objects.requireNonNull(name);
    Objects.requireNonNull(contents);
  }
  
}
