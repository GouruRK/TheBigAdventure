package parser.commandline;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

public class Arguments {

  private static Path level = null;
  private static boolean dryRun = false;
  private static boolean validate = false;
  
  public static boolean hasLevel() {
    return level != null;
  }
  
  public static boolean dryRun() {
    return dryRun;
  }
  
  public static boolean validate() {
    return validate;
  }
  
  public static Path level() {
    return level;
  }
  
  public static void parseArguments(String[] args) throws CommandLineException {
    Objects.requireNonNull(args);
    parseArguments(Arrays.stream(args).iterator());
    if (level == null) {
      throw new CommandLineException("No map specified");
    }
  }
  
  private static void parseArguments(Iterator<String> args) throws CommandLineException {
    String argument;
    while (args.hasNext()) {
      switch ((argument = args.next())) {
        case "--level" -> parseLevel(args);
        case "--validate" -> validate = true;
        case "--dry-run" -> dryRun = true;
        default -> throw new CommandLineException("Unknown option '" + argument + "'");
      }
    }
  }
  
  private static void parseLevel(Iterator<String> args) throws CommandLineException {
    if (!args.hasNext()) {
      throw new CommandLineException("Level argument require parameter");
    }
    level = Path.of(args.next());
  }
  
}
