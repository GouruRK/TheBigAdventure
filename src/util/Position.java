package util;

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
  
  public Position computeDirection(Direction dir, double step) {
    return switch (dir) {
      case NORTH -> addY(-step);
      case WEST -> addX(-step);
      case SOUTH -> addY(step);
      case EAST -> addX(step);
      default -> null;    
    };
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
