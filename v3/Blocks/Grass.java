package Blocks;

import java.awt.*;
import java.util.Map;

import Settings.*;

public class Grass extends Block {

    public Grass(int X, int Y) {
        super(X, Y);
    }

    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        
        g.fillRect(Xpos, Ypos, MapSettings.tileSize, MapSettings.tileSize);
        g.setColor(Color.WHITE);
    }

    public Rectangle getBounds() {
        return new Rectangle(Xpos, Ypos, MapSettings.tileSize, MapSettings.tileSize);
    }

    public String getId() {
        return "grass";
    }
}
