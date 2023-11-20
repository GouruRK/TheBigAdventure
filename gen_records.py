SCENERY = {'PLANK', 'ROAD', 'LILY', 'REED', 'CLOUD', 'TRACK', 'LADDER', 'TILE',
           'VINE', 'FOLIAGE', 'ALGAE', 'FLOWER', 'SPROUT', 'GRASS'}
OBSTACLE = {'PILLAR', 'DOOR', 'FORT', 'TREE', 'FENCE', 'PIANO', 'GATE', 'BOMB',
            'TABLE', 'BOG', 'TOWER', 'SPIKE', 'BRICK', 'CHAIR', 'CLIFF', 'PIPE',
            'STATUE', 'LOCK', 'ROCK', 'HUSKS', 'SHELL', 'STUMP', 'BED', 'MONITOR',
            'HUSK', 'HOUSE', 'TREES', 'RUBBLE', 'SIGN', 'HEDGE', 'WALL'}
ITEM = {'DRUM', 'SUN', 'MIRROR', 'PAPER', 'TRUMPET', 'CUP', 'SCISSORS', 'ORB',
        'GUITAR', 'HIHAT', 'COG', 'VASE', 'SEED', 'GEM', 'STAR', 'PANTS',
        'CRYSTAL', 'CLOCK', 'FLAG', 'PLANET', 'ROSE', 'RING', 'LEAF', 'CASH',
        'BOOK', 'BOX', 'KEY', 'LAMP', 'SAX', 'SHIRT', 'MOON'}
FOOD = {'PUMPKIN', 'BANANA', 'LOVE', 'TURNIP', 'CAKE', 'EGG', 'FUNGUS', 'DRINK',
        'POTATO', 'PIZZA', 'FUNGI', 'DONUT', 'FRUIT', 'BURGER', 'BOTTLE',
        'CHEESE', 'BOBA'}
WEAPON = {'STICK', 'SHOVEL', 'SWORD', 'BOLT'}
MOB = {'BABA', 'SKULL', 'CRAB', 'LIZARD', 'FISH', 'BEE', 'KEKE', 'TURTLE', 'ME',
       'DOG', 'GHOST', 'FROG', 'IT', 'BADBAD', 'JELLY', 'CAT', 'MONSTER', 'WORM',
       'TEETH', 'JIJI', 'BAT', 'BIRD', 'BUG', 'FOFO', 'ROBOT', 'SNAIL', 'BUNNY'}

form = """package %s;

import java.util.Objects;

import util.placement.Position;

public record %s(Position pos) implements %s {
  
  public %s {
    Objects.requireNonNull(pos);
  }
  
  public String pathToImage() {
    return "./assets/img/%s/%s.png";
  }
  
}

"""

records = (
    # path,                           package,                  implements, image,      item collection,    form
    ("./src/element/block/scenery/",  "element.block.scenery",  "Scenery",  "scenery",  SCENERY),
    ("./src/element/block/obstacle/", "element.block.obstacle", "Obstacle", "obstacle", OBSTACLE),
    ("./src/element/item/items/",     "element.item.items",     "Items",    "item",     ITEM),
    ("./src/element/item/food/",      "element.item.food",      "Food",     "food",     FOOD),
    ("./src/element/item/weapon/",    "element.item.weapon",    "Weapon",   "item",     WEAPON),
    ("./src/element/mob/",            "element.mob",            "Mob",      "pnj",      MOB)
)

if __name__ == '__main__':
    for path, package, implements, image, collection in records:
        for item in collection:
            name = item.capitalize()
            image_name = item.lower()
            with open(path + name + ".java", "w") as file:
                file.write(form % (package, name, implements, name, image, image_name))
