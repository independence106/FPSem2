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

    public static final int TICKS_TOTAL = 1000;

    public int currentTicks;
    public boolean skip;
    
    public ArrayList<Image> backGroundScene;

    public IntroHandler() {
        loadImg();
        currentTicks = 0;
    }
    
    public void loadImg() {
        try {
            backGroundScene.add(ImageIO.read(new File("./images/intro1.png")));
            backGroundScene.add(ImageIO.read(new File("./images/intro2.png")));
            backGroundScene.add(ImageIO.read(new File("./images/intro3.png")));

        } catch (Exception e) {
            //TODO: handle exception
        }
        backGroundScene.set(0, backGroundScene.get(0).getScaledInstance(30, 30, backGroundScene.get(0).SCALE_DEFAULT));
        backGroundScene.set(1, backGroundScene.get(1).getScaledInstance(30, 30, backGroundScene.get(1).SCALE_DEFAULT));
        backGroundScene.set(2, backGroundScene.get(2).getScaledInstance(30, 30, backGroundScene.get(2).SCALE_DEFAULT));


    }
    @Override
    public void draw(Graphics g, DriverRunner driver) {
        // TODO Auto-generated method stub
        tick(driver);
        g.clearRect(0, 0, 800, 600);
        switch (currentTicks) {
            case 300:
            
        }
        g.drawImage(backGroundScene, 0, 0, driver);
        
    }

    @Override
    public void tick(DriverRunner driver) {
        // TODO Auto-generated method stub
        currentTicks++;
        if (currentTicks > TICKS_TOTAL || skip) {
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
