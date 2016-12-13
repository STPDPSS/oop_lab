package functions;

public class InappropriateFunctionPointException extends IllegalArgumentException{
  public InappropriateFunctionPointException(double x) {
    super("x = " + x);
  }
}
