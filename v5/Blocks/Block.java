package Blocks;

import java.awt.*;
import java.io.File;

import javax.imageio.ImageIO;

import Handlers.DriverRunner;

public abstract class Block {

    protected int row;
    protected int col;

    protected int Xpos;
    protected int Ypos;

    protected Image image;

    public Block() {

    }

    public Block(int X, int Y, String sting) {
        Xpos = X;
        Ypos = Y;
        row = -1;
        col = -1;
        loadImg(sting);

    }

    public void loadImg(String string) {
        if (!(string == "N")) {
            try {
                image = ImageIO.read(new File(string));

            } catch (Exception e) {
                //TODO: handle exception
            }

        }

    }

    public abstract void draw(Graphics g, DriverRunner driver);

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
