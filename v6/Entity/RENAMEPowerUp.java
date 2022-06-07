package Entity;

import java.awt.*;

import Handlers.DriverRunner;
import LevelRelated.Level;
import Settings.MapSettings;

public class RENAMEPowerUp extends Entity {

    
    public RENAMEPowerUp() {
        startup();
    }

    public RENAMEPowerUp(int x, int y) {
        startup();
        xPos = x;
        yPos = y;
    }

    public void startup() {
        width = 40;
        height = 40;
        yVelo = 4;
        xVelo = -1;
        falling = true;
    }

    @Override
    public void tick(Level level) {
        // TODO Auto-generated method stub
        if (falling) {
            yVelo = 3;
        }
        
        rigidCollision(level);
        xPos -= xVelo;
        yPos += yVelo;
        rigidCollision(level);    
    }

    

    public void rigidCollision(Level level) {
        for (int i = 0; i < level.levMap.rigidBlocks.size(); i++) {

            if (getBottomBounds().intersects(level.levMap.rigidBlocks.get(i).getBounds())) {
                yVelo = 0;
            }
            if (getRightBounds().intersects(level.levMap.rigidBlocks.get(i).getBounds())) {
                xPos = level.levMap.rigidBlocks.get(i).getX() - width;       
                xVelo *= -1;        
            }

            if (getLeftBounds().intersects(level.levMap.rigidBlocks.get(i).getBounds())) {
                xPos = level.levMap.rigidBlocks.get(i).getX() + MapSettings.tileSize; 
                xVelo *= -1;              
            }
        }
    }

    public Rectangle getBounds() {
        return new Rectangle((int) xPos, (int) yPos, width, height);
    }

    public Rectangle getRightBounds() {
        return new Rectangle((int) xPos + width - 4, (int) yPos, 4, height - 4);
    }

    public Rectangle getLeftBounds() {
        return new Rectangle((int) xPos + 1, (int) yPos, 4, height - 4);
    }

    public Rectangle getBottomBounds() {
        return new Rectangle((int) xPos + 1, (int) yPos + height - 4, width - 1, 5); //4 is arbitrary
    }

    public double getX() {
        return xPos;
    }

    @Override
    public void draw(Graphics g, DriverRunner driverRunner) {
        // TODO Auto-generated method stub
        g.setColor(Color.BLUE);
        g.fillRect((int) xPos, (int) yPos, width, height);
    }
    
}
