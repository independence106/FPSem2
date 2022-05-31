package Handlers;

import java.awt.Graphics;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuHandler extends Handler {

    public boolean quit;

    public MenuHandler() {
        quit = false;
    }

    @Override
    public void draw(Graphics g, DriverRunner driver) {
        // TODO Auto-generated method stub
        tick(driver);
        Graphics2D g2d = (Graphics2D) g;
        // g2d.clearRect(0, 0, 800, 600);

        // TODO Auto-generated method stub
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, 800, 600);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        g2d.setFont(new Font("Sans Serif", Font.PLAIN, 25));
        g2d.drawString("Paused", 350, 280);
        
    }

    @Override
    public void tick(DriverRunner driver) {
        // TODO Auto-generated method stub
        if (quit) {
            quit = false;
            
            driver.levelHandler.levels.get(driver.levelHandler.currLev).resumeMusic = true;
            driver.gameStack.pop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            quit = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }
    
}
