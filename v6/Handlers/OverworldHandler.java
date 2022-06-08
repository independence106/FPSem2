package Handlers;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Currency;

import javax.imageio.ImageIO;

import Entity.Player;
import Entity.Player.Direction;
import Entity.Player.State;
import LevelRelated.Camera;
import LevelRelated.Level;
import music.MusicThing;

import java.awt.*;

// will handle overworld and level selection

public class OverworldHandler extends Handler {
    
    public class Lock {

        public int xPos;
        public int yPos;
        public Image image;

        public Lock(int x, int y) {
            this.xPos = x;
            this.yPos = y;
            loadImg();
        }

        public void loadImg() {
            try {
                image = ImageIO.read(new File("./images/lock.png"));
    
            } catch (Exception e) {
                //TODO: handle exception
            }
        }

        // REPLACE W/ A CARTOON LOCK PNG
        public void draw(Graphics g, DriverRunner driver) {
            g.drawImage(image,xPos, yPos, driver );
        }
    }

    public Player player;
    public DriverRunner driver; //is this needed?
    public Camera cam;

    public MusicThing music;
    
    public boolean glideLeft;
    public boolean glideRight;
    public int timesTick;
    public int selectedLevel;
    public boolean start;
    public boolean doneWalking;
    public boolean smoothing;

    public Image background;

    public int latestLev;
    public static final int finalLev = 3;
    public Image lives;

    public final int DISTANCE_BETWEEN_LEVELS = 300;

    public OverworldHandler(DriverRunner driver) {
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            //TODO: handle exception
        }
        try {
            music = new MusicThing("./music/overworld.mid");
        } catch (Exception e) {
            System.out.println(e + "catch error");
        }  
        music.pause();
        this.driver = driver;
        timesTick = 0;
        glideLeft = false;
        glideRight = false;
        player = new Player(127, 200, State.BABY);
        cam = new Camera(player.getX(), player.getY());
        selectedLevel = 0;
        start = false;
        snapCamera(player);
        latestLev = 0;
        loadImg();
        doneWalking = true;
        smoothing = false;
    }

    public void snapCamera(Player player) {
		cam.setX(player.xPos);
		cam.setY(player.yPos);
	}

    public void startUp(DriverRunner driver) {
        music.pause();
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            //TODO: handle exception
        }
        driver.levelHandler.resetLev();
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            //TODO: handle exception
        }
    }

    public void loadImg() {
        try {
            lives = ImageIO.read(new File("./images/pineapple.png"));

        } catch (Exception e) {
            //TODO: handle exception
        }
        lives = lives.getScaledInstance(30, 30, lives.SCALE_DEFAULT);
        try {
            background = ImageIO.read(new File("./images/overworldBackground.png"));
        } catch (Exception e) {
            //TODO: handle exception
        }
        background = background.getScaledInstance(800, 600, background.SCALE_DEFAULT);
    }

    @Override
    public void draw(Graphics g, DriverRunner driver) {
        // TODO Auto-generated method stub
        if (!smoothing) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                //TODO: handle exception
            }
            smoothing = true;
        }
        try {
            music.play();

        } catch (Exception e) {
            //TODO: handle exception
        }
        
        tick(driver);
        cam.tick(player);
		Toolkit.getDefaultToolkit().sync(); 
        g.setColor(Color.WHITE);
        g.clearRect(0, 0, 800, 600);
        g.drawImage(background, 0, 0, driver);
		if (cam.getX() < 0) g.translate((int) cam.getX(), 0);
        for (int i = 0; i < driver.levelHandler.levels.size(); i++) {
            g.setColor(Color.ORANGE);

            if (latestLev > i && latestLev != 0) {
                g.fillRect(127 + (i)* DISTANCE_BETWEEN_LEVELS, 300, DISTANCE_BETWEEN_LEVELS, 20);
            }
            
            g.drawImage(driver.levelHandler.levels.get(i).getImage(), 100 + i * DISTANCE_BETWEEN_LEVELS, 150, driver);
            if (latestLev < (i)) {
                Lock lock = new Lock(100+ i *DISTANCE_BETWEEN_LEVELS, 140);
                lock.draw(g, driver);
            }
            if (latestLev < i) {
                g.setColor(Color.BLUE);
            } else {
                g.setColor(Color.RED);
            }
            
            g.fillRoundRect(125 + i * DISTANCE_BETWEEN_LEVELS, 290, 50, 40, 50, 35);
        }
		player.draw(g, driver);
		if (cam.getX() < 0) g.translate((int) -cam.getX(), 0);
        g.setColor(Color.GRAY);
        g.setFont(new Font("Serif", Font.PLAIN, 25));
        g.drawString("LEVEL " + Integer.toString(selectedLevel + 1), 350, 40);
        g.drawImage(lives, 15, 20, driver);
		g.setFont(new Font("Serif", Font.BOLD, 25));
        g.setColor(Color.BLACK); 
        g.drawString("x ",  50, 40);
        g.drawString(Integer.toString(Player.getLives()),  65, 40);
		g.setColor(Color.YELLOW);
		g.fillOval(20, 55, 20, 20);
        g.setColor(Color.BLACK);
        g.drawString("x ",  50, 70);
        g.drawString(Integer.toString(Player.getCoins()),  65, 70);
    }

    @Override
    public void tick(DriverRunner driver) {
        if (Player.getState() != State.BABY) {
            player.yPos = 220;
        } else {
            player.yPos = 270;
        }
        // TODO Auto-generated method stub
        player.tickOverworld();
        if (Player.getLives() <= 0) {
            // CUT SCENE DEATH ANIMATION
            player.setLives(5);            
        }
        if (glideLeft == false && glideRight == false) {
            player.xVelo = 0;
        } else if (glideRight && timesTick <= 75) {
            player.moving = true;
            if (!player.direction.contains(Direction.RIGHT)) {
                player.direction.add(Direction.RIGHT);
            }
            player.right();
            
            timesTick++;
        } else if (glideLeft && timesTick <= 75) {
            if (player.getX() >= 100) {
                player.moving = true;
                if (!player.direction.contains(Direction.LEFT)) {
                player.direction.add(Direction.LEFT);
                }
                player.left();
            } else {
                player.xVelo = 0;
                selectedLevel = 0;
            }
           
            timesTick++;
        } 
        if (timesTick > 75 || player.getX() < 100) {
            player.direction.remove(Direction.LEFT);
            player.direction.remove(Direction.RIGHT);
            player.moving = false;
            glideLeft = false;
            glideRight = false;
            doneWalking = true;
            timesTick = 0;
        }
        if (start) {
            driver.levelHandler.currLev = selectedLevel;
            music.pause();
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                //TODO: handle exception
            }
            driver.gameStack.push(driver.levelHandler);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        
        if(e.getKeyCode()==KeyEvent.VK_D && glideLeft == false && glideRight == false && player.xPos < (120 + (latestLev) * DISTANCE_BETWEEN_LEVELS)) {
            glideRight = true;
            doneWalking = false;
            selectedLevel++;
        } else if (e.getKeyCode()==KeyEvent.VK_A && glideRight == false && glideLeft == false  && player.xPos >= 430) {
            glideLeft = true;
            doneWalking = false;
            selectedLevel--;
        }
        if (e.getKeyCode()==KeyEvent.VK_ENTER && doneWalking) {
          
            startUp(driver);
            smoothing = false;
            start = true;
            
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }



}
