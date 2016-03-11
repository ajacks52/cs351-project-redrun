package redrun.main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class Gui
{
  Thread t;

  public static void main(String[] args)
  {
    SwingUtilities.invokeLater(new Runnable()
    // java.awt.EventQueue.invokeLater(new Runnable()
        {
          public void run()
          {
            final Gui GUI = new Gui();
            GUI.showGui();
          }
        });
  }

  public void showGui()
  {

    JFrame window = new JFrame("RedRun");
    window.setBounds(0, 0, 300, 200);
    window.setLocationRelativeTo(null);
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Container content = window.getContentPane();
    content.setLayout(null);
    Component[] components = new Component[6];

    int col = 30, row = 75, start = 50;
    Dimension size;

    JTextArea textArea = new JTextArea("To play click \"Start Server\" then \"Start Game\"\n"
        + "\nIf the server is already started then click\n\"Start Game\"");
    textArea.setEditable(false);
    textArea.setBackground(new Color(238, 238, 238));
    textArea.setWrapStyleWord(true);
    components[2] = textArea;
    size = textArea.getPreferredSize();
    textArea.setBounds(5, 5, size.width, size.height);
    content.add(textArea);

    JButton button1 = new JButton(" Start Server ");
    components[1] = button1;
    size = button1.getPreferredSize();
    button1.setBounds(row * 1, start + (col * 1) - 1, size.width, size.height);
    content.add(button1);

    JButton button2 = new JButton(" Start Game ");
    components[3] = button2;
    size = button2.getPreferredSize();
    button2.setBounds(row * 1, start + (col * 2) - 1, size.width, size.height);
    content.add(button2);

    ActionListener actionListener1 = new ActionListener()
    {
      public void actionPerformed(ActionEvent actionEvent)
      {
        String command = actionEvent.getActionCommand();
        System.out.println(command + " Server");
        if (command.equalsIgnoreCase("start"))
        {
          t = new Thread(new Runnable()
          {
            @Override
            public void run()
            {
              redrun.network.Server.main(null);
            }
          });
          t.start();

          // button1.setActionCommand("End");
        }
        else if (command.equalsIgnoreCase("End"))
        {
          // button1.setActionCommand("Start");
        }
      }
    };
    ActionListener actionListener2 = new ActionListener()
    {
      public void actionPerformed(ActionEvent actionEvent)
      {
        System.out.println("Start Game ");
        Thread t = new Thread(new Runnable()
        {
          @Override
          public void run()
          {
            Main.main(null);
          }
        });
        t.start();

      }
    };

    button1.setActionCommand("Start");
    button1.addActionListener(actionListener1);
    button2.addActionListener(actionListener2);

    window.setVisible(true); // Display the window
  }
}