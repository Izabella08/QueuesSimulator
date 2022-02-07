package view;

import javax.swing.*;
import java.awt.*;

public class ViewSimulator extends JFrame{

    JFrame frame;
    public JLabel text;
    public JPanel panel;

    public ViewSimulator() {

        frame = new JFrame();
        frame.setSize(600, 600);
        frame.setTitle("RESULTS:");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(null);

        //setez backgoundul frame-ului
        frame.getContentPane().setBackground(new Color(100, 100, 100));

        panel = new JPanel();
        panel.setBounds(0,0,600,600);
        panel.setLayout(null);
        frame.add(panel);

        text = new JLabel();
        text.setBounds(0,0,600,600);
        panel.add(text);

        frame.setVisible(true);
    }

}
