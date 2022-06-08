package Blocks;

import java.awt.*;
import java.awt.Rectangle;
import java.io.File;

import javax.imageio.ImageIO;

import Handlers.DriverRunner;
import Settings.MapSettings;

public class Powerup extends Block {

    public enum Phase {
        HIT,
        NOHIT
    }

    public Phase phase;

    public Image imageHit;

    public Powerup(int x, int y) {
        super(x, y, "./images/powerUpBlock.png");
        loadImgE("./images/powerUpBlockHit.png");
        phase = Phase.NOHIT;
        image = image.getScaledInstance(67, 67, image.SCALE_DEFAULT);
        imageHit = imageHit.getScaledInstance(67, 67, imageHit.SCALE_DEFAULT);
    }

    public void loadImgE(String string) {
        if (!(string == "N")) {
            try {
                imageHit = ImageIO.read(new File(string));
    
            } catch (Exception e) {
                //TODO: handle exception
            }
           
        }
       
    }

    public void drawNoHit(Graphics g, DriverRunner driver) {
        g.drawImage(image, Xpos, Ypos, driver);
    }

    public void drawHit(Graphics g, DriverRunner driver) {
        g.drawImage(imageHit, Xpos, Ypos, driver);
    }

    @Override
    public void draw(Graphics g, DriverRunner driver) {
        
        // TODO Auto-generated method stub
        if (phase == Phase.NOHIT) {
            drawNoHit(g, driver);
        } else {
            drawHit(g, driver);
        }
    }

    public void setHit() {
        phase = Phase.HIT;
    }

    public Phase getPhase() {
        return phase;
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
        return "powerup";
    }
    
}
