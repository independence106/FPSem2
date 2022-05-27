package Blocks;

import java.awt.Graphics;
import java.awt.Rectangle;

import Settings.MapSettings;

import java.awt.*;


public class Coin extends Block {

    public Coin(int X, int Y) {
        super(X, Y);
    }

    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        
        g.fillOval(Xpos + (MapSettings.tileSize / 4), Ypos + (MapSettings.tileSize / 4), MapSettings.tileSize / 2, MapSettings.tileSize / 2);
        g.setColor(Color.WHITE);
    }

    public Rectangle getBounds() {
        return new Rectangle(Xpos + (MapSettings.tileSize / 4), Ypos + (MapSettings.tileSize / 4), MapSettings.tileSize / 2, MapSettings.tileSize / 2);
    }
    

    public String getId() {
        return "coin";
    }
}
