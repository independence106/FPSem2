import LevelRelated.BackGroundDrawer;
import music.MusicThing;
import java.awt.image.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.Timer;

import java.awt.*;
import java.awt.event.KeyEvent;

public class IntroMenuHandler extends Handler {

    public enum States {
        AnimatedBeginning,
        Settings,
        Main,
        Play,
    }

    // pineapple animation bounce thing
    public class Pineapple {

        public int xPos;
        public int yPos;
        public int xVelo;
        public int yVelo;

        public Image image;
        public double rotation;
        public boolean warped;
        public double rotationdx;

        public Pineapple() {
            xPos = (int) (Math.random() * (750));
            yPos = (int) (Math.random() * (550));

            xVelo = (int) (Math.random() * 3) + 1;
            yVelo = (int) (Math.random() * 3) + 1;
            loadImg();
            warped = (Math.random() < 0.5) ? true : false;
            rotationdx = (Math.random() / 10) + 0.01;
        }

        public void tick() {
            xPos += xVelo;
            yPos += yVelo;
            bounce();
            rotate();
        }

        public void rotate() {
            
            rotation += (warped) ? rotationdx : -rotationdx;
        }

        public void loadImg() {
            try {
                image = ImageIO.read(new File("./images/pineapple.png"));
    
            } catch (Exception e) {
                //TODO: handle exception
            }
            image = image.getScaledInstance(60, 60, image.SCALE_DEFAULT);
        }

        public void draw(Graphics g, DriverRunner driver)
        {
            
            tick();
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.rotate(rotation, xPos + 30, yPos + 30);
            g2d.drawImage(image, xPos, yPos, driver);
        }

        void bounce()
        {
            if (xPos < 0 )
                xVelo = Math.abs(xVelo);
            if ( xPos > 740 )
                xVelo = -1 * Math.abs(xVelo);
            if (yPos < 0 )
                yVelo = Math.abs(yVelo);
            if ( yPos > 540 )
                yVelo = -1 * Math.abs(yVelo);
        }
    }

    public Timer timer;

    public MusicThing music;
    public String filePath;
    public boolean started; 

    private long localTime;

    private String part;
    private boolean isFinishedWithPartOne = true;
    
    public ArrayList<Pineapple> pineapples;

    public Stack<States> state;

    public States selection;
    

    Image logoImg;

    public float fade = 0f;

    public IntroMenuHandler() {
        try {
            music = new MusicThing("./music/test.mid");
        } catch (Exception e) {
            System.out.println(e + "catch error");
        }  
        music.play();
        music.pause();  
        started = false;
        part = "papple";
        localTime = System.currentTimeMillis();
        loadImg();
        selection = null;
        state = new Stack<States>();
        startStates();
        pineapples = new ArrayList<Pineapple>();
        populatePineapples();

    }

    public void populatePineapples() {
        for (int i = 0; i < 40; i++) {
            pineapples.add(new Pineapple());
        }
    }

    public void restart() {
        try {
            music.restart();
        } catch (Exception e) {
            //TODO: handle exception
        }
        
    }

    // do something to make this generic to every class
    public void loadImg() {
		try {
			logoImg = ImageIO.read(new File("./images/logo.png"));

		} catch (Exception e) {
			//TODO: handle exception
		}
		logoImg = logoImg.getScaledInstance(640, 480, logoImg.SCALE_DEFAULT);
	}

    public void tick(DriverRunner driver) {
        if ((System.currentTimeMillis() - localTime) > 500) {
            if (!started) {
                music.play();
                started = true;
            }
        }

        if (System.currentTimeMillis() - localTime < 6000) {
            fadeIn();
        } else if (System.currentTimeMillis() - localTime > 18000) {
            isFinishedWithPartOne = true;
        }
        else if (System.currentTimeMillis() - localTime > 14000) {
            fadeOut();
        } 
        else if (System.currentTimeMillis() - localTime > 11000) {
            part = "";
            fade = 1f;
        } 
        else if (System.currentTimeMillis() - localTime > 6000) {
            fadeOut();
        }
        

        

    }

    public void fadeIn() {
        if (fade < 0.995f) {
            fade += 0.005f;
        } else {
            fade = 1f;
        }
        
    }

    public void fadeOut() {
        if (fade > 0.005f) {
            fade -= 0.005f;
        } else {
            fade = 0f;
        }  
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

    public void drawSettings(Graphics g) {
        g.setColor(Color.WHITE);
        g.clearRect(0, 0, 800, 600);
    }

    public void drawMain(Graphics g) {
        // draw background <- insert code here when u find out
        if (selection == States.Play) {
            g.fillRoundRect(290, 190, 220, 70, 50, 50);
        }
        if (selection == States.Settings) {
            g.fillRoundRect(290, 390, 220, 70, 50, 50);
        }
        g.setColor(Color.YELLOW);
        g.fillRoundRect(300, 200, 200, 50, 50, 50);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Serif", Font.PLAIN, 25));
        g.drawString("Play", 370, 230);

        g.setColor(Color.YELLOW);
        g.fillRoundRect(300, 400, 200, 50, 50, 50);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Serif", Font.PLAIN, 25));
        g.drawString("Settings", 350, 430);
        
        System.out.println(selection);
    }

    public void drawLogo(Graphics g, DriverRunner driver) {
        // for color fading
        Graphics2D g2d = (Graphics2D) g;
        // g2d.clearRect(0, 0, 800, 600);
        tick(driver);

        // TODO Auto-generated method stub
        g2d.fillRect(0, 0, 800, 600);

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, fade));
        if (part.equals("papple")) {
            
            
            
    
            g2d.setColor(Color.BLACK);
            
            g2d.drawImage(logoImg, 80, 60, driver);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
        } else {

            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, fade));
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, 800, 600);
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Serif", Font.PLAIN, 100));
            g2d.drawString("PRESENTS", 180, 300);
        }
        
    }
    
    public void draw(Graphics g, DriverRunner driver) {
        if (!isFinishedWithPartOne) {
            drawLogo(g, driver);
        } else {
            g.clearRect(0, 0, 800, 600);
            for (Pineapple p : pineapples) {
                p.draw(g, driver);
            }
            if (state.peek() == States.Main) {
                drawMain(g);
            } else if (state.peek() == States.Settings) {
                drawSettings(g);
            } else if (state.peek() == States.Play) {

                music.pause();
                driver.gameStack.pop();
            }
        }
        
        
    }

    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        if (isFinishedWithPartOne) {
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
        } else {
            isFinishedWithPartOne = true;
        }
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }




    
    
    
}
