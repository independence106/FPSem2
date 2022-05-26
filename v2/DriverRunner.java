
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import Entity.Player;
import LevelRelated.BackGroundDrawer;
import LevelRelated.Levels.FileLocations;
import Settings.MapSettings;


public class DriverRunner extends JPanel implements Runnable{

	static final Dimension SCREEN_SIZE = new Dimension(MapSettings.GAME_WIDTH, MapSettings.GAME_HEIGHT);
	
    BackGroundDrawer map = new BackGroundDrawer();
	Stack<Handler> gameStack;
	Thread gameThread;
	
	Graphics graphics;

	LevelHandler levelHandler;
	IntroMenuHandler logoIntroHandler;
	OverworldHandler overworldHandler;

	int imag2x;
	int imag2y;
	
	public DriverRunner() {
		this.setFocusable(true);
		this.addKeyListener(new AL());
		this.setPreferredSize(SCREEN_SIZE);

		levelHandler = new LevelHandler(this);
		logoIntroHandler = new IntroMenuHandler();
		overworldHandler = new OverworldHandler(this);

		gameStack = new Stack<Handler>();
		// map.loadImg("map.png");
        imag2x = map.imag2x;
        imag2y = map.imag2y;
		gameThread = new Thread(this);
		gameThread.start();

		startup();
    }

	public void startup() {
		gameStack.push(levelHandler);
		// gameStack.push(overworldHandler);
		// gameStack.push(logoIntroHandler);
	}
	
	public void paint(Graphics g) {
		Toolkit.getDefaultToolkit().sync(); 
		draw(g);
	}
	
	public void draw(Graphics g) {
		gameStack.peek().draw(g, this);

	}
	
	public void run() {
		//game loop
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0; // fps
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		while(true) {
			
			long now = System.nanoTime();
			delta += (now -lastTime)/ns;
			lastTime = now;

			if(delta >=1) {
				repaint();
				delta--;
			}
			
		}
	}
	public class AL extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			gameStack.peek().keyPressed(e);
		}
		public void keyReleased(KeyEvent e) {
			gameStack.peek().keyReleased(e);

		}
	}
}

