import javax.swing.*;

public class Server {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CheckersGame());
    }
}
