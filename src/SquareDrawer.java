import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.GridLayout;

public class SquareDrawer extends JPanel {

    private static final int TILE_SIZE = 50;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        boolean isWhite = true;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (isWhite) {
                    g.setColor(Color.WHITE);
                } else {
                    g.setColor(Color.BLACK);
                }
                g.fillRect(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                isWhite = !isWhite;
            }
            isWhite = !isWhite;
        }
    }

    @Override
    public int getWidth() {
        return 8 * TILE_SIZE;
    }

    @Override
    public int getHeight() {
        return 8 * TILE_SIZE;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Chess Board");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(8 * TILE_SIZE + 16, 8 * TILE_SIZE + 39); // +16 and +39 for frame insets

        SquareDrawer board = new SquareDrawer();
        frame.add(board);

        frame.setVisible(true);
    }
}
