package functions;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

public class TabulatedFunctions {

  public static TabulatedFunction tabulate(Function function, double leftX, double rightX,
                                           int pointsCount) {
    if (leftX < function.getLeftDomainBorder() || function.getRightDomainBorder() < rightX) {
      throw new IllegalArgumentException();
    }
    FunctionPoint[] points = new FunctionPoint[pointsCount];
    double deltaX = (rightX - leftX) / (pointsCount - 1);
    for (int i = 0; i < pointsCount; i++) {
      points[i] = new FunctionPoint(leftX, function.getFunctionValue(leftX));
      leftX += deltaX;
    }
    return new LinkedListTabulatedFunction(points);
  }

  public static void outputTabulatedFunction(TabulatedFunction function, OutputStream out) {
    byte[] b = function.toString().getBytes();
    try {
      out.write(b);
      out.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static TabulatedFunction inputTabulatedFunction(InputStream in) {
    String s = "";
    try {
      int c = in.read();
      while (c != -1) {
        s += String.valueOf((char)c);
        c = in.read();
      }
      in.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return stringToFunction(s);
  }

  public static void writeTabulatedFunction(TabulatedFunction function, Writer out) {
    try {
      out.write(function.toString());
      out.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static TabulatedFunction readTabulatedFunction(Reader in) {
    String s = "";
    try {
      int c = in.read();
      while (c != -1) {
        s += String.valueOf((char)c);
        c = in.read();
      }
      in.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return stringToFunction(s);
  }

  private static TabulatedFunction stringToFunction(String s) {
    s = s.substring(1, s.length() - 1);
    String[] strings = s.split(", ");
    int n = strings.length;
    FunctionPoint[] points = new FunctionPoint[n];
    for (int i = 0; i < n; i++) {
      points[i] = stringToFunctionPoint(strings[i]);
    }
    return new LinkedListTabulatedFunction(points);
  }

  private static FunctionPoint stringToFunctionPoint(String s) {
    String string = s.substring(1, s.length() - 1);
    String[] point = string.split("; ");
    return new FunctionPoint(Double.valueOf(point[0]), Double.valueOf(point[1]));
  }
}
