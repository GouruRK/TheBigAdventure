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
  
  public void addX(double x) {
    this.x += x;
  }
  
  public void addX(int x) {
    this.x += (double)x;
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
  
  public void addY(double y) {
    this.y += y;
  }
  
  public void addY(int y) {
    this.y += (double)y;
  }
  
  @Override
  public String toString() {
    return "Position(x: " + x + ", y: " + y + ")";
  }
  
}
