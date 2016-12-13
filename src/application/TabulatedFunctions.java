package application;

import com.sun.javafx.scene.control.skin.TableColumnHeader;

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

  public TabulatedFunctions() {
    setContentPane(contentPane);
    setTitle("Tabulated Functions");
    setResizable(true);
    setFocusable(true);

    JMenuBar menubar = new JMenuBar();

    JMenu file = new JMenu("File");
//    JMenuItem eMenuItem1 = new JMenuItem("Exit");
//    file.add(eMenuItem1);
    JMenu tabulate = new JMenu("Tabulate");
    //JMenuItem eMenuItem2 = new JMenuItem("Tabulate");
    //tabulate.add(eMenuItem2);

    menubar.add(file);
    menubar.add(tabulate);

    setJMenuBar(menubar);
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
  }
}
