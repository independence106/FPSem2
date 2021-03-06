package Entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import Handlers.DriverRunner;
import LevelRelated.Level;

public class Fireball extends Entity {

    public Fireball(double x, double y, double targetX, double targetY) {
        this.width = 30;
        this.height = 30;
        this.xPos = x;
        this.yPos = y;
        falling = true;
        xVelo = 2;
        setUpMath(x, y, targetX, targetY);
        
    }

    public void setUpMath(double x, double y, double targetX, double targetY) {
        double hypotneuse = Math.sqrt(Math.pow((targetX - xPos), 2) + Math.pow((targetY - yPos), 2));
        xVelo = 2 * ((targetX - xPos) / hypotneuse);
        yVelo = 2 * ((targetY - yPos) / hypotneuse);

    }

    @Override
    public void tick(Level level) {
        // TODO Auto-generated method stub
        
        if (xPos < -100 || yPos > 1000 || yPos < 0) {
            
        } else {
            xPos += xVelo;
            yPos += yVelo;
        } 
    }

    @Override
    public void draw(Graphics g, DriverRunner driverRunner) {
        // TODO Auto-generated method stub
        g.setColor(Color.ORANGE);
        g.fillRect((int) xPos, (int ) yPos, width, height);
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

    
    public Rectangle getTopBounds(){
        return new Rectangle((int) xPos + 1 , (int) yPos, height - 1 - 2 , 5);
    }

   
    public String getId() {
        return "fireball";
    }
    
}
 