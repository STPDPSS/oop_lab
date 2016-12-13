package functions.meta;

import functions.Function;

public class Mult implements Function {

  private Function first;
  private Function second;

  public Mult(Function first, Function second) {
    if (Math.min(first.getRightDomainBorder(), second.getRightDomainBorder()) <
        Math.max(first.getLeftDomainBorder(), second.getLeftDomainBorder())) {
      throw new IllegalArgumentException();
    }
    this.first = first;
    this.second = second;
  }

  @Override
  public double getLeftDomainBorder() {
    return Math.max(first.getLeftDomainBorder(), second.getLeftDomainBorder());
  }

  @Override
  public double getRightDomainBorder() {
    return Math.min(first.getRightDomainBorder(), second.getRightDomainBorder());
  }

  @Override
  public double getFunctionValue(double x) {
    double firstValue = first.getFunctionValue(x);
    double secondValue = second.getFunctionValue(x);
    return firstValue * secondValue;
  }
}
