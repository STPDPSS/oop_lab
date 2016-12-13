package functions;

import java.io.Serializable;

public class ArrayTabulatedFunction implements TabulatedFunction, Serializable {
  static private final double EPS = 1e-6;
  private double leftX, rightX;
  private int pointsCount;
  private FunctionPoint[] points;

  public ArrayTabulatedFunction(ArrayTabulatedFunction f) {
    this.leftX = f.getLeftDomainBorder();
    this.rightX = f.getRightDomainBorder();
    this.pointsCount = f.getPointsCount();
    for (int i = 0; i < pointsCount; i++) {
      points[i] = new FunctionPoint(f.getPoint(i));
    }
  }

  public ArrayTabulatedFunction(FunctionPoint[] points) {
    if (points.length < 2) {
      throw new IllegalArgumentException();
    }
    pointsCount = points.length;
    this.points = new FunctionPoint[pointsCount];
    leftX = points[0].getX();
    rightX = points[pointsCount - 1].getX();
    for (int i = 0; i < pointsCount; i++) {
      this.points[i] = points[i];
      if (i > 0 && points[i].getX() <= points[i - 1].getX()) {
        throw new IllegalArgumentException();
      }
    }
  }

  public ArrayTabulatedFunction(double leftX, double rightX, int pointsCount) {
    if (leftX >= rightX || pointsCount < 2) {
      throw new IllegalArgumentException();
    }
    this.leftX = leftX;
    this.rightX = rightX;
    this.pointsCount = pointsCount;
    double deltaX = (rightX - leftX) / (pointsCount - 1);
    this.points = new FunctionPoint[pointsCount * 4];
    for (int i = 0; i < pointsCount; i++) {
      points[i] = new FunctionPoint(leftX);
      leftX += deltaX;
    }
  }

  public ArrayTabulatedFunction(double leftX, double rightX, double[] values) {
    if (leftX >= rightX || values.length < 2) {
      throw new IllegalArgumentException();
    }
    this.leftX = leftX;
    this.rightX = rightX;
    this.pointsCount = values.length;
    double deltaX = (rightX - leftX) / (pointsCount - 1);
    this.points = new FunctionPoint[pointsCount * 4];
    for (int i = 0; i < pointsCount; i++) {
      points[i] = new FunctionPoint(leftX, values[i]);
      leftX += deltaX;
    }
  }

  @Override
  public double getLeftDomainBorder() {
    return this.leftX;
  }

  @Override
  public double getRightDomainBorder() {
    return this.rightX;
  }

  @Override
  public double getFunctionValue(double x) {
    if (x < leftX || x > rightX) {
      throw new IllegalArgumentException();
    }
    return getY(x);
  }

  private double getY(double valueX) {
    if (Math.abs(valueX - points[0].getX()) < EPS) {
      return points[0].getY();
    }
    for (int i = 1; i < pointsCount; i++) {
      if (Math.abs(valueX - points[i].getX()) < EPS) {
        return points[i].getY();
      }
      if (points[i - 1].getX() < valueX && valueX < points[i].getX()) {
        double dy = points[i].getY() - points[i - 1].getY();
        double dx = points[i].getX() - points[i - 1].getX();
        double dydx = dy / dx;
        return points[i - 1].getY() + (valueX - points[i - 1].getX()) * dydx;
      }
    }
    return Double.NaN;
  }

  @Override
  public int getPointsCount() {
    return pointsCount;
  }

  @Override
  public FunctionPoint getPoint(int index) {
    checkIndex(index);
    return new FunctionPoint(points[index]);
  }

  @Override
  public void setPoint(int index, FunctionPoint point) {
    checkIndex(index);
    if (point.getX() < leftX || rightX < point.getX()) {
      throw new InappropriateFunctionPointException(point.getX());
    }
    points[index] = new FunctionPoint(point);
  }

  @Override
  public void removePoint(int index) {
    checkIndex(index);
    if (pointsCount < 3) {
      throw new IllegalStateException();
    }
    points[index] = null;
    shiftToLeft(index + 1);
    pointsCount--;
  }

  private void shiftToLeft(int index) {
    for (int i = index; i < pointsCount; i++) {
      points[i - 1] = points[i];
    }
    points[pointsCount - 1] = null;
  }

  @Override
  public void addPoint(FunctionPoint point) {
    if (pointsCount == points.length - 2) {
      resize();
    }
    for (int i = 0; i < pointsCount; i++) {
      if (Math.abs(point.getX() - points[i].getX()) < EPS) {
        throw new InappropriateFunctionPointException(point.getX());
      }
    }
    for (int i = 1; i < pointsCount; i++) {
      if (points[i - 1].getX() < point.getX() && point.getX() < points[i].getX()) {
        shiftToRight(i);
        points[i] = point;
      }
    }
    pointsCount++;
    leftX = Math.min(leftX, point.getX());
    rightX = Math.max(rightX, point.getX());
  }

  private void resize() {
    FunctionPoint[] newPoints = new FunctionPoint[points.length * 4];
    System.arraycopy(newPoints, 0, points, 0, pointsCount);
    points = newPoints;
  }

  private void shiftToRight(int index) {
    for (int i = pointsCount - 1; i >= index; i--) {
      points[i + 1] = points[i];
      points[i] = null;
    }
  }

  @Override
  public void setPointX(int index, double x) {
    checkIndex(index);
    FunctionPoint point = new FunctionPoint(x, points[index].getY());
    setPoint(index, point);
  }

  @Override
  public void setPointY(int index, double y) {
    checkIndex(index);
    FunctionPoint point = new FunctionPoint(points[index].getX(), y);
    setPoint(index, point);
  }

  @Override
  public double getPointX(int index) {
    checkIndex(index);
    return points[index].getX();
  }

  @Override
  public double getPointY(int index) {
    checkIndex(index);
    return points[index].getY();
  }

  private void checkIndex(int index) {
    if (index < 0 || index >= pointsCount) {
      throw new FunctionPointIndexOutOfBoundsException(index);
    }
  }

  @Override
  public Object clone() {
    return new ArrayTabulatedFunction(this);
  }

  @Override
  public int hashCode() {
    int hash = 0;
    for (int i = 0; i < pointsCount; i++) {
      hash = hash * 31 + points[i].hashCode();
    }
    hash = hash * 31 + Integer.hashCode(pointsCount);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof TabulatedFunction)) {
      return false;
    }
    if (obj instanceof ArrayTabulatedFunction) {
      ArrayTabulatedFunction o = (ArrayTabulatedFunction) obj;
      return o.points == this.points;
    } else {
      TabulatedFunction o = (TabulatedFunction) obj;
      if (o.getPointsCount() != this.getPointsCount()) {
        return false;
      }
      for (int i = 0; i < this.getPointsCount(); i++) {
        if (!o.getPoint(i).equals(this.getPoint(i))) {
          return false;
        }
      }
      return true;
    }
  }

  @Override
  public String toString() {
    String result = "{";
    for (int i = 0; i < pointsCount; i++) {
      result += points[i].toString();
      if (i < pointsCount - 1) {
        result += ", ";
      }
    }
    result += "}";
    return result;
  }
}
