package Blocks;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import Settings.MapSettings;

public class ButtonFlag extends Block {

    public boolean pressed;

    public ButtonFlag(int X, int Y) {
        super(X, Y);
        pressed = false;
    }

    
    public void draw(Graphics g) {
        // TODO Auto-generated method stub\
        g.setColor(Color.RED);
        if (!pressed) {
            g.fillRoundRect(Xpos + 2, Ypos + (MapSettings.tileSize / 2), MapSettings.tileSize - 4, MapSettings.tileSize / 2, 10, 10);
        }
        else {
            g.fillRect(Xpos + 2, Ypos + MapSettings.tileSize - 2, MapSettings.tileSize - 4, 2);
        }
        g.setColor(Color.WHITE);

    }

    
    public Rectangle getBounds() {
        // TODO Auto-generated method stub
        return (!pressed) ? new Rectangle(Xpos + 2, Ypos + (MapSettings.tileSize / 2), MapSettings.tileSize - 4, MapSettings.tileSize / 2) : 
                new Rectangle(Xpos, Ypos+(MapSettings.tileSize), MapSettings.tileSize, 2);
    }
 

    public Rectangle getLeftBounds() {
        return new Rectangle(Xpos, Ypos, 4, MapSettings.tileSize);
    }

    public Rectangle getRightBounds() {
        return new Rectangle(Xpos + MapSettings.tileSize - 4, Ypos, 4, MapSettings.tileSize);
    }

 

    public Rectangle getBottomBounds() {
        return new Rectangle(Xpos, Ypos + MapSettings.tileSize - 4, MapSettings.tileSize, 4);
    }
    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return "button";
    }

    public Rectangle getTopBounds() {
        return new Rectangle(Xpos + 4, Ypos + (MapSettings.tileSize / 2), MapSettings.tileSize - 6, MapSettings.tileSize / 4);
    }

    public boolean setPressed() {
        pressed = true;
        return true;
    }


    

    
    
}
