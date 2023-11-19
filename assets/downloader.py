"""Download and store image from 'https://babaiswiki.fandom.com'
"""
 
import re
import io
import requests
from selenium import webdriver
from bs4 import BeautifulSoup
from PIL import Image
from pathlib import Path

from items import DESTINATION

def det_folder(url: str) -> tuple[str, str]:
    for folder, collection in DESTINATION.items():
        for item in collection:
            if item in url:
                return folder, item.lower()
    return "other", url.split('/')[7][:-6].lower()

def extract_image_url(soup):
    urls = []
    for img in soup.findAll('img'):
        res = re.findall('data-src="([^"]*)"', img.__repr__())
        if res and "Text_" not in res[0]:
            urls.append(res[0])
    return urls


def download_images(urls):
    for url in urls:
        image_content = requests.get(url).content
        image_file = io.BytesIO(image_content)
        image = Image.open(image_file).convert("RGB")
        dest_folder, item_name = det_folder(url)
        print(f"Treating {item_name}")
        file_path = Path(f"./img/{dest_folder}/", item_name + ".png")
        image.save(file_path, "PNG", quality=80)

if __name__ == '__main__':
    driver = webdriver.Chrome()
    driver.get("https://babaiswiki.fandom.com/wiki/Category:Nouns")

    content = driver.page_source
    soup = BeautifulSoup(content, features="html.parser")
    driver.quit()
    
    urls = extract_image_url(soup)
    download_images(urls)
    
    print(f"\nTreated {len(urls)} images")
