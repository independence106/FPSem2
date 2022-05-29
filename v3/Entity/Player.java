package Entity;


import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Stack;

import Blocks.Block;
import Blocks.ButtonFlag;
import Handlers.DriverRunner;
import LevelRelated.Level;
import Settings.MapSettings;
import music.SoundEffect;

public class Player {

    public enum State {
        BABY,
        NORMAL,
        POWERUP
    }

    public enum Direction {
        LEFT,
        RIGHT
    }

    public final static int HAND_TO_WALL = 7;

    public static int lives = 5;
    public static int coins = 0;
    public static State state; 
    
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

    public ArrayList<Direction> direction;
    public boolean moving;
    
    public Thread soundEffect;
    public SoundEffect sound;



    public Player() {
        
        this.xPos = 400;
        this.yPos = 80;
        startUp();
        Animation.loadImg();
        
    }

    public Player(int x, int y) {
        this.xPos = x;
        this.yPos = y;
        startUp();

    }

    public void startUp() {
        state = State.BABY;
        this.height = 40;
        this.width = 40;
        moving = false;
        Animation.loadImg();
        direction = new ArrayList<Direction>();
        direction.add(Direction.RIGHT);
        sound = new SoundEffect();
        soundEffect = new Thread(sound);
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
        if (moving) {
            animationTick++;
        }
        

    }


    

    public void tickOverworld() {
        xPos += xVelo;
        if (moving) {
            animationTick++;
        }
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
                xPos = level.levMap.rigidBlocks.get(i).getX() - width + HAND_TO_WALL;               
            }

            if (getLeftBounds().intersects(level.levMap.rigidBlocks.get(i).getRightBounds())) {
           
                xPos = level.levMap.rigidBlocks.get(i).getX() + MapSettings.tileSize - HAND_TO_WALL;               
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
               try {
                sound.setCoin();
                soundEffect.start();
                soundEffect = new Thread(sound);

            } catch (Exception a) {
                //TODO: handle exception
            }
           }
           
        }
        for (int i = 0; i < level.levMap.enemies.size(); i++) {
            if (getBottomBounds().intersects(level.levMap.enemies.get(i).getBounds())) {
                level.levMap.enemies.remove(i);
                System.out.println("DEAD");
            } else if (getRightBounds().intersects(level.levMap.enemies.get(i).getBounds()) || getLeftBounds().intersects(level.levMap.enemies.get(i).getBounds())) {
                level.setDead();

            }  
        }
    }

    public void drawBaby(Graphics g, DriverRunner driver) {
        g.drawImage(getPlayerImageUsed(), (int) xPos, (int) yPos, driver);
    }

    public void draw(Graphics g, DriverRunner driver) {
        Graphics o = g.create();
        o.setColor(Color.LIGHT_GRAY);
        drawBaby(o, driver);
        o.setColor(Color.RED);
        o.drawRect((int) xPos + HAND_TO_WALL, (int) yPos, width - 2 * HAND_TO_WALL, height);
        
        o.setColor(Color.BLACK);
        o.drawRect((int)xPos + width - 4 - HAND_TO_WALL, (int) yPos, 4, height);
        o.drawRect((int)xPos + HAND_TO_WALL + 1, (int) yPos, 4, height - 4);
    }
    
    public Image getPlayerImageUsed() {
        if (direction.size() > 0) {
            if (direction.get(0) == Direction.LEFT) {
                if (animationTick < 5) {
                    return Animation.getLeftWalk1();
                } 
                
                if (animationTick > 10) {
                    animationTick = 0;
                    
                }
                return Animation.getLeftWalk2();
            } else {
                if (animationTick < 5) {
                    return Animation.getRightWalk1();
                } 
                
                if (animationTick > 10) {
                    animationTick = 0;
                    
                }
                return Animation.getRightWalk2();
            }
        }
        return Animation.getNormalBaby();
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
        if (state == State.BABY) {
            return new Rectangle((int) xPos + HAND_TO_WALL, (int) yPos, width - 2 * HAND_TO_WALL, height);
        }
        return new Rectangle((int) xPos, (int) yPos, width, height);
    }

    public Rectangle getRightBounds() {
        if (state == State.BABY) {
            return new Rectangle((int)xPos + width - 4 - HAND_TO_WALL, (int) yPos, 4, height);
        }
        return new Rectangle((int) xPos + width - 4, (int) yPos, 4, height - 4);
    }

    public Rectangle getLeftBounds() {
        if (state == State.BABY) {
            return new Rectangle((int)xPos + HAND_TO_WALL + 1, (int) yPos, 4, height - 4);
        }
        return new Rectangle((int) xPos + 1, (int) yPos, 4, height - 4);
    }

    public Rectangle getTopBounds(){
        return new Rectangle((int) xPos + 1 + HAND_TO_WALL, (int) yPos, width - 1 - 2 * HAND_TO_WALL, 5);
    }

    public Rectangle getBottomBounds() {
        return new Rectangle((int) xPos + 4 + HAND_TO_WALL, (int) yPos + height - 4, width - 8 - 2*HAND_TO_WALL, 5); //4 is arbitrary
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

    public static int getLives() {
        return lives;
    }

    public static int getCoins() {
        return coins;
    }
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_W && canJump) {
            try {
                sound.setJump();
                soundEffect.start();
                soundEffect = new Thread(sound);

            } catch (Exception a) {
                //TODO: handle exception
            }

            falling = true;
            canJump = false; 
            up();
        }
        if(e.getKeyCode()==KeyEvent.VK_S) {
            movingY = -1;
        }
        if(e.getKeyCode()==KeyEvent.VK_A) {
            moving = true;
            if (!direction.contains(Direction.LEFT)) {
                direction.add(Direction.LEFT);
            }
            
            left();
        }
        if(e.getKeyCode()==KeyEvent.VK_D) {
            moving = true;
            if (!direction.contains(Direction.RIGHT)) {
                direction.add(Direction.RIGHT);
            }
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
            moving = false;
            direction.remove(Direction.LEFT);
            xVelo = 0;
        }
        if(e.getKeyCode()==KeyEvent.VK_D) {
            moving = false;
            direction.remove(Direction.RIGHT);
            xVelo = 0;
        }
    }

    
}