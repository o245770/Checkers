import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Board extends JPanel {
    static final int WIDTH = 8, HEIGHT = 8;
    static final int SQUARE_SIZE = 50;
    private final Square[][] squares = new Square[WIDTH][HEIGHT];
    Player whitePlayer;
    Player blackPlayer;
    Player currentPlayer;
    private Piece selectedPiece;
    private int initialX, initialY;
    public JLabel errorMessage;
    public JLabel turnLabel;


    Board() {
        errorMessage = new JLabel("Start");
        turnLabel = new JLabel("Turn: WHITE");
        selectedPiece = null;

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                squares[i][j] = new Square((i + j) % 2 == 0 ? Color.white : Color.black, j, i);
            }
        }
        whitePlayer = new Player(Color.white);
        blackPlayer = new Player(Color.black);
        currentPlayer = whitePlayer;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(selectedPiece == null) {
                    int x = e.getX();
                    int y = e.getY();
                    for (Piece piece : currentPlayer.getPieces()) {
                        //środek pionka
                        int pieceX = piece.getCol() * SQUARE_SIZE + SQUARE_SIZE / 2;
                        int pieceY = piece.getRow() * SQUARE_SIZE + SQUARE_SIZE / 2;
                        int distance = (int) Math.sqrt(Math.pow(pieceX - x, 2) + Math.pow(pieceY - y, 2));
                        if (distance < SQUARE_SIZE / 2) {
                            if(piece.getColor() == Color.white && currentPlayer==blackPlayer) {
                                errorMessage.setText("To jest kolejka czarnych!");
                                repaint();
                                break;
                            }
                            if(piece.getColor() == Color.black && currentPlayer==whitePlayer) {
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
                        errorMessage.setText("Poprawny ruch, ("+ newRow+' ' + newCol+")");
                        selectedPiece.setPosition(newCol, newRow);

                        changeToKing(newRow);

                        if(currentPlayer==whitePlayer) currentPlayer=blackPlayer;
                        else currentPlayer=whitePlayer;

                        if(currentPlayer.getPieces().size()==0) {
                            errorMessage.setText("Koniec gry!");

                            if(currentPlayer==whitePlayer) currentPlayer=blackPlayer;
                            else currentPlayer=whitePlayer;
                            currentPlayer.getPieces().clear();
                        }
                        repaint();
                        if(currentPlayer==whitePlayer) {
                            turnLabel.setText("Turn: WHITE");
                        }else {
                            turnLabel.setText("Turn: BLACK");
                        }
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
        if(!selectedPiece.isKing)
        {
            if(newCol!=initialX-1 && newCol!=initialX+1)
            {
                if(newCol==initialX-2 || newCol==initialX+2)
                {
                    if(!checkCapture(newRow,newCol)){
                        errorMessage.setText("Za dalekie pole!");
                        return false;
                    }
                }else {
                    errorMessage.setText("Za dalekie pole");
                    return false;
                }
            }
        }else
        {
            int xDiffrence = Math.abs(newCol-initialX);
            int yDiffrence = Math.abs(newRow-initialY);

            if(xDiffrence!=yDiffrence)
            {
                errorMessage.setText("Za dalekie pole");
                return false;
            }

            checkCaptureKing(newRow,newCol);
        }


        //czy na polu znajduje się pionek
        for (Piece piece : whitePlayer.getPieces())
        {
            if(piece.isPieceOnThisPosition(newCol,newRow) && piece!=selectedPiece)
            {
                errorMessage.setText("Na polu znajduje sie pionek");
                return false;
            }
        }
        for (Piece piece : blackPlayer.getPieces())
        {
            if(piece.isPieceOnThisPosition(newCol,newRow) && piece!=selectedPiece)
            {
                errorMessage.setText("Na polu znajduje sie pionek");
                return false;
            }
        }
        return true;
    }

    boolean checkCapture(int newRow, int newCol) {
        int middleRow = (newRow+initialY)/2;
        int middleCol = (newCol+initialX)/2;
        Player opponentPlayer;
        if(currentPlayer==whitePlayer) opponentPlayer = blackPlayer;
        else opponentPlayer = whitePlayer;
        for(Piece piece : opponentPlayer.getPieces())
        {
            if(piece.isPieceOnThisPosition(middleCol,middleRow))
            {
                opponentPlayer.getPieces().remove(piece);
                return true;
            }
        }
        return false;
    }
    boolean checkCaptureKing(int newRow, int newCol) {
        int rowStep = newRow > initialY ? 1 : -1;
        int colStep = newCol > initialX ? 1 : -1;
        int distance = Math.abs(newRow - initialY);

        Player opponentPlayer = (currentPlayer == whitePlayer) ? blackPlayer : whitePlayer;

        for (int i = 1; i < distance; i++) {
            int middleRow = initialY + i * rowStep;
            int middleCol = initialX + i * colStep;

            for (Piece piece : opponentPlayer.getPieces()) {
                if (piece.isPieceOnThisPosition(middleCol, middleRow)) {
                    opponentPlayer.getPieces().remove(piece);
                    return true;
                }
            }
        }

        return false;
    }

    void changeToKing(int newRow)
    {
        if(currentPlayer==whitePlayer)
        {
            if(newRow==0)
            {
                selectedPiece.changeToKing();
                errorMessage.setText("Pionek staje się damką!");
            }
        }else{
            if(newRow==7)
            {
                selectedPiece.changeToKing();
                errorMessage.setText("Pionek staje się damką!");
            }
        }
    }



    private void drawPieces(Graphics g) {
        for (Piece piece : whitePlayer.getPieces()) {

            g.setColor(piece.getColor());
            int x = piece.getCol() * SQUARE_SIZE;
            int y = piece.getRow() * SQUARE_SIZE;

            //obramowanie
            if(piece==selectedPiece)
            {
                g.setColor(Color.red);
            }else if(piece.isKing)
            {
                g.setColor(Color.yellow);
            }else
            {
                g.setColor(Color.blue);
            }
            g.fillOval(x + 5, y + 5, SQUARE_SIZE - 10, SQUARE_SIZE - 10);



            //rysowanie pionka
            g.setColor(piece.getColor());
            g.fillOval(x + 10, y + 10, SQUARE_SIZE - 20, SQUARE_SIZE - 20);
        }
        for (Piece piece : blackPlayer.getPieces()) {

            g.setColor(piece.getColor());
            int x = piece.getCol() * SQUARE_SIZE;
            int y = piece.getRow() * SQUARE_SIZE;

            //obramowanie
            if(piece==selectedPiece)
            {
                g.setColor(Color.red);
            }else if(piece.isKing)
            {
                g.setColor(Color.yellow);
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
