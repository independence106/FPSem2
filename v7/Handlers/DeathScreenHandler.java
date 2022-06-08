package Handlers;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.imageio.ImageIO;

import Entity.Player;
import music.MusicThing;
import music.SoundEffect;

public class DeathScreenHandler extends Handler {

    public Image deathImg;
    public Image gameOver;
    public Image restart;
    public final static int TICKS_TOTAL = 600;
    
    public boolean resetLevels;
    public int currentTicks;

    public MusicThing music;

    public SoundEffect soundEffect; // use these but until i find the right song ill use the other one
    public Thread thread;

    public DeathScreenHandler() {
        loadImg();
        try {
            music = new MusicThing("./music/TestFile.mid");
        } catch (Exception e) {
            //TODO: handle exception
        }
        music.pause();
        soundEffect = new SoundEffect();
        thread = new Thread(soundEffect);
        currentTicks = 0;
        resetLevels = false;
        
    }

    public void loadImg() {
        try {
			deathImg = ImageIO.read(new File("./images/death.png"));

		} catch (Exception e) {
			//TODO: handle exception
		}
        deathImg = deathImg.getScaledInstance(200, 200, deathImg.SCALE_DEFAULT);
        try {
			gameOver = ImageIO.read(new File("./images/gameover.png"));

		} catch (Exception e) {
			//TODO: handle exception
		}
        gameOver = gameOver.getScaledInstance(800, 800, deathImg.SCALE_DEFAULT);
        try {
			restart = ImageIO.read(new File("./images/restart.png"));

		} catch (Exception e) {
			//TODO: handle exception
		}
        restart = restart.getScaledInstance(50, 50, restart.SCALE_DEFAULT);
    }

    @Override
    public void draw(Graphics g, DriverRunner driver) {
        music.play();
        tick(driver);
        // TODO Auto-generated method stub
        
        g.clearRect(0, 0, 800, 600);
        if (currentTicks > TICKS_TOTAL) {
            g.drawImage(restart, 720, 520, driver);
        }
        g.drawImage(gameOver, 0, -200, driver);
        g.drawImage(deathImg, 370, 270, driver);
    }

    @Override
    public void tick(DriverRunner driver) {
        // TODO Auto-generated method stub
        currentTicks++;

        if (resetLevels) {
            resetLevels = false;
            currentTicks = 0;
            music.pause();
            try {
                music.restart();

                Thread.sleep(100);
            } catch (Exception e) {
                //TODO: handle exception
            }
            Player.setLives(5);
            driver.gameStack.pop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        if (currentTicks > TICKS_TOTAL) {
            resetLevels = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }
    
}
