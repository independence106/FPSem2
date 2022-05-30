package Handlers;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Stack;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.Timer;

import music.MusicThing;

public class OutroHandler extends Handler {

    public static final int TICKS_TOTAL = 1000;

    public int currentTicks;
    public boolean skip;
    
    public Image backGroundScene;

    public OutroHandler() {
        loadImg();
        currentTicks = 0;
    }
    
    public void loadImg() {
        try {
            backGroundScene = ImageIO.read(new File("./images/pineapple.png"));

        } catch (Exception e) {
            //TODO: handle exception
        }
        backGroundScene = backGroundScene.getScaledInstance(30, 30, backGroundScene.SCALE_DEFAULT);
        

    }
    @Override
    public void draw(Graphics g, DriverRunner driver) {
        // TODO Auto-generated method stub
        tick(driver);
        g.clearRect(0, 0, 800, 600);
        g.drawImage(backGroundScene, 0, 0, driver);
        
    }

    @Override
    public void tick(DriverRunner driver) {
        // TODO Auto-generated method stub
        currentTicks++;
        if (currentTicks > TICKS_TOTAL || skip) {
            driver.gameStack.pop();
            driver.gameStack.push(driver.creditsHandler);
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
