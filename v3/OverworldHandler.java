import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import Entity.Player;
import LevelRelated.Camera;
import LevelRelated.Level;

import java.awt.*;

// will handle overworld and level selection

public class OverworldHandler extends Handler {
    
    public class Lock {

        public int xPos;
        public int yPos;

        public Lock(int x, int y) {
            this.xPos = x;
            this.yPos = y;
        }

        // REPLACE W/ A CARTOON LOCK PNG
        public void draw(Graphics g) {
            g.setColor(Color.RED);
            g.fillRect(xPos, yPos, 40, 40);
        }
    }

    public Player player;
    public DriverRunner driver; //is this needed?
    public Camera cam;
    
    public boolean glideLeft;
    public boolean glideRight;
    public int timesTick;
    public int selectedLevel;
    public boolean start;

    public int latestLev;

    public final int DISTANCE_BETWEEN_LEVELS = 300;

    


    public OverworldHandler(DriverRunner driver) {
        this.driver = driver;
        timesTick = 0;
        glideLeft = false;
        glideRight = false;
        player = new Player(100, 300);
        cam = new Camera(player.getX(), player.getY());
        selectedLevel = 0;
        start = false;
        snapCamera(player);
        latestLev = 0;
        
    }

    public void snapCamera(Player player) {
		cam.setX(player.xPos);
		cam.setY(player.yPos);
	}

    public void startUp(DriverRunner driver) {
        driver.levelHandler.levels.set(driver.levelHandler.currLev, new Level(driver.levelHandler.currLev + 1));
    }

    @Override
    public void draw(Graphics g, DriverRunner driver) {
        // TODO Auto-generated method stub
        tick(driver);
        cam.tick(player);
		Toolkit.getDefaultToolkit().sync(); 
       
        g.clearRect(0, 0, 800, 600);
		g.fillRect(0, 0, 800, 600); //background
		if (cam.getX() < 0) g.translate((int) cam.getX(), 0);
        for (int i = 0; i < driver.levelHandler.levels.size(); i++) {
           
            g.drawImage(driver.levelHandler.levels.get(i).getImage(), 100 + i * DISTANCE_BETWEEN_LEVELS, 100, driver);
            if (latestLev < (i)) {
                Lock lock = new Lock(100+ i *DISTANCE_BETWEEN_LEVELS, 100);
                lock.draw(g);
            }
        }
		player.draw(g);
		if (cam.getX() < 0) g.translate((int) -cam.getX(), 0);
    }

    @Override
    public void tick(DriverRunner driver) {
        // TODO Auto-generated method stub
        player.tickOverworld();
        if (player.getLives() <= 0) {
            // CUT SCENE DEATH ANIMATION
            System.out.println("DEATH SCREEN ANIMATION");
            player.setLives(5);
        }
        if (glideLeft == false && glideRight == false) {
            player.xVelo = 0;
        } else if (glideRight && timesTick <= 100) {
            player.right();
            
            timesTick++;
        } else if (glideLeft && timesTick <= 100) {
            if (player.getX() >= 100) {
                player.left();
            } else {
                player.xVelo = 0;
                selectedLevel = 0;
            }
           
            timesTick++;
        } 
        if (timesTick > 100 || player.getX() < 100) {
            glideLeft = false;
            glideRight = false;
            
            timesTick = 0;
        }
        if (start) {
            driver.levelHandler.currLev = selectedLevel;
            driver.gameStack.push(driver.levelHandler);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        
        if(e.getKeyCode()==KeyEvent.VK_D && glideLeft == false) {
            glideRight = true;
            selectedLevel++;
        } else if (e.getKeyCode()==KeyEvent.VK_A && glideRight == false) {
            glideLeft = true;
            selectedLevel--;
        }
        if (e.getKeyCode()==KeyEvent.VK_ENTER) {
            startUp(driver);
            start = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }



}
