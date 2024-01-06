package graphic.controller;

import java.util.Objects;

import util.Direction;
import util.Text;

public class TextController {

  private final int maxLinePerPage = 10;
  private boolean isTextInterfaceShow = false;
  private int numberOfPages = 0;
  private int minLineIndex = 0;
  private int pageNumber = 0;
  private int maxLineIndex;
  private Text text = null;
  
  public int page() {
    return pageNumber;
  }
  
  public int minLineIndex() {
    return minLineIndex;
  }
  
  public int maxLineIndex() {
    return maxLineIndex;
  }
  
  public int numberOfPages() {
    return numberOfPages;
  }
  
  public boolean isTextInterfaceShow() {
    return isTextInterfaceShow;
  }
  
  public int getLinePerPage() {
    return Math.min(maxLinePerPage, text.numberOfLines());
  }
  
  public Text text() {
    return text;
  }
  
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
  
  public void toggleIsTextInterfaceShow() {
    isTextInterfaceShow = !isTextInterfaceShow;
  }
  
  public void changePage(Direction dir) {
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
