package Entity;


import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Stack;

import Blocks.Block;
import Blocks.ButtonFlag;
import Blocks.Powerup;
import Blocks.Powerup.Phase;
import Handlers.DriverRunner;
import LevelRelated.Level;
import Settings.MapSettings;
import music.SoundEffect;

public class Player {

    public enum State {
        BABY(40, 40),
        NORMAL(40, 80),
        POWERUP(40, 80);

        public final int width;
        public final int height;

        private State(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }

    public enum Direction {
        LEFT,
        RIGHT
    }

    // helpful
    public final static int HAND_TO_WALL = 7;

    public static int lives = 5;
    public static int coins = 0;
    public static State state = Player.getState();

    public double xPos;
    public double yPos;
    public int height;
    public int width;
    public int jumpHeight;

    //1 right/up, 0 not moving, -1 left/down
    public int movingX = 0;
    public int movingY = 0;

    public int animationTick = 0;

    public boolean falling = true;
    public boolean jumping = false;
    public boolean canJump = false;
    public boolean groundPound = false;

    public int jumpTick = 0;
    public double yVelo = 4;
    public double yAccel = 0;
    public double xVelo = 0;

    public final int INVINCIBILITY_TICKS = 10;
    public int currInvincTicks;
    public boolean canTakeDamge;

    public ArrayList<Direction> direction;
    public boolean moving;

    public Thread soundEffect;
    public SoundEffect sound;



    public Player() {

        this.xPos = 400;
        this.yPos = 80;
        startUp();
    }

    public Player(int x, int y) {
        this.xPos = x;
        this.yPos = y;
        startUp();

    }

    public Player(int x, int y, State setState) {
        state = setState;
        this.xPos = x;
        this.yPos = y;
        startUp();

    }

    public void startUp() {
        canTakeDamge = true;
        currInvincTicks = 0;
        this.height = state.height;
        this.width = state.width;
        moving = false;
        Animation.loadImg();
        direction = new ArrayList<Direction>();
        direction.add(Direction.RIGHT);
        sound = new SoundEffect();
        soundEffect = new Thread(sound);
        jumpHeight = 50;
    }


