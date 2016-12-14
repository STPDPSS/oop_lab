package application;

import com.sun.javafx.scene.control.skin.TableColumnHeader;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;
import java.util.Random;

import javax.swing.*;

import functions.LinkedListTabulatedFunction;

/**
 * Created by nika on 12/13/16.
 */
public class TabulatedFunctions extends JFrame {
  private JPanel contentPane;
  private JTextField newPointX;
  private JTextField newPointY;
  private JButton addPointButton;
  private JButton deletePointButton;
  private JTable functions;

  private FunctionParameters functionParameters;
  private TabulatedFunctionData tabulatedFunctionData;
  private JFileChooser fileChooser;

  public TabulatedFunctions() {
    setContentPane(contentPane);
    setTitle("Tabulated Functions");
    setResizable(true);
    setFocusable(true);

    fileChooser = new JFileChooser();

    JMenuBar menubar = new JMenuBar();

    JMenu file = new JMenu("File");
    JMenuItem newFile = new JMenuItem("New...");
    newFile.addActionListener(e -> {
      functionParameters = new FunctionParameters();
      functionParameters.showDialog();
    });
    JMenuItem openFile = new JMenuItem("Open...");
    openFile.addActionListener(e -> {
      fileChooser.showOpenDialog(contentPane);
    });
    JMenuItem saveFile = new JMenuItem("Save");
    JMenuItem saveAsFile = new JMenuItem("Save as...");
    JMenuItem exit = new JMenuItem("Exit");
    file.add(newFile);
    file.add(openFile);
    file.add(saveFile);
    file.add(saveAsFile);
    file.add(exit);

    JMenu tabulate = new JMenu("Tabulate");
    JMenuItem tabulateFunction = new JMenuItem("Tabulate function...");
    tabulateFunction.addActionListener(e -> {
      fileChooser.showOpenDialog(contentPane);
    });
    tabulate.add(tabulateFunction);

    menubar.add(file);
    menubar.add(tabulate);

    setJMenuBar(menubar);
    pack();

  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new TabulatedFunctions().setVisible(true));
  }

  private void createUIComponents() {
    // TODO: place custom component creation code here
    Random rnd = new Random();
    String[] columnNames = {"x", "y"};
    Object[][] data = {
        {rnd.nextDouble(), rnd.nextDouble()},
        {rnd.nextDouble(), rnd.nextDouble()},
        {rnd.nextDouble(), rnd.nextDouble()},
        {rnd.nextDouble(), rnd.nextDouble()},
        {rnd.nextDouble(), rnd.nextDouble()},
        {rnd.nextDouble(), rnd.nextDouble()},
        {rnd.nextDouble(), rnd.nextDouble()},
        {rnd.nextDouble(), rnd.nextDouble()}
    };
    functions = new JTable(data, columnNames);
    functions.revalidate();
  }
}
