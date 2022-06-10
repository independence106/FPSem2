package LevelRelated;



import java.util.ArrayList;

import javax.imageio.ImageIO;

import Entity.Enemy;
import Entity.Entity;
import Entity.Player;
import Entity.Player.State;
import Handlers.DriverRunner;
import Handlers.LevelHandler;
import Handlers.OverworldHandler;
import Handlers.Stats;
import music.MusicThing;
import music.SoundEffect;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.awt.event.*;



public class Level {

    public Player player;
    public TileMap levMap;
    public Camera cam;
    public static ArrayList<Integer> scores; //add quicksort to sort levels

    public Image background;
    public Image levelBackground;
    public Image coins;
    public Image lives;
    public boolean isDone;
    public boolean isDead;
    public boolean isQuit;
    public boolean goMenu;

    public boolean startedMusic;
    public boolean resumeMusic;

    public boolean debugTool;
    
    public int score;
    public int deaths;

    
    public Level(int level, boolean first) {
        startup(level, first);
    }

    public void startup(int level, boolean first) {
       
        debugTool = false;
        startedMusic = true;
        isDone = false;
        isQuit = false;
        isDead = false;
        goMenu = false;
        resumeMusic = true;
        levMap = new TileMap();
        score = 0;
        deaths = 0;
        
        switch (level) {
            case 1:
                loadLev("./LevelRelated/Lev1.txt");
                loadImg("./images/level1Overworld.png");
                loadLevBackground("./images/levelBackground1.png");

                break;
            case 2:
                loadLev("./LevelRelated/Lev2.txt");
                loadImg("./images/level3Overworld.png");
                loadLevBackground("./images/levelBackground3.png");
                
                break;
            case 3: 
                

                loadLev("./LevelRelated/Lev3.txt");
                loadImg("./images/level2Overworld.png");
                loadLevBackground("./images/levelBackground2.png");

                break;
            case 4: 
                

                loadLev("./LevelRelated/Lev4.txt");
                loadImg("./images/level4Overworld.png");
                loadLevBackground("./images/levelBackground4.png");

                break;
            default:
                
                break;
        }
        
        player = new Player(levMap.startX, levMap.startY);
        cam = new Camera(player.getX(), player.getY());
        snapCamera(player);
        player.width = Player.state.width;
        player.width = Player.state.height;
        

        
    }
    
    public void loadImg(String string) {
        try {
            background = ImageIO.read(new File(string));

        } catch (Exception e) {
            //TODO: handle exception
        }
        try {
            lives = ImageIO.read(new File("./images/pineapple.png"));

        } catch (Exception e) {
            //TODO: handle exception
        }
        lives = lives.getScaledInstance(30, 30, lives.SCALE_DEFAULT);
        background = background.getScaledInstance(100, 100, background.SCALE_DEFAULT);

    }
    public void loadLevBackground(String string) {
        try {
            levelBackground = ImageIO.read(new File(string));

        } catch (Exception e) {
            //TODO: handle exception
        }
        levelBackground = levelBackground.getScaledInstance(800, 600, levelBackground.SCALE_DEFAULT);

    }
    

    public Image getImage() {
        return this.background;
    }

    public void snapCamera(Player player) {
		cam.setX(player.xPos);
        
        cam.setY(player.yPos);
		
	}

    public void loadLev(String filename) {
		levMap.loadFile(filename);
		levMap.load();
	}

    public void tick(DriverRunner driver) {
        player.setStateBug(Player.state);
         if (goMenu) {
            goMenu = false;
            
            driver.gameStack.push(driver.menuHandler);
        }
       
          //TODO: handle exception
        
        player.tick(this);
    }

    public void updateScore(int score) {
        this.score += score;
    }

    public void setDone() {
        isDone = true;

    }

    public boolean isDone() {
        
        return isDone;
    }

    public void setDead() {
        isDead = true;
        
    }

    public boolean isDead() {
        try {
            // musicthing.pause();
        } catch (Exception e) {
            //TODO: handle exception
        }
        return isDead;
    }

    public void setQuit() {
       isQuit = true;

    }

    public boolean isQuit() {
        return isQuit;
    }

    public int getScore() {
        return score;
    }

    public void draw(Graphics g, DriverRunner driver) {
        // if (startedMusic) {
        //     soundEffect.start();
        //     startedMusic = false;
        // }
        tick(driver);
        
        
		cam.tick(player);
		Toolkit.getDefaultToolkit().sync(); 
        g.drawImage(levelBackground, 0, 0, driver);
		 //background
		// map.draw(g, this);	

		if (cam.getX() < 0 && driver.levelHandler.currLev < (OverworldHandler.finalLev - 1)) g.translate((int) cam.getX(), 0);
		// if (cam.getY() < 0) g.translate(0, (int) cam.getY());
        
        for (Entity entity : levMap.entities) {
            entity.tick(this);
            entity.draw(g, driver);
            if (entity.yPos > 800) {
                levMap.enemies.remove(entity);
            }
        }
        for (int i = levMap.enemies.size() - 1; i >= 0; i--) {
            levMap.enemies.get(i).tick(this);
            levMap.enemies.get(i).draw(g, driver);

        }

		levMap.draw(g, driver);
		player.draw(g, driver);
		if (cam.getX() < 0 && driver.levelHandler.currLev < (OverworldHandler.finalLev - 1)) g.translate((int) -cam.getX(), 0);
		
	}

	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		player.keyPressed(e);
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            goMenu = true;
        }
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		player.keyReleased(e);

	}

    
}
