import java.awt.*;

public class Piece {
    private int col;
    private int row;
    private Color color;
    public Piece(int c, int r, Color color) {
        this.col = c;
        this.row = r;
        this.color = color;
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
}
