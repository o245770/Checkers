import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Player {
    Color playersColor;
    private List<Piece> pieces = new ArrayList<>();

    private void initializePieces() {
        // początkowe ustawienie pionków na planszy
        if(playersColor == Color.BLACK) {
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 8; col++) {
                    if ((row + col) % 2 == 1) {
                        pieces.add(new Piece(col, row, Color.black));
                    }
                }
            }
        }else
        {
            for (int row = 5; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    if ((row + col) % 2 == 1) {
                        pieces.add(new Piece(col, row, Color.white));
                    }
                }
            }
        }
    }

    Player(Color playersColor) {
        this.playersColor = playersColor;
        initializePieces();
    }

    public List<Piece> getPieces() {
        return pieces;
    }

}
