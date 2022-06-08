package Handlers;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.imageio.ImageIO;

import music.SoundEffect;

public class CourseClearHandler extends Handler {

    public Image clearImage;
    public Image clearBackGround;


    public static final int TICKS_TOTAL = 500;

    public int currentTicks;
    public boolean firstTime;

    public SoundEffect soundEffect;
    public Thread thread;
    
    public CourseClearHandler() {
        currentTicks = 0;
        firstTime = true;
        loadImg();
        soundEffect = new SoundEffect();
        thread = new Thread(soundEffect);
    }

    public void loadImg() {
        try {
            clearImage = ImageIO.read(new File("./images/win.png"));

        } catch (Exception e) {
            //TODO: handle exception
        }
        clearImage = clearImage.getScaledInstance(200, 200, clearImage.SCALE_DEFAULT);
        try {
            clearBackGround = ImageIO.read(new File("./images/clear.png"));

        } catch (Exception e) {
            //TODO: handle exception
        }
        clearBackGround = clearBackGround.getScaledInstance(800, 800, clearBackGround.SCALE_DEFAULT);

    }

    @Override
    public void draw(Graphics g, DriverRunner driver) {
        // TODO Auto-generated method stub
        if (firstTime) {
            try {
                soundEffect.setCourseClear();
                thread.start();
                thread = new Thread(soundEffect);
    
            } catch (Exception a) {
                //TODO: handle exception
            }
            firstTime = false;
        }
        
        tick(driver);
        g.clearRect(0, 0, 800, 600);
        g.drawImage(clearBackGround, 50, -100, driver);
        g.drawImage(clearImage, 300, 350, driver);
    }

    @Override
    public void tick(DriverRunner driver) {
        // TODO Auto-generated method stub
        currentTicks++;
        if (currentTicks > TICKS_TOTAL) {
            firstTime = true;
            currentTicks = 0;
            driver.gameStack.pop();
            
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }
    
}
