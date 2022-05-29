package Handlers;

import java.awt.Graphics;
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
    }

    @Override
    public void tick(DriverRunner driver) {
        // TODO Auto-generated method stub
        if (quit) {
            quit = false;
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
