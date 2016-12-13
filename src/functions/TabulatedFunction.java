package functions;

import java.io.Serializable;

public interface TabulatedFunction extends Function {
  int getPointsCount();

  void setPoint(int index, FunctionPoint point);
  FunctionPoint getPoint(int index);

  void addPoint(FunctionPoint point);
  void removePoint(int index);

  void setPointX(int index, double x);
  void setPointY(int index, double y);
  double getPointX(int index);
  double getPointY(int index);

  Object clone();
}
