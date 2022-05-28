import java.awt.Graphics;
import java.awt.event.KeyEvent;

import Handlers.DriverRunner;
import Handlers.Handler;

// course clear stuff after every level
public class LevelWinHandler extends Handler {

    public int tick;

    public LevelWinHandler() {
        tick = 0;
    }

    @Override
    public void draw(Graphics g, DriverRunner driver) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void tick(DriverRunner driver) {
        // TODO Auto-generated method stub
        tick++;
        if (tick > 100) {

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
