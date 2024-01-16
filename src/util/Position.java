package util;

import java.util.List;
import java.util.Objects;

public class Position {

  private double x;
  private double y;
  
  public Position(double x, double y) {
    this.x = x;
    this.y = y;
  }
  
  public Position(int x, int y) {
    this.x = (double)x;
    this.y = (double)y;
  }
  
  
  public double x() {
    return x;
  }
  
  public void x(double x) {
    this.x = x;
  }
  
  public void x(int x) {
    this.x = (int)x;
  }
  
  public Position addX(double x) {
    return new Position(this.x + x, y);
  }
  
  public Position addX(int x) {
  	return new Position(this.x + x, y);
  }
  
  public void addLocalX(double x) {
    this.x += x;
  }
  
  public void addLocalX(int x) {
    this.x += x;
  }
  
  public double y() {
    return y;
  }

  public void y(double y) {
    this.y = y;
  }
  
  public void y(int y) {
    this.y = (double)y;
  }
  
  public Position addY(double y) {
  	return new Position(x,this.y + y);
  }
  
  public Position addY(int y) {
  	return new Position(x,this.y + y);
  }
  
  public void addLocalY(double y) {
    this.x += x;
  }
  
  public void addLocalY(int y) {
    this.x += y;
  }
  
  public Position computeDirection(Direction dir, double step) {
    Objects.requireNonNull(dir);
    return switch (dir) {
      case NORTH -> addY(-step);
      case WEST -> addX(-step);
      case SOUTH -> addY(step);
      case EAST -> addX(step);
      default -> null;    
    };
  }
  
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
  
  public List<Position> getAround() {
    return List.<Position>of(facingDirection(Direction.NORTH),
                             facingDirection(Direction.WEST),
                             facingDirection(Direction.SOUTH),
                             facingDirection(Direction.EAST));
  }
  
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
