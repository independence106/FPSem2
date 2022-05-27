import java.awt.event.KeyEvent;
import java.util.Stack;
import java.awt.*;

import javax.swing.Timer;

import music.MusicThing;

public class IntroHandler extends Handler {

    public enum States {
        AnimatedBeginning,
        Settings,
        Main,
        Play,
    }
    // need background for this atm will just be blank, current idea: bunch of pineapples falling from sky
    public MusicThing music; 

    public Stack<States> state;

    public States selection;

    public Timer timer;

    public IntroHandler() {
        try {
            music = new MusicThing("./music/test.mid");
        } catch (Exception e) {
            System.out.println(e + "catch error");
        }  
       music.play();
       music.pause();  
       selection = null;
       state = new Stack<States>();
       startStates();
    }

    public void startStates() {
        state.push(States.Main);
    }

    public void selectOther() {
        if (selection == States.Play) {
            selection = States.Settings;
        } else {
            selection = States.Play;
        }
    }


    @Override
    public void tick(DriverRunner driver) {
        // TODO Auto-generated method stub
        // should do nothing here
    }
    
    public void drawSettings(Graphics g) {
        g.setColor(Color.WHITE);
        g.clearRect(0, 0, 800, 600);
    }

    public void drawMain(Graphics g) {
        // draw background <- insert code here when u find out
        g.setColor(Color.WHITE);
        g.fillRect(300, 200, 100, 50);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Serif", Font.PLAIN, 25));
        g.drawString("Play", 320, 350);
        System.out.println(selection);
    }

    @Override
    public void draw(Graphics g, DriverRunner driver) {
        // TODO Auto-generated method stub
        if (state.peek() == States.Main) {
            drawMain(g);
        } else if (state.peek() == States.Settings) {
            drawSettings(g);
        } else if (state.peek() == States.Play) {
            driver.gameStack.pop();
        }
     }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        if(e.getKeyCode()==KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_S) {
            selectOther();
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER && selection != null) {
            state.push(selection);
            selection = null;
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE && state.peek() != States.Main) {
            state.pop();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    
}
