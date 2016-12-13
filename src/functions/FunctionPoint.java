package functions;

import java.io.Serializable;

public class FunctionPoint implements Serializable {
  private double x, y;

  public FunctionPoint(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public FunctionPoint(FunctionPoint point) {
    this.x = point.getX();
    this.y = point.getY();
  }

  public FunctionPoint() {
    this.x = 0;
    this.y = 0;
  }

  public FunctionPoint(double x) {
    this.x = x;
    this.y = 0;
  }


  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }


  public void setX(double x) {
    this.x = x;
  }

  public void setY(double y) {
    this.y = y;
  }

  @Override
  public String toString() {
    return "(" + String.format("%.2f", x) + "; " + String.format("%.2f", y) + ")";
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof FunctionPoint)) {
      return false;
    }
    FunctionPoint o = (FunctionPoint) obj;
    return this.x == o.x && this.y == o.y;
  }

  @Override
  public int hashCode() {
    return Double.hashCode(x) * 31 + Double.hashCode(y);
  }

  @Override
  protected Object clone() {
    return new FunctionPoint(this);
  }
}
