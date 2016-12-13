package functions;

import java.io.Serializable;

public class LinkedListTabulatedFunction implements TabulatedFunction, Serializable{
  static private class FunctionNode implements Serializable {
    FunctionPoint point;
    FunctionNode previous, next;

    public FunctionNode() {
      previous = this;
      next = this;
    }

    public FunctionNode(FunctionPoint point) {
      this.point = new FunctionPoint(point);
      previous = this;
      next = this;
    }

    public FunctionNode(FunctionPoint point, FunctionNode previous) {
      this.point = point;
      this.previous = previous;
      this.next = previous.next;
      previous.next = this;
    }
  }

  private static final double EPS = 1e-6;
  private double leftX, rightX;
  private final FunctionNode head;
  private int pointsCount;

  public LinkedListTabulatedFunction(LinkedListTabulatedFunction f) {
    this.leftX = f.getLeftDomainBorder();
    this.rightX = f.getRightDomainBorder();
    this.pointsCount = f.getPointsCount();
    head = new FunctionNode();
    FunctionNode currentNode = head;
    for (int i = 0; i < pointsCount; i++) {
      currentNode.next = new FunctionNode(new FunctionPoint(f.getPoint(i)), currentNode);
      currentNode = currentNode.next;
    }
    currentNode.next = head;
    head.previous = currentNode;
  }

  public LinkedListTabulatedFunction(FunctionPoint[] points) {
    if (points.length < 2) {
      throw new IllegalArgumentException();
    }
    head = new FunctionNode();
    pointsCount = points.length;
    leftX = points[0].getX();
    rightX = points[pointsCount - 1].getX();
    FunctionNode currentNode = head;
    for (int i = 0; i < pointsCount; i++) {
      currentNode.next = new FunctionNode(new FunctionPoint(points[i]), currentNode);
      currentNode = currentNode.next;
      if (i > 0 && points[i].getX() <= points[i - 1].getX()) {
        throw new IllegalArgumentException();
      }
    }
    currentNode.next = head;
    head.previous = currentNode;
  }

  public LinkedListTabulatedFunction(double leftX, double rightX, int pointsCount) {
    if (leftX >= rightX || pointsCount < 2) {
      throw new IllegalArgumentException();
    }
    this.leftX = leftX;
    this.rightX = rightX;
    this.pointsCount = pointsCount;
    double deltaX = (rightX - leftX) / (pointsCount - 1);
    head = new FunctionNode();
    FunctionNode currentNode = head;
    for (int i = 0; i < pointsCount; i++) {
      currentNode.next = new FunctionNode(new FunctionPoint(leftX), currentNode);
      currentNode = currentNode.next;
      leftX += deltaX;
    }
    currentNode.next = head;
    head.previous = currentNode;
  }

  public LinkedListTabulatedFunction(double leftX, double rightX, double[] values) {
    if (leftX >= rightX || values.length < 2) {
      throw new IllegalArgumentException();
    }
    this.leftX = leftX;
    this.rightX = rightX;
    this.pointsCount = values.length;
    double deltaX = (rightX - leftX) / (pointsCount - 1);
    head = new FunctionNode();
    FunctionNode currentNode = head;
    for (double value : values) {
      currentNode.next = new FunctionNode(new FunctionPoint(leftX, value), currentNode);
      currentNode = currentNode.next;
      leftX += deltaX;
    }
    currentNode.next = head;
    head.previous = currentNode;
  }


  private FunctionNode getNodeByIndex(int index) {
    FunctionNode currentNode = head;
    for (int currentIndex = -1; currentIndex != index; currentIndex++) {
      currentNode = currentNode.next;
    }
    return currentNode;
  }

  private FunctionNode addNodeToTail() {
    head.previous.next = new FunctionNode(new FunctionPoint(), head.previous);
    head.previous = head.previous.next;
    pointsCount++;
    return head.previous;
  }

  private FunctionNode addNodeByIndex(int index) {
    FunctionNode previousNode = getNodeByIndex(index - 1);
    FunctionNode nextNode = previousNode.next;
    previousNode.next = new FunctionNode(new FunctionPoint(), previousNode);
    previousNode.next.next = nextNode;
    nextNode.previous = previousNode.next;
    pointsCount++;
    return previousNode.next;
  }

