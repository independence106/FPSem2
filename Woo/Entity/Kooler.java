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
import LevelRelated.TileMap;
import Settings.MapSettings;


public class Kooler extends Enemy {

    public Image image;
    
    public Kooler(){
        startup();
    }

    public Kooler(int x, int y){
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
        yVelo = 4;
        xVelo = -2;
        setDirectionRight = true;
        falling = true;
    }

    public void rigidCollision(Level level) {
        for (int i = 0; i < level.levMap.rigidBlocks.size(); i++) {

            if (getBottomBounds().intersects(level.levMap.rigidBlocks.get(i).getBounds())) {
                yPos = level.levMap.rigidBlocks.get(i).getY() - height;  
                yVelo = 0;
            }
            if (getRightBounds().intersects(level.levMap.rigidBlocks.get(i).getBounds())) {
                flipDir();    

                xPos = level.levMap.rigidBlocks.get(i).getX() - width;     
            }

            if (getLeftBounds().intersects(level.levMap.rigidBlocks.get(i).getBounds())) {
                flipDir();

                xPos = level.levMap.rigidBlocks.get(i).getX() + MapSettings.tileSize; 
            }

            
            
           

        }
    }
    //
    public void nonRigidCollision(Level level) { 
        
    }


    public void flipDir() {
        xVelo *= -1;
    }

    public Rectangle getBounds() {
        
        return new Rectangle((int) xPos , (int) yPos, width - 2 , height);
    }

    public Rectangle getRightBounds() {
        
        return new Rectangle((int)xPos + width - 4 , (int) yPos, 4, height);
    }

    public Rectangle getLeftBounds() {
        
        return new Rectangle((int)xPos , (int) yPos, 4, height - 1);
    }

    public Rectangle getTopBounds(){
        return new Rectangle((int) xPos + 1 , (int) yPos, height - 1 - 2 , 5);
    }

    public Rectangle getBottomBounds() {
        return new Rectangle((int) xPos + 5 , (int) yPos + height - 4, height - 10, 5); //4 is arbitrary
    }

    public double getX() {
        return xPos;
    }




    @Override
    public void tick(Level level) {
        // TODO Auto-generated method stub
       
        if (falling) {
            yVelo = 6;
        }
        rigidCollision(level);
        xPos -= xVelo;
        yPos += yVelo;
        rigidCollision(level);    
    }




    @Override
    public void draw(Graphics g, DriverRunner driver) {
        
        // TODO Auto-generated method stub

        g.drawImage(image, (int) xPos, (int) yPos, driver);
    }


}