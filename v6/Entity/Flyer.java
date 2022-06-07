//first type of enemy(gomba)
package Entity;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Blocks.Block;
import Blocks.ButtonFlag;
import Handlers.DriverRunner;
import LevelRelated.Level;
import Settings.MapSettings;


public class Flyer extends Enemy {

    public Image image;
    
    public Flyer(){
        startup();
    }

    public Flyer(int x, int y){
        startup();
        xPos = x;
        yPos = y;
    }

    public void startup() {
        try {
            
            image = ImageIO.read(new File("./images/orange.png"));

        } catch (Exception e) {
            //TODO: handle exception
        }
        width = 40;
        height = 40;
        yVelo = 3;
        xVelo = 0;
        setDirectionRight = true;
        falling = true;
    }

    public void rigidCollision(Level level) {
        for (int i = 0; i < level.levMap.rigidBlocks.size(); i++) {

            if (getBottomBounds().intersects(level.levMap.rigidBlocks.get(i).getBounds())) {
                yPos = level.levMap.rigidBlocks.get(i).getY() + height;  
                //yVelo = 0;
                //System.out.println("hit a block");
                //yPos = level.levMap.rigidBlocks.get(i).getY() + MapSettings.tileSize;
                yVelo *= -1;
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
             else if (getTopBounds().intersects(level.levMap.rigidBlocks.get(i).getBounds())) {
                yPos = level.levMap.rigidBlocks.get(i).getY() - height;
                System.out.println("hit a block");
                yVelo *= 0;
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
        return new Rectangle((int) xPos , (int) yPos + height, width - 1, 5); // 4 is arbitrary
    }

    public Rectangle getBottomBounds() {
        return new Rectangle((int) xPos + 1, (int) yPos + height - 4, width - 1, 5); //4 is arbitrary
    }

    public double getX() {
        return xPos;
    }




    @Override
    public void tick(Level level) {
        // TODO Auto-generated method stub

        rigidCollision(level);
        
        yPos -= yVelo;

        rigidCollision(level);    
    }




    @Override
    public void draw(Graphics g, DriverRunner driver) {
        
        // TODO Auto-generated method stub

        g.drawImage(image, (int) xPos, (int) yPos, driver);
    }


}
