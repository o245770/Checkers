import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI implements ActionListener {
    Board board;
    public GUI(){
        JFrame window = new JFrame();
        window.setTitle("Checkers");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(800, 600);
        window.setBackground(Color.green);
        window.setLocationRelativeTo(null);
        window.setLayout(null);
        window.setVisible(true);

        JPanel panel = new JPanel();

    }

    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setTitle("Checkers");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(800, 600);

        Board board = new Board();
        window.add(board);

        window.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}