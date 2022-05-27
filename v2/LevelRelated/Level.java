package LevelRelated;

import java.util.ArrayList;

import javax.imageio.ImageIO;

import Entity.Enemy;
import Entity.Player;
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

    public MusicThing music;

    public boolean isDone;
    public boolean isDead;
    public boolean isQuit;

    public boolean startedMusic;

    
    public Level(int level) {
        startup(level);
    }

    public void startup(int level) {
        
        startedMusic = false;
        isDone = false;
        isQuit = false;
        isDead = false;
        player = new Player();
        levMap = new TileMap();
        cam = new Camera(player.getX(), player.getY());
        snapCamera(player);
        
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
            default:
                
                break;
        }
        music.pause();
        
    }
    
    public void loadImg(String string) {
        try {
            background = ImageIO.read(new File(string));

        } catch (Exception e) {
            //TODO: handle exception
        }
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

    public void tick() {
        
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

    public void draw(Graphics g) {
        
        
        tick();
        if (!startedMusic) {
            music.play();
            startedMusic = true;
        }
		cam.tick(player);
		Toolkit.getDefaultToolkit().sync(); 
		g.clearRect(0, 0, 800, 600);
		g.fillRect(0, 0, 800, 600); //background
        
		// map.draw(g, this);		
		if (cam.getX() < 0) g.translate((int) cam.getX(), 0);
		if (cam.getY() < 0) g.translate(0, (int) cam.getY());
        for (Enemy enemy : levMap.enemies) {
            enemy.tick(this);
            enemy.draw(g);
        }
		levMap.draw(g);
		player.draw(g);
		if (cam.getX() < 0) g.translate((int) -cam.getX(), 0);
		if (cam.getY() < 0) g.translate(0, (int) -cam.getY());
		
	}

	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		player.keyPressed(e);

	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		player.keyReleased(e);

	}

    
}