  private FunctionNode removeNodeByIndex(int index) {
    FunctionNode currentNode = getNodeByIndex(index);
    FunctionNode previousNode = currentNode.previous;
    FunctionNode nextNode = currentNode.next;
    previousNode.next = nextNode;
    nextNode.previous = previousNode;
    pointsCount--;
    return currentNode;
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
    FunctionNode currentNode = head.next;
    if (Math.abs(valueX - currentNode.point.getX()) < EPS) {
      return currentNode.point.getY();
    }
    currentNode = currentNode.next;
    for (int i = 1; i < pointsCount; i++, currentNode = currentNode.next) {
      if (Math.abs(valueX - currentNode.point.getX()) < EPS) {
        return currentNode.point.getY();
      }
      if (currentNode.previous.point.getX() < valueX && valueX < currentNode.point.getX()) {
        double dy = currentNode.point.getY() - currentNode.previous.point.getY();
        double dx = currentNode.point.getX() - currentNode.previous.point.getX();
        double a = dy / dx;
        return currentNode.previous.point.getY() + (valueX - currentNode.previous.point.getX()) * a;
      }
    }
    throw new IllegalArgumentException();
  }

  @Override
  public int getPointsCount() {
    return pointsCount;
  }

  @Override
  public FunctionPoint getPoint(int index) {
    checkIndex(index);
    return new FunctionPoint(getNodeByIndex(index).point);
  }

  @Override
  public void setPoint(int index, FunctionPoint point) {
    checkIndex(index);
    if (point.getX() < leftX || rightX < point.getX()) {
      throw new InappropriateFunctionPointException(point.getX());
    }
    FunctionNode currentNode = getNodeByIndex(index);
    if (currentNode.previous != head && currentNode.previous.point.getX() > point.getX()) {
      throw new InappropriateFunctionPointException(point.getX());
    }
    if (currentNode.next != head && currentNode.next.point.getX() < point.getX()) {
      throw new InappropriateFunctionPointException(point.getX());
    }
    currentNode.point = new FunctionPoint(point);
  }

  @Override
  public void removePoint(int index) {
    checkIndex(index);
    if (pointsCount < 3) {
      throw new IllegalStateException();
    }
    removeNodeByIndex(index);
  }

  @Override
  public void addPoint(FunctionPoint point) {
    FunctionNode currentNode = head.next;
    for (int i = 0; i < pointsCount; i++, currentNode = currentNode.next) {
      if (Math.abs(point.getX() - currentNode.point.getX()) < EPS) {
        throw new InappropriateFunctionPointException(point.getX());
      }
    }
    currentNode = head.next.next;
    for (int i = 1; i < pointsCount; i++, currentNode = currentNode.next) {
      if (currentNode.previous.point.getX() < point.getX() && point.getX() < currentNode.point.getX()) {
        addNodeByIndex(i).point = point;
      }
    }
    leftX = Math.min(leftX, point.getX());
    rightX = Math.max(rightX, point.getX());
  }

  @Override
  public void setPointX(int index, double x) {
    checkIndex(index);
    FunctionPoint point = new FunctionPoint(x, getPoint(index).getY());
    setPoint(index, point);
  }

  @Override
  public void setPointY(int index, double y) {
    checkIndex(index);
    FunctionPoint point = new FunctionPoint(getPoint(index).getX(), y);
    setPoint(index, point);
  }

  @Override
  public double getPointX(int index) {
    checkIndex(index);
    return getPoint(index).getX();
  }

  @Override
  public double getPointY(int index) {
    checkIndex(index);
    return getPoint(index).getY();
  }

  private void checkIndex(int index) {
    if (index < 0 || index >= pointsCount) {
      throw new FunctionPointIndexOutOfBoundsException(index);
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof TabulatedFunction)) {
      return false;
    }
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

  @Override
  public int hashCode() {
    int hash = 0;
    FunctionNode currentNode = head.next;
    while (currentNode != head) {
      hash = hash * 31 + currentNode.point.hashCode();
      currentNode = currentNode.next;
    }
    hash = hash * 31 + Integer.hashCode(pointsCount);
    return hash;
  }

  @Override
  public Object clone() {
    return new LinkedListTabulatedFunction(this);
  }

  @Override
  public String toString() {
    String result = "{";
    FunctionNode currentNode = head.next;
    while (currentNode.next != head) {
      result += currentNode.point.toString() + ", ";
      currentNode = currentNode.next;
    }
    result += currentNode.point.toString();
    result += "}";
    return result;
  }
}
