package Blocks;

import java.awt.*;

import Handlers.DriverRunner;
import Settings.*;



public class Lava extends Block{
    
    public Lava(int X, int Y) {
        super(X, Y, "N");
    }
    public void draw(Graphics g, DriverRunner driver) {
        g.setColor(new Color(153, 102, 0)); // 
        
        g.fillRect(Xpos, Ypos, MapSettings.tileSize, MapSettings.tileSize);
        g.setColor(Color.ORANGE);
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
}
