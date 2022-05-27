package Blocks;

import java.awt.*;
import Settings.*;



public class Lava extends Block{
    
    public Lava(int X, int Y) {
        super(X, Y);
    }
    public void draw(Graphics g) {
        g.setColor(new Color(153, 102, 0)); // 
        
        g.fillRect(Xpos, Ypos, MapSettings.tileSize, MapSettings.tileSize);
        g.setColor(Color.ORANGE);
    }

    public Rectangle getBounds() {
        return new Rectangle(Xpos, Ypos, MapSettings.tileSize, MapSettings.tileSize);
    }
}
