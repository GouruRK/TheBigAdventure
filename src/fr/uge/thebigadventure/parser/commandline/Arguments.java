package fr.uge.thebigadventure.parser.commandline;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

/**
 * Parse command line arguments
 * All methods are static, because online arguments 
 * are supposed to be parsed only one time
 */
public class Arguments {

  //------- Fields -------
  
  /**
   * Map path
   */
  private static Path level = null;
  
  /**
   * Check if the game is run as a dry run
   */
  private static boolean dryRun = false;
  
  /**
   * Check if the given maps only needs to be validated by the parser
   */
  private static boolean validate = false;
  
  /**
   * Store if help has been ask by the user
   */
  private static boolean help = false;

  //------- Getter -------
  
  /**
   * Check if after parsing, there is a level
   * @return
   */
  public static boolean hasLevel() {
    return level != null;
  }
  
  /**
   * Get intel if the programm run the map as dry
   * @return
   */
  public static boolean dryRun() {
    return dryRun;
  }
  
  /**
   * Get intel if the map will not be played but only validated
   * @return
   */
  public static boolean validate() {
    return validate;
  }
  
  /**
   * Get intel if help has been ask
   */
  public static boolean help() {
    return help;
  }

   /**
   * Get path of the map to play
   * @return
   */
  public static Path level() {
    return level;
  }

  //------- Methods -------
  
  /**
   * Parse arguments from the command line
   * @param args
   * @throws CommandLineException
   */
  public static void parseArguments(String[] args) throws CommandLineException {
    Objects.requireNonNull(args);
    parseArguments(Arrays.stream(args).iterator());
    if (level == null && !help) {
      throw new CommandLineException("No map specified");
    }
  }
  
  /**
   * Parse arguments from the command lines using iterator
   * @param args
   * @throws CommandLineException
   */
  private static void parseArguments(Iterator<String> args) throws CommandLineException {
    String argument;
    while (args.hasNext()) {
      switch ((argument = args.next())) {
        case "--help", "-h" -> help = true;
        case "--level", "-lvl" -> parseLevel(args);
        case "--validate" -> validate = true;
        case "--dry-run" -> dryRun = true;
        default -> throw new CommandLineException("Unknown option '" + argument + "'");
      }
    }
  }
  
  /**
   * Get help message
   */
  public static String getHelp() {
    return "thebigadventure.jar --level FILE [OPTIONS ...]\n\n"
           + "Options : \n"
           + "\t -lvl, --level\tmap to play. This argument is mandatory\n"
           + "\t --validate\tcheck if a map syntax is correct without playing it\n"
           + "\t --dry-run\tplay easy-mode with still mobs\n"
           + "\t -h, --help\tprint this message and exit\n";
  }

  /**
   * Parse level
   * @param args
   * @throws CommandLineException
   */
  private static void parseLevel(Iterator<String> args) throws CommandLineException {
    if (!args.hasNext()) {
      throw new CommandLineException("Level argument require parameter");
    }
    level = Path.of(args.next());
  }
  
}
