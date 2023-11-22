"""Download and store image from 'https://babaiswiki.fandom.com'
"""
 
import re
import io
import requests
from selenium import webdriver
from bs4 import BeautifulSoup
from PIL import Image
from pathlib import Path

RAW_DECORATION = "ALGAE, CLOUD, FLOWER, FOLIAGE, GRASS, LADDER, LILY, PLANK, REED, ROAD, SPROUT, TILE, TRACK, VINE"
DECORATION = {r for r in RAW_DECORATION.split(', ')}

RAW_OBSTACLE = "BED, BOG, BOMB, BRICK, CHAIR, CLIFF, DOOR, FENCE, FORT, GATE, HEDGE, HOUSE, HUSK, HUSKS, LOCK, MONITOR, PIANO, PILLAR, PIPE, ROCK, RUBBLE, SHELL, SIGN, SPIKE, STATUE, STUMP, TABLE, TOWER, TREE, TREES, WALL"
OBSTACLE = {r for r in RAW_OBSTACLE.split(', ')}

RAW_PNJ = "BABA, BADBAD, BAT, BEE, BIRD, BUG, BUNNY, CAT, CRAB, DOG, FISH, FOFO, FROG, GHOST, IT, JELLY, JIJI, KEKE, LIZARD, ME, MONSTER, ROBOT, SNAIL, SKULL, TEETH, TURTLE, WORM"
PNJ = {r for r in RAW_PNJ.split(', ')}

RAW_ITEMS = "BOOK, BOLT, BOX, CASH, CLOCK, COG, CRYSTAL, CUP, DRUM, FLAG, GEM, GUITAR, HIHAT, KEY, LAMP, LEAF, MIRROR, MOON, ORB, PANTS, PAPER, PLANET, RING, ROSE, SAX, SCISSORS, SEED, SHIRT, SHOVEL, STAR, STICK, SUN, SWORD, TRUMPET, VASE"
ITEMS = {r for r in RAW_ITEMS.split(', ')}

RAW_FOOD = "BANANA, BOBA, BOTTLE, BURGER, CAKE, CHEESE, DONUT, DRINK, EGG, FRUIT, FUNGUS, FUNGI, LOVE, PIZZA, POTATO, PUMPKIN, TURNIP"
FOOD = {r for r in RAW_FOOD.split(', ')}

INTERMITTENTS = {"BUBBLE", "DUST"}

DESTINATION = {
    "scenery": DECORATION,
    "obstacle": OBSTACLE,
    "item": ITEMS,
    "food": FOOD,
    "npc": PNJ,
    "effect": INTERMITTENTS
}

def det_folder(url: str) -> tuple[str, str]:
    for folder, collection in DESTINATION.items():
        for item in collection:
            if item in url:
                return folder, item.lower()
    return "other", url.split('/')[7][:-6].lower()

def extract_image_url(content: str) -> list[str]:
    soup = BeautifulSoup(content, features="html.parser")
    urls = []
    for img in soup.findAll('img'):
        res = re.findall('data-src="([^"]*)"', img.__repr__())
        if res and "Text_" not in res[0]:
            urls.append(res[0])
    return urls


def download_images(urls: list[str]) -> None:
    for url in urls:
        image_content = requests.get(url).content
        image_file = io.BytesIO(image_content)
        image = Image.open(image_file).convert("RGB")
        dest_folder, item_name = det_folder(url)
        print(f"Downloading {item_name}")
        file_path = Path(f"./images/{dest_folder}/", item_name + ".png")
        image.save(file_path, "PNG", quality=80)

if __name__ == '__main__':
    driver = webdriver.Chrome()
    driver.get("https://babaiswiki.fandom.com/wiki/Category:Nouns")

    content = driver.page_source
        
    urls = extract_image_url(content)
    download_images(urls)
    
    print(f"\n{len(urls)} images downloaded")
