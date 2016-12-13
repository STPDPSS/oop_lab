import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import functions.ArrayTabulatedFunction;
import functions.Function;
import functions.FunctionPoint;
import functions.Functions;
import functions.LinkedListTabulatedFunction;
import functions.TabulatedFunction;
import functions.TabulatedFunctions;
import functions.basic.Cos;
import functions.basic.Exp;
import functions.basic.Log;
import functions.basic.Sin;
import functions.meta.Composition;

public class Main {

  public static void main(String[] args) {
    lab5();
  }

  private static void lab5() {
    double[] values = {2, 3, 1, 53.6, 32.4, 86.2, 3, 4, 90};
    double[] values2 = {2, 3, 51, 53.6, 32.4, 86.2, 3, 4, 90};

    //to string
    TabulatedFunction arrayF = new ArrayTabulatedFunction(-10, 5, values);
    TabulatedFunction listF = new LinkedListTabulatedFunction(-10, 5, values);
    System.out.println("-------------");
    System.out.println("Вывод функции");
    System.out.println("1) массив");
    System.out.println(arrayF.toString());
    System.out.println("2) связный список");
    System.out.println(listF.toString());
    System.out.println("-------------");

    //equals
    TabulatedFunction listA = new LinkedListTabulatedFunction(-10, 5, values);
    TabulatedFunction listB = new LinkedListTabulatedFunction(-10, 5, values2);

    TabulatedFunction arrayA = new ArrayTabulatedFunction(-10, 5, values);
    TabulatedFunction arrayB = new ArrayTabulatedFunction(-10, 5, values2);

    System.out.println("-------------");
    System.out.println("Сравнение функций");
    System.out.println("1) функция А на листе == функция А на массиве");
    System.out.println(listA.equals(arrayA));
    System.out.println("2) функция А на листе == функция Б на листе");
    System.out.println(listA.equals(listB));
    System.out.println("3) функция А на массиве == функция Б на массиве");
    System.out.println(arrayA.equals(arrayB));
    System.out.println("-------------");

    //hash code
    System.out.println("-------------");
    System.out.println("Хэши");
    System.out.println("1) функция А на листе");
    System.out.println(listA.hashCode());
    System.out.println("2) функция А на массиве");
    System.out.println(arrayA.hashCode());
    listA.setPointY(2, listA.getPointY(2) + 0.002);
    System.out.println("3) измененная функция А на листе");
    System.out.println(listA.hashCode());
    System.out.println("4) функция Б на массиве");
    System.out.println(arrayB.hashCode());
    System.out.println("-------------");

    //clone
    System.out.println("-------------");
    System.out.println("Клонирование");
    TabulatedFunction listA2 = (TabulatedFunction) listA.clone();
    System.out.println("1) функция А1");
    System.out.println(listA.toString());
    System.out.println("2) функция А2 (клон А1)");
    System.out.println(listA2.toString());
    listA.setPointY(2, 0);
    System.out.println("3) измененная функция А1 в 3-ей точке");
    System.out.println(listA.toString());
    System.out.println("4) функция А2 (осталась прежней)");
    System.out.println(listA2.toString());
    System.out.println("-------------");
  }

  private static void lab4() {
    Function sin = new Sin();
    Function cos = new Cos();
    System.out.println("-------------");
    System.out.println("sin:\n-------------");
    for (double x = 0; x <= 2 * Math.PI; x += 0.1) {
      System.out.println(new FunctionPoint(x, sin.getFunctionValue(x)).toString());
    }
    System.out.println("-------------");
    System.out.println("cos:\n-------------");
    for (double x = 0; x <= 2 * Math.PI; x += 0.1) {
      System.out.println(new FunctionPoint(x, cos.getFunctionValue(x)).toString());
    }


    Function TabulatedSin = TabulatedFunctions.tabulate(sin, 0, 2 * Math.PI, 10);
    Function TabuletesCos = TabulatedFunctions.tabulate(cos, 0, 2 * Math.PI, 10);
    System.out.println("--------------------------");
    System.out.println("sin:\n--------------------------");
    for (double x = 0; x <= 2 * Math.PI; x += 0.1) {
      System.out.print(new FunctionPoint(x, sin.getFunctionValue(x)).toString() + ", ");
      System.out.println(new FunctionPoint(x, TabulatedSin.getFunctionValue(x)).toString());
    }
    System.out.println("--------------------------");
    System.out.println("cos:\n--------------------------");
    for (double x = 0; x <= 2 * Math.PI; x += 0.1) {
      System.out.print(new FunctionPoint(x, cos.getFunctionValue(x)).toString() + ", ");
      System.out.println(new FunctionPoint(x, TabuletesCos.getFunctionValue(x)).toString());
    }
    System.out.println("--------------------------");


    System.out.println("sin^2 + cos^2:\n-------------");
    Function sum = Functions.sum(
        Functions.power(TabulatedSin, 2), Functions.power(TabuletesCos, 2));
    for (double x = 0; x <= 2 * Math.PI; x += 0.1) {
      System.out.println(new FunctionPoint(x, sum.getFunctionValue(x)).toString());
    }


    try {
      TabulatedFunction exp = TabulatedFunctions.tabulate(new Exp(), 0, 10, 11);
      TabulatedFunctions.writeTabulatedFunction(exp, new FileWriter("exp.txt"));
      TabulatedFunction expFromFile = TabulatedFunctions.readTabulatedFunction(
          new FileReader("exp.txt"));
      System.out.println("--------------------------");
      System.out.println("exp:\n--------------------------");
      for (int x = 0; x <= 10; x++) {
        System.out.print(new FunctionPoint(x, exp.getFunctionValue(x)).toString() + ", ");
        System.out.println(new FunctionPoint(x, expFromFile.getFunctionValue(x)).toString());
      }
      System.out.println("--------------------------");
    } catch (Exception e) {
      e.printStackTrace();
    }


    try {
      TabulatedFunction log = TabulatedFunctions.tabulate(new Log(Math.E), 0, 10, 11);
      TabulatedFunctions.outputTabulatedFunction(log, new FileOutputStream("log.txt"));
      TabulatedFunction logFromFile = TabulatedFunctions.inputTabulatedFunction(
          new FileInputStream("log.txt"));
      System.out.println("--------------------------");
      System.out.println("log:\n--------------------------");
      for (int x = 0; x <= 10; x++) {
        System.out.print(new FunctionPoint(x, log.getFunctionValue(x)).toString() + ", ");
        System.out.println(new FunctionPoint(x, logFromFile.getFunctionValue(x)).toString());
      }
      System.out.println("--------------------------");
    } catch (Exception e) {
      e.printStackTrace();
    }


    try {
      TabulatedFunction logExp = TabulatedFunctions.tabulate(
          new Composition(new Log(Math.E), new Exp()), 0, 10, 11);
      FileOutputStream fos = new FileOutputStream("logExp.out");
      ObjectOutputStream oos = new ObjectOutputStream(fos);
      oos.writeObject(logExp);
      oos.flush();
      oos.close();

      FileInputStream fis = new FileInputStream("logExp.out");
      ObjectInputStream oin = new ObjectInputStream(fis);
      TabulatedFunction logExpFromFile = (TabulatedFunction) oin.readObject();

      System.out.println("--------------------------");
      System.out.println("logExp:\n--------------------------");
      for (int x = 0; x <= 10; x++) {
        System.out.print(new FunctionPoint(x, logExp.getFunctionValue(x)).toString() + ", ");
        System.out.println(new FunctionPoint(x, logExpFromFile.getFunctionValue(x)).toString());
      }
      System.out.println("--------------------------");
    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}