    public void tick(Level level) {
        rigidCollision(level);
        if (falling && !jumping) {
            if (groundPound) {
                yVelo = 5;
            } else {
                yVelo = 1 + yAccel;
                if (!(yAccel > 4)) {
                yAccel += 0.15;
            }
            }



        }
        if (jumping && jumpHeight == 50) {
            yVelo = -5.5 + yAccel;
            yAccel += 0.1;
            jumpTick++;
        } else if (jumping) {
            yVelo = -3 + yAccel;
            yAccel += 0.1;
            jumpTick++;
        }
        if (jumpTick >= jumpHeight || groundPound) {
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
        if (!canTakeDamge) {
            currInvincTicks++;
        }
        if (currInvincTicks > INVINCIBILITY_TICKS) {
            canTakeDamge = true;
        }

    }




    public void tickOverworld() {
        xPos += xVelo;
        if (moving) {
            animationTick++;
        }
    }

    public void setJumpHeight(int ticks) {
        jumpHeight = ticks;
    }

    //prevents player going through blocks (specifically "rigid blocks" such as dirt or brick)
    public void rigidCollision(Level level) {
      //iter over the map, checking if player intersects a block
        for (int i = 0; i < level.levMap.rigidBlocks.size(); i++) {
          //check below the player
            if (getBottomBounds().intersects(level.levMap.rigidBlocks.get(i).getTopBounds())) {
              //button
                if (level.levMap.rigidBlocks.get(i).getId().equals("button")) {
                    ((ButtonFlag) level.levMap.rigidBlocks.get(i)).setPressed();
                        level.setDone();

                }
                //hostile
                else if (level.levMap.rigidBlocks.get(i).getId().equals("hostile")) {
                    level.setDead();
                }
                //other blocks
                else {
                    yPos = level.levMap.rigidBlocks.get(i).getY() - height;
                    yVelo = 0;
                    yAccel = 0;
                    canJump = true;
                    groundPound = false;

                }
            }
            //check to the right of the player
            if (getRightBounds().intersects(level.levMap.rigidBlocks.get(i).getLeftBounds())) {
              //check if hostile
              if (level.levMap.rigidBlocks.get(i).getId().equals("hostile")) {
                  level.setDead();
              }
                xPos = level.levMap.rigidBlocks.get(i).getX() - width + HAND_TO_WALL;
            }
            //check to the left of the player
            if (getLeftBounds().intersects(level.levMap.rigidBlocks.get(i).getRightBounds())) {
              //check if hostile
              if (level.levMap.rigidBlocks.get(i).getId().equals("hostile")) {
                  level.setDead();
              }
                xPos = level.levMap.rigidBlocks.get(i).getX() + MapSettings.tileSize - HAND_TO_WALL;
            }
            //check above the player
            if (getTopBounds().intersects(level.levMap.rigidBlocks.get(i).getBounds())) {
              //check if hostile
              if (level.levMap.rigidBlocks.get(i).getId().equals("hostile")) {
                  level.setDead();
              }
                yPos = level.levMap.rigidBlocks.get(i).getY() + MapSettings.tileSize;
                jumping = false;
                jumpTick = 50;
                falling = true;
                if (level.levMap.rigidBlocks.get(i).getId().equals("powerup"))   {

                    if (((Powerup) level.levMap.rigidBlocks.get(i)).getPhase() == Phase.NOHIT) {

                        level.levMap.entities.add(new RENAMEPowerUp(level.levMap.rigidBlocks.get(i).getX(), level.levMap.rigidBlocks.get(i).getY() - MapSettings.tileSize));
                    }
                    ((Powerup) level.levMap.rigidBlocks.get(i)).setHit();

                }
            }
            //adding a mehtod to detect if hit
            //if(getLeftBounds().intersects(level.levMap.rigidBlocks.get(i).getBounds()) ||




        }
    }

    //deals with player intersecting a "nonrigid" block (coin, powerup)
    public void nonRigidCollision(Level level) {
      //iter over the map, checking if player intersects a block
        for (int i = 0; i < level.levMap.nonRigidBlocks.size(); i++) {

           if (getBounds().intersects(level.levMap.nonRigidBlocks.get(i).getBounds())) {
               coins++;
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
                if (level.levMap.enemies.get(i).getId().equals("boss")) {
                    ((Boss) level.levMap.enemies.get(i)).nextPhase();
                    falling = true;
                    canJump = false;
                    yVelo = 3;

                    yAccel = 0;
                    setJumpHeight(20);
                    up();
                } else {
                    level.levMap.enemies.remove(i);
                    falling = true;
                    canJump = false;
                    yVelo = 3;

                    yAccel = 0;
                    setJumpHeight(20);
                    up();
                    System.out.println("DEAD");
                }

            } else if (getRightBounds().intersects(level.levMap.enemies.get(i).getBounds()) || getLeftBounds().intersects(level.levMap.enemies.get(i).getBounds())) {
                if (canTakeDamge) {
                    if (state != State.BABY) {
                        updateState(State.BABY);
                        level.levMap.enemies.get(i).flipDir();
                        canTakeDamge = false;
                    } else {
                        // level.setDead();

                    }
                }


            }
        }
        for (int i = 0; i < level.levMap.entities.size(); i++) {
            if (getBounds().intersects(level.levMap.entities.get(i).getBounds())) {
                if (level.levMap.entities.get(i).getId().equals("fireball")) {
                    if (canTakeDamge) {
                        if (state != State.BABY) {
                            updateState(State.BABY);
                            level.levMap.entities.remove(i);
                            canTakeDamge = false;
                        } else {
                            // level.setDead();

                        }
                    }
                } else {
                    try {
                        sound.setPowerUp();
                        soundEffect.start();
                        soundEffect = new Thread(sound);

                    } catch (Exception a) {
                        //TODO: handle exception
                    }
                    updateState(State.NORMAL);
                    level.levMap.entities.remove(i);
                }

            }
        }
    }

    public void updateState(State newState) {

        state = newState;
        this.width = state.width;
        this.height = state.height;
        if (newState == State.BABY) {
            this.yPos += 40;
        } else {
            this.yPos -= 40;

        }

    }

    public void setStateBug(State newState) {
        state = newState;
        this.width = state.width;
        this.height = state.height;
    }

    public static State getState() {
        return state;
    }

    public void drawWithAnimation(Graphics g, DriverRunner driver) {
        g.drawImage(getPlayerImageUsed(), (int) xPos, (int) yPos, driver);
    }

    public void draw(Graphics g, DriverRunner driver) {
        Graphics o = g.create();
        o.setColor(Color.LIGHT_GRAY);
        drawWithAnimation(g, driver);
        o.setColor(Color.RED);
        o.drawRect((int) xPos + HAND_TO_WALL, (int) yPos, width - 2 * HAND_TO_WALL, height);

        // o.setColor(Color.BLACK);
        // o.drawRect((int)xPos + width - 4 - HAND_TO_WALL, (int) yPos, 4, height);
        // o.drawRect((int)xPos + HAND_TO_WALL + 1, (int) yPos, 4, height - 4);
    }

    public Image getPlayerImageUsed() {
        if (state == State.BABY) {
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
        } else {
            if (direction.size() > 0) {
                if (direction.get(0) == Direction.LEFT) {
                    if (animationTick < 5) {
                        return Animation.getLeftWalkAdult1();
                    }

                    if (animationTick > 10) {
                        animationTick = 0;

                    }
                    return Animation.getLeftWalkAdult2();
                } else {
                    if (animationTick < 5) {
                        return Animation.getRightWalkAdult1();
                    }

                    if (animationTick > 10) {
                        animationTick = 0;

                    }
                    return Animation.getRightWalkAdult2();
                }
            }
            return Animation.getNormalAdult();
        }

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

        return new Rectangle((int) xPos + HAND_TO_WALL, (int) yPos, state.width - 2 * HAND_TO_WALL, state.height);
    }

    public Rectangle getRightBounds() {

        return new Rectangle((int)xPos + state.width - 4 - HAND_TO_WALL, (int) yPos, 4, state.height);
    }

    public Rectangle getLeftBounds() {

        return new Rectangle((int)xPos + HAND_TO_WALL, (int) yPos, 4, state.height - 1);
    }

    public Rectangle getTopBounds(){
        return new Rectangle((int) xPos + 1 + HAND_TO_WALL, (int) yPos, state.width - 1 - 2 * HAND_TO_WALL, 5);
    }

    public Rectangle getBottomBounds() {
        return new Rectangle((int) xPos + 5 + HAND_TO_WALL, (int) yPos + state.height - 4, state.width - 10 - 2*HAND_TO_WALL, 5); //4 is arbitrary
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
            setJumpHeight(50);
            up();
        }
        if(e.getKeyCode()==KeyEvent.VK_S) {
            movingY = -1;
            groundPound = true;
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
