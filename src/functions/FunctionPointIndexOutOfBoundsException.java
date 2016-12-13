package functions;

public class FunctionPointIndexOutOfBoundsException extends IndexOutOfBoundsException {
  public FunctionPointIndexOutOfBoundsException(int index) {
    super(Integer.toString(index));
  }
}
