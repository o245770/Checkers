import java.awt.*;

public class Piece {
    private int col;
    private int row;
    private Color color;
    boolean isKing;


    public Piece(int c, int r, Color color) {
        this.col = c;
        this.row = r;
        this.color = color;
        this.isKing = false;
    }
    public int getCol() {
        return col;
    }
    public int getRow() {
        return row;
    }
    public Color getColor() {
        return color;
    }
    public void setPosition(int x, int y) {
        this.col = x;
        this.row = y;
    }

    public boolean isPieceOnThisPosition(int x, int y) {
        return x == this.col && y == this.row;
    }

    void changeToKing()
    {
        isKing = true;
    }
}
