package LevelRelated;



import java.util.ArrayList;

import javax.imageio.ImageIO;

import Entity.Enemy;
import Entity.Entity;
import Entity.Player;
import Entity.Player.State;
import Handlers.DriverRunner;
import music.MusicThing;

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
    public Image coins;
    public Image lives;

    public MusicThing music;

    public boolean isDone;
    public boolean isDead;
    public boolean isQuit;
    public boolean goMenu;

    public boolean startedMusic;
    public boolean resumeMusic;
    

    
    public Level(int level) {
        startup(level);
    }

    public void startup(int level) {
        
        startedMusic = true;
        isDone = false;
        isQuit = false;
        isDead = false;
        goMenu = false;
        resumeMusic = false;
        levMap = new TileMap();

        
        switch (level) {
            case 1:
                loadLev("./LevelRelated/Lev1.txt");
                loadImg("./images/pineapple.png");
                try {
                    music = new MusicThing("./music/salaj.mid");
                } catch (Exception e) {
                    System.out.println(e + "catch error");
                }  
                break;
            case 2:
                loadLev("./LevelRelated/Lev2.txt");
                loadImg("./images/pineapple.png");
                try {
                    music = new MusicThing("./music/salaj.mid");
                } catch (Exception e) {
                    System.out.println(e + "catch error");
                }  
                break;
            case 3: 
                loadLev("./LevelRelated/Lev3.txt");
                loadImg("./images/pineapple.png");
                try {
                    music = new MusicThing("./music/salaj.mid");
                } catch (Exception e) {
                    System.out.println(e + "catch error");
                }  
                break;
            default:
                
                break;
        }
        music.pause();
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
            music.pause();
            
            driver.gameStack.push(driver.menuHandler);
        }
        if (startedMusic) {
            music.play();
            startedMusic = false;
        }
        try {
            if (resumeMusic) {
                music.play();
                resumeMusic = false;
            }
        } catch (Exception e) {
            //TODO: handle exception
        }
        player.tick(this);
    }

    public void addScore(int score) {
        scores.add(score);
    }

    public void setDone() {
        isDone = true;

        music.pause();

    }

    public boolean isDone() {
        
        return isDone;
    }

    public void setDead() {
        isDead = true;
        music.pause();

    }

    public boolean isDead() {

        return isDead;
    }

    public void setQuit() {
        isQuit = true;
        
        music.pause();

    }

    public boolean isQuit() {
        return isQuit;
    }

    public void draw(Graphics g, DriverRunner driver) {
        
        
        tick(driver);
        
        
		cam.tick(player);
		Toolkit.getDefaultToolkit().sync(); 
		 //background
		// map.draw(g, this);		
		if (cam.getX() < 0) g.translate((int) cam.getX(), 0);
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
        for (Enemy enemy : levMap.enemies) {
            enemy.tick(this);
            enemy.draw(g, driver);
        }
		levMap.draw(g, driver);
		player.draw(g, driver);
		if (cam.getX() < 0) g.translate((int) -cam.getX(), 0);
		// if (cam.getY() < 0) g.translate(0, (int) -cam.getY());
		
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
