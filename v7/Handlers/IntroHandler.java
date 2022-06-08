package Handlers;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Stack;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.Timer;

import music.MusicThing;

public class IntroHandler extends Handler {

    public static final int TICKS_TOTAL = 2000;

    public int currentTicks;
    public boolean skip;
    
    public ArrayList<Image> backGroundScene;

    public MusicThing music;
    public MusicThing musicForBoss;

    public float fade;

    public IntroHandler() {
        fade = 1f;
        try {
            Thread.sleep(500);
        } catch (Exception e) {
            //TODO: handle exception
        }
        try {
            music = new MusicThing("./music/introPicnic.mid");
        } catch (Exception e) {
            System.out.println(e + "catch error");
        }  
        music.pause();
        try {
            musicForBoss = new MusicThing("./music/bossintro.mid");
        } catch (Exception e) {
            System.out.println(e + "catch error");
        }  
        musicForBoss.pause();
        backGroundScene = new ArrayList<Image>();
        loadImg();
        currentTicks = 0;
    }
    
    public void loadImg() {
        try {
            backGroundScene.add(ImageIO.read(new File("./images/introOne.png")));
            backGroundScene.add(ImageIO.read(new File("./images/introTwo.png")));
            backGroundScene.add(ImageIO.read(new File("./images/introThree.png")));

        } catch (Exception e) {
            //TODO: handle exception
        }
        backGroundScene.set(0, backGroundScene.get(0).getScaledInstance(800, 600, backGroundScene.get(0).SCALE_DEFAULT));
        backGroundScene.set(1, backGroundScene.get(1).getScaledInstance(800, 600, backGroundScene.get(1).SCALE_DEFAULT));
        backGroundScene.set(2, backGroundScene.get(2).getScaledInstance(800, 600, backGroundScene.get(2).SCALE_DEFAULT));


    }
    @Override
    public void draw(Graphics g, DriverRunner driver) {
        Graphics2D g2d = (Graphics2D) g;
        // TODO Auto-generated method stub
        tick(driver);
        g2d.clearRect(0, 0, 800, 600);
        if (currentTicks < 800) {
            music.play();
            g2d.drawImage(backGroundScene.get(0), 0,0, driver);
        } else if (currentTicks < 1500) {
            music.pause();
            g2d.drawImage(backGroundScene.get(1), 0,0, driver);
            musicForBoss.play();
        } else if (currentTicks< 1900) {
            g2d.drawImage(backGroundScene.get(2), 0,0, driver);

        } else {
            fadeOut();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, fade));
            g2d.drawImage(backGroundScene.get(2), 0,0, driver);
        }
        if (skip) {
            musicForBoss.pause();
            music.pause();
        }
       
        
    }

    public void fadeOut() {
        if (fade > 0.01f) {
            fade -= 0.01f;
        } else {
            fade = 0f;
        }  
    }

    @Override
    public void tick(DriverRunner driver) {
        // TODO Auto-generated method stub
        currentTicks++;
        if (currentTicks > TICKS_TOTAL || skip) {
            musicForBoss.pause();
            music.pause();
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                //TODO: handle exception
            }
            driver.gameStack.pop();
            driver.gameStack.push(driver.overworldHandler);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        skip = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    
    
}
