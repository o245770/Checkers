import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Board extends JPanel {
    int WIDTH = 8, HEIGHT = 8;
    static final int squareSize = 50;
    Square[][] squares = new Square[WIDTH][HEIGHT];
    List<Piece> pieces = new ArrayList<>();

    private void initializePieces() {
        // Początkowe ustawienie pionków na planszy
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 8; col++) {
                if ((row + col) % 2 == 1) {
                    pieces.add(new Piece(col, row, Color.black));
                }
            }
        }

        for (int row = 5; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if ((row + col) % 2 == 1) {
                    pieces.add(new Piece(col, row, Color.white));
                }
            }
        }
    }

    Board() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                squares[i][j] = new Square((i + j) % 2 == 0 ? Color.white : Color.black, j, i);
            }
        }
        initializePieces(); // Inicjalizacja pionków
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                g.setColor(squares[row][col].SquareColor);
                g.fillRect(col * squareSize, row * squareSize, squareSize, squareSize);
            }
        }
        drawPieces(g); // Rysowanie pionków
    }

    private void drawPieces(Graphics g) {
        for (Piece piece : pieces) {
            g.setColor(piece.getColor());
            int x = piece.getCol() * squareSize;
            int y = piece.getRow() * squareSize;

            //obramowanie
            g.setColor(Color.blue);
            g.fillOval(x + 5, y + 5, squareSize - 10, squareSize - 10);

            //rysowanie pionka
            g.setColor(piece.getColor());
            g.fillOval(x + 10, y + 10, squareSize - 20, squareSize - 20);

       }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Checkers Board");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(8 * Board.squareSize + 16, 8 * Board.squareSize + 39); // +16 i +39 dla marginesów ramki

        Board board = new Board();
        frame.add(board);

        frame.setVisible(true);
    }
}
