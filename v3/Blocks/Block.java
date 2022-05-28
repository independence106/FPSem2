package Blocks;

import java.awt.*;

public abstract class Block {

    protected int row;
    protected int col;

    protected int Xpos;
    protected int Ypos;

    public Block() {

    }
    
    public Block(int X, int Y) {
        Xpos = X;
        Ypos = Y;
        row = -1;
        col = -1;
    }

    public abstract void draw(Graphics g);

    public int getX() {
        return Xpos;
    }

    public int getY() {
        return Ypos;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public abstract Rectangle getBounds();
    public abstract Rectangle getLeftBounds();
    public abstract Rectangle getRightBounds();
    public abstract Rectangle getTopBounds();
    public abstract Rectangle getBottomBounds();
        
    public String getId() {
        return "block";
    }
        
}
