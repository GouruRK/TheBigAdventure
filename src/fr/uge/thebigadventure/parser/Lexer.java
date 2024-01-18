package fr.uge.thebigadventure.parser;

import java.util.List;
import java.util.Objects;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Give tokens to the parser based on their regular expression
 */
public class Lexer {
  
  //------- Constants -------
  
  /**
   * List of tokens to parsed
   */
  private static final List<Token> TOKENS = List.of(Token.values());
  
  /**
   * Use patterns to detect tokens from an input
   */
  private static final Pattern PATTERN = Pattern.compile(
      TOKENS.stream()
          .map(token -> "(" + token.regex() + ")")
          .collect(Collectors.joining("|")));

  //------- Fields -------
  
  /**
   * Text to parsed
   */
  private final String text;
  
  /**
   * Math tokens to text
   */
  private final Matcher matcher;
  
  /**
   * Stack of result
   */
  private final Stack<Result> stack;
  
  /**
   * Last returned Result
   */
  private Result last;
  
  /**
   * current line number
   */
  private int line;
  
  //------- Constructor -------
  
  public Lexer(String text) {
    this.text = Objects.requireNonNull(text);
    this.matcher = PATTERN.matcher(text);
    this.stack = new Stack<Result>();;
    this.line = 1;
  }
  
  //------- Getter -------
  
  /**
   * Get last returned result
   * @return
   */
  public Result last() {
    return last;
  }

  /**
   * Check if there are more tokens to be returned
   * @return
   */
  public boolean hasNext() {
    if (!stack.isEmpty()) {
      return true;
    }
    Result next = nextResult();
    if (next != null) {
      stack.add(next);
      return true;
    }
    return false;
  }

  /**
   * Get next result with no tokens equals to NEWLINE
   * @return
   */
  public Result nextResult() {
    Result next = filteredNextResult();
    while (next != null && next.token() == Token.NEWLINE) {
      next = filteredNextResult();
      line++;
    }
    if (next == null) {
      return null;
    }
    last = next;
    return new Result(next.token(), next.content(), line);
  }
  
  /**
   * Get next result
   * @return
   */
  private Result filteredNextResult() {
    if (!stack.isEmpty()) {
      return stack.pop();
    }
    var matches = matcher.find();
    if (!matches) {
      return null; 
    }
    for (var group = 1; group <= matcher.groupCount(); group++) {   
      var start = matcher.start(group);
      if (start != -1) {
        var end = matcher.end(group);
        var content = text.substring(start, end);
        
        Result res = new Result(TOKENS.get(group - 1), content, line);
        if (res.token() == Token.QUOTE) {
          line += content.chars().mapToObj(obj -> (char)obj).filter(c -> c == '\n').count();
        }
        
        return res;
      }
    }
    throw new AssertionError();
  }
  
  /**
   * Add a result to the stack
   * @param res
   */
  public void addNext(Result res) {
    Objects.requireNonNull(res);
    stack.add(res);
  }
 
}
