package fr.uge.thebigadventure.graphic.controller;

import java.util.Objects;

import fr.uge.thebigadventure.util.Direction;
import fr.uge.thebigadventure.util.Text;

/**
 * Text controller to handle player's interactions while on a text interface
 * Display a given text correctly, and change pages if its require
 */
public class TextController {

  //------- Constants -------  
  
  /**
   * Maximum of lines displayed at the same time
   */
  private final int maxLinePerPage = 10;
  
  //------- Fields -------
  
  /**
   * Give intel if the text interface is currently displayed
   */
  private boolean isTextInterfaceShow = false;
  
  /**
   * Number of pages to read
   */
  private int numberOfPages = 0;
  
  /**
   * Current lowest line index displayed
   */
  private int minLineIndex = 0;
  
  /**
   * Current greatest line index displayed
   */
  private int maxLineIndex;
  
  /**
   * Current page number
   */
  private int pageNumber = 0;
  
  /**
   * Current text displayed
   */
  private Text text = null;
  
  /***
   * Get current page number
   * @return
   */
  public int page() {
    return pageNumber;
  }
  
  //------- Getters -------
  
  /**
   * Get current lowest line index displayed
   * @return
   */
  public int minLineIndex() {
    return minLineIndex;
  }
  
  /**
   * Get current greatest line index displayed
   * @return
   */
  public int maxLineIndex() {
    return maxLineIndex;
  }
  
  /**
   * Get number of pages
   * @return
   */
  public int numberOfPages() {
    return numberOfPages;
  }
  
  /**
   * Get intel if the text interface is currently show
   * @return
   */
  public boolean isTextInterfaceShow() {
    return isTextInterfaceShow;
  }
  
  /**
   * Get minimum line per page
   * @return
   */
  public int getLinePerPage() {
    return Math.min(maxLinePerPage, text.numberOfLines());
  }
  
  /**
   * Get current text
   * @return
   */
  public Text text() {
    return text;
  }

  //------- Modifiers -------
  
  /**
   * Change current displayed text
   * @param text
   */
  public void setText(Text text) {
    Objects.requireNonNull(text);
    this.text = text;
    if (text.numberOfLines() < maxLinePerPage) {
      maxLineIndex = text.numberOfLines();
      numberOfPages = 0;
    } else {
      numberOfPages = text.numberOfLines() / maxLinePerPage;
      maxLineIndex = maxLinePerPage;
    }
    pageNumber = 0; 
  }
  
  /**
   * Toggle text interface
   */
  public void toggleIsTextInterfaceShow() {
    isTextInterfaceShow = !isTextInterfaceShow;
  }
  
  /**
   * Change current page
   * @param dir
   */
  public void changePage(Direction dir) {
    Objects.requireNonNull(dir);
    
    if (numberOfPages != 0) {
      if (dir == Direction.WEST && pageNumber != 0) {
        pageNumber--;
        minLineIndex -= maxLinePerPage;
        maxLineIndex = minLineIndex + maxLinePerPage;
      } else if (dir == Direction.EAST && pageNumber != numberOfPages) {
        pageNumber++;
        minLineIndex += maxLinePerPage;
        maxLineIndex = Math.min(maxLineIndex + maxLinePerPage, text.numberOfLines());
      }
    } 
  }
  
}
