import javax.swing.*;
import java.awt.*;

public class Board extends JPanel {
    int WIDTH = 8, HEIGHT = 8;
    static final int squareSize = 50;
    Square[][] squares = new Square[WIDTH][HEIGHT];

    Board(){
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                squares[i][j] = new Square((i+j)%2==0? Color.white:Color.black,j,i);
            }
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (squares[row][col].SquareColor==Color.white) {
                    g.setColor(Color.WHITE);
                } else {
                    g.setColor(Color.BLACK);
                }
                g.fillRect(col * squareSize, row * squareSize, squareSize, squareSize);
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Chess Board");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(8 * squareSize + 16, 8 * squareSize + 39); // +16 and +39 for frame insets

        Board board = new Board();
        frame.add(board);

        frame.setVisible(true);
    }
}
