package Blocks;

import java.awt.*;
import java.util.Map;

import Handlers.DriverRunner;
import Settings.*;

public class Wood extends Block {

    public Wood(int X, int Y) {
        super(X, Y, "./images/wood.png");
        image = image.getScaledInstance(40, 40, image.SCALE_DEFAULT);

    }

    public void draw(Graphics g, DriverRunner driver) {
        g.drawImage(image, Xpos, Ypos, driver);
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
        return "wood";
    }
}
