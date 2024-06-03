import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Board extends JPanel {
    private final int WIDTH = 8, HEIGHT = 8;
    static final int SQUARE_SIZE = 50;
    private final Square[][] squares = new Square[WIDTH][HEIGHT];
    List<Piece> pieces = new ArrayList<>();
    private Piece selectedPiece;
    private int initialX, initialY;
    public JLabel errorMessage;
    int turn;
    public JLabel turnLabel;

    private void initializePieces() {
        // początkowe ustawienie pionków na planszy
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
        turn = 0;
        errorMessage = new JLabel("Start");
        turnLabel = new JLabel("Turn: WHITE");
        selectedPiece = null;

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                squares[i][j] = new Square((i + j) % 2 == 0 ? Color.white : Color.black, j, i);
            }
        }
        initializePieces(); //inicjalizacja pionków

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(selectedPiece == null) {
                    int x = e.getX();
                    int y = e.getY();
                    for (Piece piece : pieces) {
                        //środek pionka
                        int pieceX = piece.getCol() * SQUARE_SIZE + SQUARE_SIZE / 2;
                        int pieceY = piece.getRow() * SQUARE_SIZE + SQUARE_SIZE / 2;
                        int distance = (int) Math.sqrt(Math.pow(pieceX - x, 2) + Math.pow(pieceY - y, 2));
                        if (distance < SQUARE_SIZE / 2) {
                            if(piece.getColor() == Color.white && turn%2==1) {
                                errorMessage.setText("To jest kolejka czarnych!");
                                repaint();
                                break;
                            }
                            if(piece.getColor() == Color.black && turn%2==0) {
                                errorMessage.setText("To jest kolejka białych!");
                                repaint();
                                break;
                            }
                            selectedPiece = piece;
                            initialX = x/ SQUARE_SIZE;
                            initialY = y/ SQUARE_SIZE;
                            errorMessage.setText("SelectedPiece: "+initialX+' '+initialY);
                            repaint();
                            break;
                        }
                    }
                } else{
                    int x = e.getX();
                    int y = e.getY();
                    int newRow = (y/ SQUARE_SIZE);
                    int newCol = (x/ SQUARE_SIZE);
                    if (isValidMove(newRow, newCol)) {
                        turn++;
                        if(turn % 2 == 0) {
                            turnLabel.setText("Turn: WHITE");
                        }else {
                            turnLabel.setText("Turn: BLACK");
                        }
                        errorMessage.setText("Poprawny ruch, ("+ newRow+' ' + newCol+")");
                        selectedPiece.setPosition(newCol, newRow);
                        repaint();
                    }
                    repaint();
                    selectedPiece = null;
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                g.setColor(squares[row][col].SquareColor);
                g.fillRect(col * SQUARE_SIZE, row * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
            }
        }
        drawPieces(g); // Rysowanie pionków
    }
    private boolean isValidMove(int newRow, int newCol) {
        // czy pole jest w obrębie planszy
        if(newRow < 0 || newRow >= HEIGHT || newCol < 0 || newCol >= WIDTH)
        {
            errorMessage.setText("Pole poza zakresem");
            return false;
        }

        //czy pole jest czarne
        if((newCol+newRow)%2==0)
        {
            errorMessage.setText("Nie można stawac na bialych polach");
            return false;
        }

        //czy pole nie jest zbyt odlegle
        if(newCol!=initialX-1 && newCol!=initialX+1)
        {
            errorMessage.setText("Za dalekie pole");
            return false;
        }
        if(newRow!=initialY-1 && newRow!=initialY+1)
        {
            errorMessage.setText("Za dalekie pole");
            return false;
        }

        //czy na polu znajduje się pionek
        for (Piece piece : pieces)
        {
            if(piece.isPieceOnThisPosition(newCol,newRow) && piece!=selectedPiece)
            {
                errorMessage.setText("Na polu znajduje sie pionek");
                return false;
            }
        }


        return true;
    }

    private void drawPieces(Graphics g) {
        for (Piece piece : pieces) {

            g.setColor(piece.getColor());
            int x = piece.getCol() * SQUARE_SIZE;
            int y = piece.getRow() * SQUARE_SIZE;

            //obramowanie
            if(piece==selectedPiece)
            {
                g.setColor(Color.red);
            }else
            {
                g.setColor(Color.blue);
            }
            g.fillOval(x + 5, y + 5, SQUARE_SIZE - 10, SQUARE_SIZE - 10);

            //rysowanie pionka
            g.setColor(piece.getColor());
            g.fillOval(x + 10, y + 10, SQUARE_SIZE - 20, SQUARE_SIZE - 20);

       }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Checkers Board");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(8 * Board.SQUARE_SIZE + 16, 8 * Board.SQUARE_SIZE + 39); // +16 i +39 dla marginesów ramki

        Board board = new Board();
        frame.add(board);

        frame.setVisible(true);
    }
}
