import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CheckersGame {
    Board board;
    final private long startTime;
    final private Timer timer;
    long whitePlayersTime;
    long blackPlayersTime;
    private final JLabel labelPlayerWhite;
    private final JLabel labelPlayerBlack;

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
        whitePlayersTime = 0;
        blackPlayersTime = 0;

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

        if(board.turn%2==0)
        {
            whitePlayersTime += 1;
        }else
        {
            blackPlayersTime += 1;
        }
        // Konwersja czasu na format mm:ss
        long minutesBlack = blackPlayersTime / 60;
        long secondsBlack = blackPlayersTime % 60;
        long minutesWhite = whitePlayersTime / 60;
        long secondsWhite = whitePlayersTime % 60;

        // Aktualizacja etykiet czasu graczy
        labelPlayerWhite.setText("Black player's time: " + minutesBlack+':'+secondsBlack);
        labelPlayerBlack.setText("White player's time: " + minutesWhite+':'+secondsWhite);
    }

}
