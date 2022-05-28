package LevelRelated;


import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import Blocks.Block;
import Blocks.ButtonFlag;
import Entity.Animation;
import Entity.Entity;
import Handlers.DriverRunner;
import Settings.MapSettings;

public class Player {

    public static int lives = 5;
    public static int coins = 0;
    
    public double xPos;
    public double yPos;
    public int height;
    public int width;

    //1 right/up, 0 not moving, -1 left/down
    public int movingX = 0;
    public int movingY = 0;

    public int animationTick = 0;

    public boolean falling = true;
    public boolean jumping = false;
    public boolean canJump = false;

    public int jumpTick = 0;
    public double yVelo = 4;
    public double yAccel = 0;
    public double xVelo = 0;

    public Player() {
        this.xPos = 400;
        this.yPos = 80;
        this.height = 80;
        this.width = 40;
        Animation.loadImg();
        
    }

    public Player(int x, int y) {
        
        this.xPos = x;
        this.yPos = y;
        this.height = 80;
        this.width = 40;
        Animation.loadImg();

    }

    
    public void tick(Level level) {
        rigidCollision(level);
        if (falling && !jumping) {
            yVelo = 1 + yAccel;
            if (!(yAccel > 4)) {
                yAccel += 0.15;
            }
            
            
        }
        if (jumping) {
            yVelo = -6 + yAccel;
            yAccel += 0.1;
            jumpTick++;
        }
        if (jumpTick >= 50) {
            jumpTick = 0;
            jumping = false;
            falling = true;
            yAccel = 0;
        }
        
        xPos += xVelo;
        yPos += yVelo;
        rigidCollision(level);
        nonRigidCollision(level);
        if (yPos > 800) {
            level.setDead();
        }

    }

    public void tickOverworld() {
        xPos += xVelo;
    }
    
    //prevents player going through blocks
    public void rigidCollision(Level level) {
        for (int i = 0; i < level.levMap.rigidBlocks.size(); i++) {

            if (getBottomBounds().intersects(level.levMap.rigidBlocks.get(i).getTopBounds())) {
                yVelo = 0;
                yPos = level.levMap.rigidBlocks.get(i).getY() - height;   
                yAccel = 0;
                canJump = true;
               if (level.levMap.rigidBlocks.get(i).getId().equals("button")) {
                   ((ButtonFlag) level.levMap.rigidBlocks.get(i)).setPressed();
                    level.setDone();
               }
               
            }
            if (getRightBounds().intersects(level.levMap.rigidBlocks.get(i).getLeftBounds())) {
                xPos = level.levMap.rigidBlocks.get(i).getX() - width;               
            }

            if (getLeftBounds().intersects(level.levMap.rigidBlocks.get(i).getRightBounds())) {
                System.out.println("doing this");
                xPos = level.levMap.rigidBlocks.get(i).getX() + MapSettings.tileSize;               
            }
            
            if (getTopBounds().intersects(level.levMap.rigidBlocks.get(i).getBounds())) {
                yPos = level.levMap.rigidBlocks.get(i).getY() + MapSettings.tileSize;   
                jumping = false;
                jumpTick = 50;
                falling = true;            
            }
            //adding a mehtod to detect if hit
            //if(getLeftBounds().intersects(level.levMap.rigidBlocks.get(i).getBounds()) || 
            
            
           

        }
    }
    //
    public void nonRigidCollision(Level level) { 
        for (int i = 0; i < level.levMap.nonRigidBlocks.size(); i++) {
            
           if (getBounds().intersects(level.levMap.nonRigidBlocks.get(i).getBounds())) {
               coins++;
               System.out.println(level.levMap.nonRigidBlocks.get(i).getRow() + " " + level.levMap.nonRigidBlocks.get(i).getCol());
               level.levMap.setBlock(level.levMap.nonRigidBlocks.get(i).getRow(), level.levMap.nonRigidBlocks.get(i).getCol(), null);
               level.levMap.nonRigidBlocks.remove(i);
           }
           
        }
        for (int i = 0; i < level.levMap.enemies.size(); i++) {
            if (getRightBounds().intersects(level.levMap.enemies.get(i).getBounds()) || getLeftBounds().intersects(level.levMap.enemies.get(i).getBounds())) {
                level.setDead();

            }
            if (getBottomBounds().intersects(level.levMap.enemies.get(i).getBounds())) {
                level.levMap.enemies.remove(i);
                System.out.println("DEAD");
            }
        }
    }

    public void draw(Graphics g, DriverRunner driver) {
        Graphics o = g.create();
        o.setColor(Color.LIGHT_GRAY);
	    o.drawImage(Animation.getWalk1(), (int) xPos, (int) yPos, driver);
        o.setColor(Color.RED);
        o.fillRect((int) xPos + width - 4, (int) yPos, 4, height - 4);
        o.setColor(Color.BLACK);
        o.drawRect((int) xPos + 4, (int) yPos + height - 4, width - 8, 5);
    }

    public void right() {
        xVelo = 4;
    }

    public void left() {
        xVelo = -4;
    }

    public void up() {
        jumping = true;
        yPos -= 1;
    }

    public void down() {
        yVelo = -4;
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
        return new Rectangle((int) xPos + 1, (int) yPos, width - 1, 5);
    }

    public Rectangle getBottomBounds() {
        return new Rectangle((int) xPos + 4, (int) yPos + height - 4, width - 8, 5); //4 is arbitrary
    }

    public double getX() {
        return xPos;
    }

    public double getY() {
        return yPos;
    }

    public void setLives(int addlives) {
        lives = addlives;
    }

    public int getLives() {
        return lives;
    }

    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_W && canJump) {
            falling = true;
            canJump = false; 
            up();
        }
        if(e.getKeyCode()==KeyEvent.VK_S) {
            movingY = -1;
        }
        if(e.getKeyCode()==KeyEvent.VK_A) {
            left();
        }
        if(e.getKeyCode()==KeyEvent.VK_D) {
            right();
        }
    }
    public void keyReleased(KeyEvent e) {
        // if (e.getKeyCode()==KeyEvent.VK_W) {
        //     falling = true;
        //     movingY = 0;
        // }
        if(e.getKeyCode()==KeyEvent.VK_S) {
            movingY = 0;
        }
        if(e.getKeyCode()==KeyEvent.VK_A) {
            xVelo = 0;
        }
        if(e.getKeyCode()==KeyEvent.VK_D) {
            xVelo = 0;
        }
    }

    
}