//first type of enemy(gomba)
package Entity;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.math.*;
import javax.imageio.ImageIO;

import Blocks.Block;
import Blocks.ButtonFlag;
import Handlers.DriverRunner;
import LevelRelated.Level;
import Settings.MapSettings;


public class Flyer extends Enemy {

    public Image image;
    
    public final double originalXPos;
    public final double originalYPos;

    // public final double distanceToTravel;

    public Flyer(){
        startup();
        originalXPos = 0;
        originalYPos = 0;
    }

    public Flyer(int x, int y){
        startup();
        xPos = x;
        yPos = y;
        originalXPos = x;
        originalYPos = y;
    }

    public void startup() {
        try {
            
            image = ImageIO.read(new File("./images/orange.png"));

        } catch (Exception e) {
            //TODO: handle exception
        }
        width = 40;
        height = 40;
        yVelo = 4;
        xVelo = 0;
        setDirectionRight = true;
        falling = true;
    }

    public void rigidCollision(Level level) {
        for (int i = 0; i < level.levMap.rigidBlocks.size(); i++) {

            if (getBottomBounds().intersects(level.levMap.rigidBlocks.get(i).getBounds())) {
                yPos = level.levMap.rigidBlocks.get(i).getY() - height;  
                //yVelo = 0;
                //System.out.println("hit a block");
                //yPos = level.levMap.rigidBlocks.get(i).getY() + MapSettings.tileSize;
               // System.out.println("bottom bounds called");
                yVelo = 4;
            }
            // if (getRightBounds().intersects(level.levMap.rigidBlocks.get(i).getBounds())) {
                // xPos = level.levMap.rigidBlocks.get(i).getX() - width;       
                // xVelo *= -1;        
            // }
// 
            // if (getLeftBounds().intersects(level.levMap.rigidBlocks.get(i).getBounds())) {
                // xPos = level.levMap.rigidBlocks.get(i).getX() + MapSettings.tileSize; 
                // xVelo *= -1;              
            // }
// 
             if (getTopBounds().intersects(level.levMap.rigidBlocks.get(i).getBounds())) {
                yPos = level.levMap.rigidBlocks.get(i).getY() + height + 4;
                //System.out.println("top bounds called");
                yVelo = -4;
            }
        }
    } 

    //
    public void nonRigidCollision(Level level) { 
        
    }


    public void flipDir() {
        yVelo *= -1;
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

    public Rectangle getTopBounds(){
        return new Rectangle((int) xPos + 1 , (int) yPos, width - 1 - 2, 5);
    }

    public Rectangle getBottomBounds() {
        return new Rectangle((int) xPos + 5 , (int) yPos + height - 4, width - 10 - 2, 5); //4 is arbitrary
    }


    @Override
    public void tick(Level level) {
        // TODO Auto-generated method stub

        rigidCollision(level);
        
        yPos -= yVelo;
        
        if (Math.abs(yPos - originalYPos) > 50) {
            yVelo *= -1;
        }
        rigidCollision(level);    
    }




    @Override
    public void draw(Graphics g, DriverRunner driver) {
        
        // TODO Auto-generated method stub

        g.drawImage(image, (int) xPos, (int) yPos, driver);
    }


}
