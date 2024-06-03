import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CheckersGame {
    Board board;
    private long startTime;
    private Timer timer;
    private JLabel labelPlayerWhite;
    private JLabel labelPlayerBlack;

    public CheckersGame() {
        JFrame window = new JFrame();
        window.setTitle("Checkers");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(800, 600);
        window.setBackground(Color.green);
        window.setLocationRelativeTo(null);
        window.setLayout(new BorderLayout());
        window.setVisible(true);

        //dodanie planszy
        board = new Board();
        board.setBackground(Color.green);
        window.add(board, BorderLayout.CENTER);

        //dodanie panelu bocznego
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        startTime = System.currentTimeMillis();
        // Utworzenie i uruchomienie zegara
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTimeLabels(); // Aktualizacja czasu graczy co sekundę
            }
        });
        timer.start();

        panel.add(board.turnLabel);
        labelPlayerWhite = new JLabel("Black player's time: 0:00");
        panel.add(labelPlayerWhite);
        labelPlayerBlack = new JLabel("White player's time: 0:00");
        panel.add(labelPlayerBlack);
        panel.add(board.errorMessage);

        window.add(panel, BorderLayout.WEST);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CheckersGame());
    }

    private void updateTimeLabels() {
        long elapsedTime = (System.currentTimeMillis() - startTime) / 1000; // Czas od rozpoczęcia gry w sekundach

        // Konwersja czasu na format mm:ss
        long minutes = elapsedTime / 60;
        long seconds = elapsedTime % 60;

        // Aktualizacja etykiet czasu graczy
        String timeString = String.format("%d:%02d", minutes, seconds);
        labelPlayerWhite.setText("Black player's time: " + timeString);
        labelPlayerBlack.setText("White player's time: " + timeString);
    }

    private void restartGame() {
        // Logika restartu gry
        // Możesz dodać tutaj odpowiednie działania do restartu gry
    }
}
