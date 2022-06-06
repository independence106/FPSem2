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
    public int ticksAnimationDeath;
    public float fade;

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
        ticksAnimationDeath = 0;
        fade = 1f;
    }

    public void rigidCollision(Level level) {
        for (int i = 0; i < level.levMap.rigidBlocks.size(); i++) {

            if (getBottomBounds().intersects(level.levMap.rigidBlocks.get(i).getBounds()) && alive) {
                yVelo = 0;
            }
            if (getRightBounds().intersects(level.levMap.rigidBlocks.get(i).getBounds()) && alive) {
                xPos = level.levMap.rigidBlocks.get(i).getX() - width;       
                xVelo *= -1;  
                if (phase == Phase.TWO || phase == Phase.THREE) {
                    doFireballs = true;
                }      
            }

            if (getLeftBounds().intersects(level.levMap.rigidBlocks.get(i).getBounds()) && alive) {
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

    public void flipDir() {
        xVelo = xVelo;
    }

    public void tickPhaseTwo(Level level) {
        
        if (falling) {
            yVelo = 4;
        }
        
        if (doFireballs && ticksPhaseTwo < 1000) {
            ticksPhaseTwo++;
            if (ticksPhaseTwo % 100 == 0) {
                level.levMap.entities.add(new Fireball(xPos, yPos, level.player.getX(), level.player.getY()));
                level.levMap.entities.add(new Fireball(xPos, yPos, level.player.getX(), level.player.getY() + 100));
                level.levMap.entities.add(new Fireball(xPos, yPos, level.player.getX(), level.player.getY() - 100));

            }
        }  
        else {
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
                xVelo = 20;
                alive = false;
                break;
        }
    }

    public void tickPhaseThree(Level level) {
        if (falling) {
            yVelo = 4;
        }
        if (Math.random() < 0.01) {
            if (level.levMap.enemies.size() < 6) {
                level.levMap.enemies.add(new Kooler((int) (Math.random() * 800), 0));
            }
        }
        if (doFireballs && ticksPhaseTwo < 1000) {
            ticksPhaseTwo++;
            if (ticksPhaseTwo % 100 == 0) {
                level.levMap.entities.add(new Fireball(xPos, yPos, level.player.getX(), level.player.getY()));
            }
        }  
        else {
            doFireballs = false;
            ticksPhaseTwo = 0;
            rigidCollision(level);
            xPos -= xVelo;
            yPos += yVelo;
            rigidCollision(level);    
        }
    }

    public void tickDeathAnimation(Level level) {
        ticksAnimationDeath++;
        if (xPos > 1000) {
            xPos = 990;
            xVelo = -20;
        } else 
        if (xPos < -100) {
            xPos = -90;

            xVelo = 20;
        }
        xPos += xVelo;
        // yPos += yVelo;
        if (ticksAnimationDeath > 700) {
            fadeOut();
        }
        if (fade <= 0) {
            level.setDone();
        }
    }

    public void fadeOut() {
        if (fade > 0.005f) {
            fade -= 0.005f;
        } else {
            fade = 0f;
        }  
    }

    public void tick(Level level) {
        // TODO Auto-generated method stub
       if (phase == Phase.ONE) {
           tickPhaseOne(level);
       } else if (phase == Phase.TWO) {
           tickPhaseTwo(level);
       } else if (phase == Phase.THREE && alive) {
           tickPhaseThree(level);
       } else if (!alive) {
           tickDeathAnimation(level);
       }
        
    }

    @Override
    public void draw(Graphics g, DriverRunner driverRunner) {
        // TODO Auto-generated method stub
        Graphics2D g2d = (Graphics2D) g;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, fade));
        g2d.setColor(Color.RED);
        g2d.fillRect((int) xPos, (int) yPos, width, height);
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
