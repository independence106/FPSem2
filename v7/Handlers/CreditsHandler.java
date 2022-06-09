package Handlers;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Scanner;

import javax.imageio.ImageIO;

import Entity.Animation;
import music.MusicThing;
import music.SoundEffect;
import java.io.File;


// use queue here

public class CreditsHandler extends Handler {

    public MusicThing music;
    public boolean firstTime;

    public Image credits;
    public int times;
    public int currentTicks;
    public int animationTicks;

    public boolean goStats;

    
    public CreditsHandler() {
        animationTicks = 0;
        goStats = false;
        try {
            Thread.sleep(500);
        } catch (Exception e) {
            //TODO: handle exception
        }
        try {
            music = new MusicThing("./music/credits.wav");
        } catch (Exception e) {
            System.out.println(e + "catch error");
        }  
        music.pause();
        firstTime = true;
        loadImg();
        

    }

    public void loadImg() {
		try {
			credits = ImageIO.read(new File("./images/credits.png"));

		} catch (Exception e) {
			//TODO: handle exception
		}
	}

    @Override
    public void draw(Graphics g, DriverRunner driver) {
        // TODO Auto-generated method stub
        tick(driver);
        music.play();
        g.clearRect(0, 0, 800, 600);

        g.drawImage(credits, 200, -(currentTicks / 2) + 820, driver);
        
    }

    @Override
    public void tick(DriverRunner driver) {
        // TODO Auto-generated method stub
    
        currentTicks++;
        if (currentTicks > 5000) {
            currentTicks = 0;
        }
        if (goStats) {
            goStats = false;
            driver.gameStack.push(driver.stats);
        }
        
    }

    public Image getImageUsed() {
        if (animationTicks > 40) {
            return Animation.getDabLeft();
        } else {
            return Animation.getDabRight();
        }
        if (animationTicks > 80) {
            animationTicks = 0;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        if (e.getKeyCode() == KeyEvent.VK_T) {
            goStats = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }
    
}
