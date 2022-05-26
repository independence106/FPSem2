package Entity;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import Blocks.Block;
import Blocks.ButtonFlag;
import LevelRelated.Level;
import LevelRelated.TileMap;
import Settings.MapSettings;

public class Player extends Entity {

    public static int lives;
    public static int coins;

    public double xPos;
    public double yPos;
    public int height;
    public int width;

    //1 right/up, 0 not moving, -1 left/down
    public int movingX = 0;
    public int movingY = 0;

    public boolean falling = true;
    public boolean jumping = false;

    public double yVelo = 4;
    public double xVelo = 0;

    public Player() {
        this.xPos = 400;
        this.yPos = 600;
        this.height = 80;
        this.width = 40;
        coins = 0;
        lives = 5;
    }

    public Player(int x, int y) {
        
        this.xPos = x;
        this.yPos = y;
        this.height = 80;
        this.width = 40;
        coins = 0;
        lives = 5;
    }

    
    public void tick(Level level) {
        
        if (falling) {
            yVelo = 6;
        }
        rigidCollision(level);
        xPos += xVelo;
        yPos += yVelo;
        rigidCollision(level);
        nonRigidCollision(level);
    }

    public void tickOverworld() {
        xPos += xVelo;
    }
    
    public void rigidCollision(Level level) {
        for (int i = 0; i < level.levMap.rigidBlocks.size(); i++) {

            if (getBottomBounds().intersects(level.levMap.rigidBlocks.get(i).getBounds())) {
                yVelo = 0;
               if (level.levMap.rigidBlocks.get(i).getId().equals("button")) {
                   ((ButtonFlag) level.levMap.rigidBlocks.get(i)).setPressed();
                    level.setDone();
               }
               
            }
            if (getRightBounds().intersects(level.levMap.rigidBlocks.get(i).getBounds())) {
                xPos = level.levMap.rigidBlocks.get(i).getX() - width;               
            }

            if (getLeftBounds().intersects(level.levMap.rigidBlocks.get(i).getBounds())) {
                xPos = level.levMap.rigidBlocks.get(i).getX() + MapSettings.tileSize;               
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
                lives--;
            }
        }
    }

    public void draw(Graphics g) {
        Graphics o = g.create();
        o.setColor(Color.LIGHT_GRAY);
	    o.fillRect((int) xPos, (int) yPos, width, height);
        // o.setColor(Color.RED);
        // o.fillRect((int) xPos + 1, (int) yPos + height - 4, width - 1, 5);
        // o.setColor(Color.BLACK);
        // o.fillRect((int) playerX, (int) playerY + playerHeight, playerWidth, 4);
    }

    public void right() {
        xVelo = 4;
    }

    public void left() {
        xVelo = -4;
    }

    public void up() {
        jumping = true;
        yPos -= 100;
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

    public Rectangle getBottomBounds() {
        return new Rectangle((int) xPos + 1, (int) yPos + height - 4, width - 1, 5); //4 is arbitrary
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
        if(e.getKeyCode()==KeyEvent.VK_W) {
            falling = false;
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
        if (e.getKeyCode()==KeyEvent.VK_W) {
            falling = true;
            movingY = 0;
        }
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