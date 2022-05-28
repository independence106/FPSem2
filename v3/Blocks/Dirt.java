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
        return "dirt";
    }
}