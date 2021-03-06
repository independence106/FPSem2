package Blocks;

import java.awt.Graphics;
import java.awt.Rectangle;

import Handlers.DriverRunner;
import Settings.MapSettings;

import java.awt.*;


public class Coin extends Block {

    public Coin(int X, int Y) {
        super(X, Y, "N");
    }

    public void draw(Graphics g, DriverRunner driver) {
        g.setColor(Color.YELLOW);
        
        g.fillOval(Xpos + (MapSettings.tileSize / 4), Ypos + (MapSettings.tileSize / 4), MapSettings.tileSize / 2, MapSettings.tileSize / 2);
        g.setColor(Color.WHITE);
    }

    public Rectangle getBounds() {
        return new Rectangle(Xpos + (MapSettings.tileSize / 4), Ypos + (MapSettings.tileSize / 4), MapSettings.tileSize / 2, MapSettings.tileSize / 2);
    }
    

    public Rectangle getLeftBounds() {
        return new Rectangle(Xpos, Ypos, 4, MapSettings.tileSize);
    }

    public Rectangle getRightBounds() {
        return new Rectangle(Xpos + MapSettings.tileSize - 4, Ypos, 4, MapSettings.tileSize);
    }

    public Rectangle getTopBounds() {
        return new Rectangle(Xpos, Ypos, MapSettings.tileSize, 4);
    }

    public Rectangle getBottomBounds() {
        return new Rectangle(Xpos, Ypos + MapSettings.tileSize - 4, MapSettings.tileSize, 4);
    }

    public String getId() {
        return "coin";
    }
}
