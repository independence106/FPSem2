package Handlers;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import LevelRelated.BackGroundDrawer;
import Settings.MapSettings;
import music.MusicThing;


public class DriverRunner extends JPanel implements Runnable{

	static final Dimension SCREEN_SIZE = new Dimension(MapSettings.GAME_WIDTH, MapSettings.GAME_HEIGHT);

    public BackGroundDrawer map = new BackGroundDrawer();
	public Stack<Handler> gameStack;
	public Thread gameThread;

	public MusicThing lev1Music;
	public MusicThing lev2Music; 
	public MusicThing lev3Music; 

	public Graphics graphics;

	public LevelHandler levelHandler;
	public IntroMenuHandler logoIntroHandler;
	public OverworldHandler overworldHandler;
	public DeathScreenHandler deathScreenHandler;
	public IntroHandler introHandler;
	public CreditsHandler creditsHandler;
	public CourseClearHandler courseClearHandler;
	public MenuHandler menuHandler;
	public OutroHandler outroHandler;
	public Stats stats;

	public int imag2x;
	public int imag2y;

	public DriverRunner() {
		this.setFocusable(true);
		this.addKeyListener(new AL());
		this.setPreferredSize(SCREEN_SIZE);

		overworldHandler = new OverworldHandler(this);
		levelHandler = new LevelHandler(this);
		logoIntroHandler = new IntroMenuHandler();

		deathScreenHandler = new DeathScreenHandler();
		introHandler = new IntroHandler();
		creditsHandler = new CreditsHandler();
		courseClearHandler = new CourseClearHandler();
		menuHandler = new MenuHandler();
		outroHandler = new OutroHandler();

		stats = new Stats();

		gameStack = new Stack<Handler>();
		// map.loadImg("map.png");
        imag2x = map.imag2x;
        imag2y = map.imag2y;
		startMusic();
		gameThread = new Thread(this);
		gameThread.start();

		startup();

    }

	public void startMusic() {
		try {
            lev1Music = new MusicThing("./music/salaj.mid");
        } catch (Exception e) {
            System.out.println(e + "catch error");
        }  
		lev1Music.pause();
		try {
            lev2Music = new MusicThing("./music/lev2.mid");
        } catch (Exception e) {
            System.out.println(e + "catch error");
        } 
		lev2Music.pause();
		try {
            lev3Music = new MusicThing("./music/lev3.mid");
        } catch (Exception e) {
            System.out.println(e + "catch error");
        } 
		lev3Music.pause();
		try {
			Thread.sleep(50);
		} catch (Exception e) {
			//TODO: handle exception
		}
	}

	public void resetMusic() {
		try {
			lev1Music.resetAudioStream();
			lev1Music.restart();
			lev1Music.pause();
			lev2Music.resetAudioStream();
			lev2Music.restart();
			lev2Music.pause();
			lev3Music.resetAudioStream();
			lev3Music.restart();
			lev3Music.pause();
		} catch (Exception e) {
			//TODO: handle exception
		}
	}

	public void startup() {
		// gameStack.push(levelHandler);
		// gameStack.push(overworldHandler);
		gameStack.push(logoIntroHandler);
		// gameStack.push(creditsHandler);
		// gameStack.push(outroHandler);
	}

	

	public void paint(Graphics g) {
		Toolkit.getDefaultToolkit().sync();
		draw(g);
	}

	public void draw(Graphics g) {
		gameStack.peek().draw(g, this);
		if (gameStack.peek() == levelHandler && levelHandler.currLev == 0) {
			lev1Music.play();
		} else {
			lev1Music.pause();
		} 
		if (gameStack.peek() == levelHandler && levelHandler.currLev == 1) {
			lev2Music.play();
		} else {
			lev2Music.pause();
		}
		if (gameStack.peek() == levelHandler && levelHandler.currLev == 2) {
			lev3Music.play();
		} else {
			lev3Music.pause();
		}

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
