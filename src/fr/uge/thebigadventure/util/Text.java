package fr.uge.thebigadventure.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Represent text objects that basicly consist in an array of lines
 * with some more features
 */
public class Text {

  //------- Constants -------
  
  /**
   * Maximum length of a line if the text need to be formated
   */
  public static final int LINELENGTH = 80;
  
  //------- Fields -------
  
  /**
   * List of lines
   */
  private final List<String> text;
  
  /**
   * Text constructor
   * @param text text to be formated
   * @param format tells if the text need to be formated
   */
  public Text(String text, boolean data) {
    Objects.requireNonNull(text);
    String newText = text.replaceAll("\\\"\\\"\\\"", "").stripIndent();
    if (data) {
      this.text = refactorText(newText);
    } else {
      this.text = setReadable(newText);
    }
  }
  
  /**
   * Text constructor which is not formated
   * @param text
   */
  public Text(String text) {
    this(text, false);
  }
  
  //------- Getter -------
  
  /**
   * Get text
   * @return
   */
  public List<String> text() {
    return text;
  }
  
  /**
   * Get longest line
   * @return
   */
  public String longestLine() {
    return text.stream().max(Comparator.comparingInt(String::length)).orElse("");
  }
  
  /**
   * Get longest line length
   * @return
   */
  public int longestLineLength() {
    return longestLine().length();
  }
  
  /**
   * Get number of lines the text has
   * @return
   */
  public int numberOfLines() {
    return text.size();
  }
  
  /**
   * Get a specific line based on its index
   * @param index
   * @return
   */
  public String get(int index) {
    return text.get(index);
  }
  
  //------- Modifiers -------
  
  /**
   * Refactor text by removing empty lines and '\n'
   * @param text
   * @return
   */
  private List<String> refactorText(String text) {
    String[] lines = text.split("\n");
        
    List<String> res = Arrays.stream(lines)
        .map(line -> line.replaceAll("\n", ""))
        .collect(Collectors.toList());
    
    res.removeIf(line -> line.isEmpty());
    return res;
  }
  
  /**
   * Set a text readable by cutting lines that are too long
   * @param text
   * @return
   */
  private List<String> setReadable(String text) {
    ArrayList<String> newText = new ArrayList<String>();
    String[] lines = text.split("\n");
    
    for (int i = 1; i < lines.length; i++) { // set i to 1 to prevent empty line at begining
      if (lines[i].length() < LINELENGTH) {
        newText.add(lines[i]);
      } else {
        newText.addAll(cutLine(lines[i]));
      }
    }
    return List.copyOf(newText);
  }
  
  /**
   * Cut too long lines
   * @param line
   * @return
   */
  private List<String> cutLine(String line) {
    ArrayList<String> newLine = new ArrayList<String>();
    String[] words = line.split(" ");
    StringBuilder builder = new StringBuilder();
    
    int wordIndex = 0;
    int currentLength = 0;
    while (wordIndex < words.length) {
      if (currentLength + words[wordIndex].length() < LINELENGTH) {
        builder.append(words[wordIndex]).append(" ");
        currentLength += words[wordIndex].length();
        wordIndex++;
      } else {
        newLine.add(builder.toString());
        builder = new StringBuilder();
        currentLength = 0;
      }
    }
    if (builder.length() != 0) {
      newLine.add(builder.toString());
    }
    return List.copyOf(newLine);
  }
  
  //------- Other -------
  
  @Override
  public boolean equals(Object obj) {
    return obj instanceof Text txt
        && numberOfLines() == txt.numberOfLines()
        && areTextEquals(txt)
        && hashCode() == txt.hashCode();
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(text);
  }
  
  /**
   * Check if two text have the same lines
   * @param text
   * @return
   */
  private boolean areTextEquals(Text text) {
    for (int i = 0; i < numberOfLines(); i++) {
      if (!text.get(i).equals(text.get(i))) {
        return false;
      }
    }
    return true;
  }
  
}
