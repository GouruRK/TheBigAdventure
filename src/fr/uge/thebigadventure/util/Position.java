package fr.uge.thebigadventure.util;

import java.util.List;
import java.util.Objects;

/**
 * Represet a point with two coordinates
 */
public class Position {

  //------- Fields -------
  
  /**
   * Abscissa
   */
  private double x;
  
  /**
   * Ordinate
   */
  private double y;
  
  //------- Constructors -------
  
  public Position(double x, double y) {
    this.x = x;
    this.y = y;
  }
  
  public Position(int x, int y) {
    this.x = (double)x;
    this.y = (double)y;
  }
  
  //------- Getters -------
  
  /**
   * Get abscissa
   * @return
   */
  public double x() {
    return x;
  }
  
  /**
   * Get ordinate
   * @return
   */
  public double y() {
    return y;
  }

  //------- Setters -------
  
  /**
   * Set a new floating abscissa
   * @param x
   */
  public void x(double x) {
    this.x = x;
  }
  
  /**
   * Set a new integer abscissa
   * @param x
   */
  public void x(int x) {
    this.x = (int)x;
  }
  
  /**
   * Set a new floating ordinate
   * @param y
   */
  public void y(double y) {
    this.y = y;
  }
  
  /**
   * Set a new integer ordinate
   * @param y
   */
  public void y(int y) {
    this.y = (double)y;
  }

  //------- Modifiers -------
  
  /**
   * Add a floating abscissa to the current abscissa and return a new position
   * with the new coordinates
   * @param x
   * @return
   */
  public Position addX(double x) {
    return new Position(this.x + x, y);
  }
  
  /**
   * Add a integer abscissa to the current abscissa and return a new position
   * with the new coordinates
   * @param x
   * @return
   */
  public Position addX(int x) {
  	return new Position(this.x + x, y);
  }
  
  /**
   * Add a floating abscissa to the current abscissa
   * @param x
   */
  public void addLocalX(double x) {
    this.x += x;
  }
  
  /**
   * Add a floating abscissa to the current abscissa
   * @param x
   */
  public void addLocalX(int x) {
    this.x += x;
  }

  /**
   * Add a floating ordinate to the current ordinate and return a new position
   * with the new coordinates
   * @param y
   */
  public Position addY(double y) {
  	return new Position(x,this.y + y);
  }
  
  /**
   * Add a integer ordinate to the current ordinate and return a new position
   * with the new coordinates
   * @param y
   * @return
   */
  public Position addY(int y) {
  	return new Position(x,this.y + y);
  }
  
  /**
   * Add a floating ordinate to the current ordinate
   * @param y
   */
  public void addLocalY(double y) {
    this.y += y;
  }
  
  /**
   * Add a integer ordinate to the current ordinate
   * @param y
   */
  public void addLocalY(int y) {
    this.y += y;
  }
  
  /**
   * Get a new position when going one step to the given direction
   * @param dir
   * @return
   */
  public Position computeDirection(Direction dir) {
    return facingDirection(dir);
  }
  
  /**
   * Get a new position when going one step to the given direction
   * @param dir
   * @return
   */
  public Position facingDirection(Direction dir) {
    Objects.requireNonNull(dir);
    
    return switch (dir) {
      case NORTH -> addY(-1);
      case WEST -> addX(-1);
      case SOUTH -> addY(1);
      case EAST -> addX(1);
      default -> null;    
    };
  }
  
  /**
   * Get a list of positions around one current position
   * @return
   */
  public List<Position> getAround() {
    return List.<Position>of(facingDirection(Direction.NORTH),
                             facingDirection(Direction.WEST),
                             facingDirection(Direction.SOUTH),
                             facingDirection(Direction.EAST));
  }
  
  //------- Others -------
  
  @Override
  public String toString() {
    return "Position(x: " + x + ", y: " + y + ")";
  }
  
  @Override
  public boolean equals(Object obj) {
      return obj instanceof Position pos
                  && ((int)x == (int)pos.x()) && ((int)y == (int)pos.y());
  }
  
}
