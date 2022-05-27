package Blocks;

import java.awt.*;
import Settings.*;

public class Dirt extends Block {

    public Dirt(int X, int Y) {
        super(X, Y);
    }

    public void draw(Graphics g) {
        g.setColor(new Color(153, 102, 0)); // brown
        
        g.fillRect(Xpos, Ypos, MapSettings.tileSize, MapSettings.tileSize);
        g.setColor(Color.WHITE);
    }

    public Rectangle getBounds() {
        return new Rectangle(Xpos, Ypos, MapSettings.tileSize, MapSettings.tileSize);
    }

    public String getId() {
        return "dirt";
    }
}