package Entity;

import java.awt.*;
import java.awt.Rectangle;

import Handlers.DriverRunner;
import LevelRelated.Level;
import Settings.MapSettings;

public class Boss extends Enemy {

    public enum Phase {
        ONE,
        TWO,
        THREE
    }

    public Phase phase;
    public int ticks;
    public final int TICKS_PER_PHASE = 1000;
    
    public int ticksPhaseTwo;
    public boolean doFireballs;

    public boolean alive;

    public Boss(){
        startup();
    }

    public Boss(int x, int y){
        startup();
        xPos = x;
        yPos = y;
    }

    public void startup() {
        alive = true;
        width = 80;
        height = 80;
        yVelo = 4;
        xVelo = -1;
        setDirectionRight = true;
        falling = true;
        phase = Phase.ONE;
        ticks = 0;
    }

    public void rigidCollision(Level level) {
        for (int i = 0; i < level.levMap.rigidBlocks.size(); i++) {

            if (getBottomBounds().intersects(level.levMap.rigidBlocks.get(i).getBounds())) {
                yVelo = 0;
            }
            if (getRightBounds().intersects(level.levMap.rigidBlocks.get(i).getBounds())) {
                xPos = level.levMap.rigidBlocks.get(i).getX() - width;       
                xVelo *= -1;  
                if (phase == Phase.TWO) {
                    doFireballs = true;
                }      
            }

            if (getLeftBounds().intersects(level.levMap.rigidBlocks.get(i).getBounds())) {
                xPos = level.levMap.rigidBlocks.get(i).getX() + MapSettings.tileSize; 
                xVelo *= -1;   
                if (phase == Phase.TWO) {
                    doFireballs = true;
                }            
            }
        }
    }

    public void tickPhaseOne(Level level) {
        if ((level.player.xPos < xPos && xVelo < 0 || level.player.xPos > xPos && xVelo > 0) && Math.abs((level.player.xPos - xPos)) < 800) {
            xVelo *= -1;
        }
        if (falling) {
            yVelo = 4;
        }
        rigidCollision(level);
        xPos -= xVelo;
        yPos += yVelo;
        rigidCollision(level);    
    }

    public void tickPhaseTwo(Level level) {
        
        if (falling) {
            yVelo = 4;
        }
        
        if (doFireballs && ticksPhaseTwo < 1000) {
            ticksPhaseTwo++;
            if (ticksPhaseTwo % 100 == 0) {
                level.levMap.entities.add(new Fireball(xPos, yPos, level.player.getX(), level.player.getY()));
            }
        }  
        if (ticksPhaseTwo > 1000) {
            doFireballs = false;
            ticksPhaseTwo = 0;
            rigidCollision(level);
            xPos -= xVelo;
            yPos += yVelo;
            rigidCollision(level);    
        }
    }

    public void nextPhase() {
        switch(phase) {
            case ONE: 
                phase = Phase.TWO;
                break;
            case TWO:
                phase = Phase.THREE;
                break;
            case THREE:
                alive = false;
                break;
        }
    }

    public void tickPhaseThree(Level level) {
        if (falling) {
            yVelo = 4;
        }
        rigidCollision(level);
        xPos -= xVelo;
        yPos += yVelo;
        rigidCollision(level); 
           
    }

    public void tick(Level level) {
        // TODO Auto-generated method stub
       if (phase == Phase.ONE) {
           tickPhaseOne(level);
       }
        
    }

    @Override
    public void draw(Graphics g, DriverRunner driverRunner) {
        // TODO Auto-generated method stub
        g.setColor(Color.RED);
        g.fillRect((int) xPos, (int) yPos, width, height);
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

    public String getId() {
        return "boss";
    }
}
