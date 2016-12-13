package application;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import functions.LinkedListTabulatedFunction;

public class FunctionParameters extends JDialog {
  final int OK = 1;
  final int CANCEL = 0;

  int windowStatus = -1;

  private JPanel contentPane;
  private JButton buttonOK;
  private JButton buttonCancel;
  private JTextField leftDomainBorderInput;
  private JTextField rightDomainBorderInput;
  private JSpinner pointsCountInput;

  public FunctionParameters() {
    setContentPane(contentPane);
    setTitle("Function parameters");
    setModal(true);
    setResizable(false);
    setFocusable(true);

    pointsCountInput.setModel(new SpinnerNumberModel(2, 2, Integer.MAX_VALUE, 1));

    buttonOK.addActionListener(e -> onOK());
    buttonCancel.addActionListener(e -> onCancel());
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        onCancel();
      }
    });
  }

  private void onOK() {
    try {
      double leftX = getLeftDomainBorder();
      double rightX = getRightDomainBorder();
      int pointsCount = getPointsCount();
      LinkedListTabulatedFunction test = new LinkedListTabulatedFunction(leftX, rightX, pointsCount);
      setVisible(false);
      windowStatus = OK;
    } catch (Exception e) {
      JOptionPane.showMessageDialog(this, "Ooops! Incorrect data :(\n" +
          "Please, try again.");
    }
  }

  private void onCancel() {
    setVisible(false);
    windowStatus = CANCEL;
  }

  int showDialog() {
    this.setVisible(true);
    return windowStatus;
  }

  double getLeftDomainBorder() {
    return Double.parseDouble(leftDomainBorderInput.getText());
  }

  double getRightDomainBorder() {
    return Double.parseDouble(rightDomainBorderInput.getText());
  }

  int getPointsCount() {
    return (int) pointsCountInput.getValue();
  }

  public static void main(String[] args) {
    FunctionParameters dialog = new FunctionParameters();
    dialog.pack();
    dialog.setVisible(true);
    System.exit(0);
  }
}
