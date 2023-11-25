package parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Lexer {
  private static final List<Token> TOKENS = List.of(Token.values());
  private static final Pattern PATTERN = Pattern.compile(
      TOKENS.stream()
          .map(token -> "(" + token.regex + ")")
          .collect(Collectors.joining("|")));

  private final String text;
  private final Matcher matcher;
 
  private Result next = null;
  
  public Lexer(String text) {
    this.text = Objects.requireNonNull(text);
    this.matcher = PATTERN.matcher(text);
  }

  public boolean hasNext() {
    if (next != null) {
      return true;
    }
    next = nextResult();
    return next != null;
  }
  
  public Result nextResult() {
    if (next != null) {
      Result res = next;
      next = null;
      return res;
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
        return new Result(TOKENS.get(group - 1), content);
      }
    }
    throw new AssertionError();
  }

  public void addNext(Result res) {
    next = res;
  }
  
  public static void main(String[] args) throws IOException {
    var path = Path.of("map/default.map");
    var text = Files.readString(path);
    var lexer = new Lexer(text);
    Result result;
    while((result = lexer.nextResult()) != null) {
      System.out.println(result);
    }
  }
}
