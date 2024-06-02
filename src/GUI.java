import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GUI implements ActionListener {
    Board board;

    public GUI() {
        JFrame window = new JFrame();
        window.setTitle("Checkers");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(800, 600);
        window.setBackground(Color.green);
        window.setLocationRelativeTo(null);
        window.setLayout(new BorderLayout()); // Użycie BorderLayout do rozmieszczenia komponentów
        window.setVisible(true);

        board = new Board();
        board.setBackground(Color.green);
        window.add(board, BorderLayout.CENTER); // Dodanie planszy do środka okna

        JPanel panel = new JPanel();
        JLabel labelTurn = new JLabel("Turn: ");
        JLabel labelPlayerWhite = new JLabel("Black player's time: 0:00");
        JLabel labelPlayerBlack = new JLabel("White player's time: 0:00");
        panel.add(labelTurn);
        panel.add(labelPlayerWhite);
        panel.add(labelPlayerBlack);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JButton button = new JButton("Click me");
        button.addActionListener(this);
        panel.add(button);
        window.add(panel, BorderLayout.WEST); // Dodanie panelu z przyciskiem na dole okna
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GUI());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Obsługa zdarzenia kliknięcia przycisku
        System.out.println("Button clicked");
    }
}