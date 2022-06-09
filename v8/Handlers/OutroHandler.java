package Handlers;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Stack;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.Timer;

import music.MusicThing;

public class OutroHandler extends Handler {

    public static final int TICKS_TOTAL = 3000;

    public int currentTicks;
    public boolean skip;
    
    public Image backGroundScene;

    public MusicThing music;

    public OutroHandler() {
        loadImg();
        currentTicks = 0;
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
        
    }
    
    public void loadImg() {
        try {
            backGroundScene = ImageIO.read(new File("./images/outro.png"));

        } catch (Exception e) {
            //TODO: handle exception
        }
        backGroundScene = backGroundScene.getScaledInstance(1600, 1600, backGroundScene.SCALE_DEFAULT);
        

    }
    @Override
    public void draw(Graphics g, DriverRunner driver) {
        // TODO Auto-generated method stub
        music.play();
        tick(driver);
        if (currentTicks < (TICKS_TOTAL / 3)) {
            g.drawImage(backGroundScene, 2, -1000, driver);
        } else if (currentTicks < 2 *(TICKS_TOTAL / 3)) {
            
            g.drawImage(backGroundScene, 2, -1000 + (currentTicks) - ((TICKS_TOTAL / 3) ) , driver);
        } else {
            
            g.setFont(new Font("Sans Serif", Font.PLAIN, 50));
            g.drawString("THE END", 300, 250);
        }
        
        
    }

    @Override
    public void tick(DriverRunner driver) {
        // TODO Auto-generated method stub
        currentTicks++;
        if (currentTicks > TICKS_TOTAL || skip) {
            music.pause();
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
