package application;

import functions.Function;
import functions.LinkedListTabulatedFunction;
import functions.TabulatedFunction;
import functions.TabulatedFunctions;

/**
 * Created by nika on 12/13/16.
 */
public class TabulatedFunctionData {
  TabulatedFunction tabulatedFunction;
  String fileName;
  boolean modified;

  public void newFunction(double leftX, double rightX, int pointsCount) {
    tabulatedFunction = new LinkedListTabulatedFunction(leftX, rightX, pointsCount);
    modified = true;
  }

  public void saveFunction() {
    //save
    modified = false;
  }

  public void saveFunctionAs(String fileName) {
    this.fileName = fileName;
    saveFunction();
  }

  public void loadFunction(String fileName) {

  }

  public void tabulateFunction(Function function, double leftX, double rightX, int pointsCount) {
    tabulatedFunction = TabulatedFunctions.tabulate(function, leftX, rightX, pointsCount);
    modified = true;
  }
}
