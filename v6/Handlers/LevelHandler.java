package Handlers;


import LevelRelated.Level;

/***
 * Handler Class for Levels
 */

import LevelRelated.TileMap;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Entity.Player;

public class LevelHandler extends Handler { // Graphics to handle events during a level

	public ArrayList<Level> levels;

    public DriverRunner driver;

	public int currLev;

    public LevelHandler(DriverRunner driverRunner) {
		levels = new ArrayList<Level>();
        currLev = 0;
        loadLev();
    }

	public void loadLev() {
		levels.add(new Level(1, true)); 
		levels.add(new Level(2, true));
		levels.add(new Level(3, true));
		// levels.add(new Level(4));
		// levels.add(new Level(5));
		// levels.add(new Level(6));
		// levels.add(new Level(7));
		// levels.add(new Level(8));

	}

	public void tick(DriverRunner driver) {
		driver.overworldHandler.start = false;
		if (levels.get(currLev).isDone()) {
			
			if (currLev == driver.overworldHandler.latestLev) driver.overworldHandler.latestLev++;
			
			driver.gameStack.pop();
			if (driver.overworldHandler.latestLev >= OverworldHandler.finalLev) {
				driver.gameStack.push(driver.outroHandler);

			}
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				//TODO: handle exception
			}
			driver.overworldHandler.smoothing = false;
			driver.gameStack.push(driver.courseClearHandler);
		}
		if (levels.get(currLev).isDead()) {
			// levels.set(currLev, new Level(currLev + 1));
			driver.overworldHandler.player.setLives(driver.overworldHandler.player.getLives() - 1);
			try {
				levels.get(currLev).musicthing.stop();

			} catch (Exception e) {
				//TODO: handle exception
			}
			driver.gameStack.pop();
			if (Player.getLives() <= 0) {
				try {
					Thread.sleep(100);
				} catch (Exception e) {
					//TODO: handle exception
				}
				driver.gameStack.push(driver.deathScreenHandler);
			}
			
			
			
		}
		// empty here will add sutff later
	}

    public void draw(Graphics g, DriverRunner driver) {
		g.setColor(Color.WHITE);
		g.clearRect(0, 0, 800, 600);
		g.fillRect(0, 0, 800, 600);

		
		tick(driver);
        levels.get(currLev).draw(g, driver);
		g.drawImage(levels.get(currLev).lives, 15, 20, driver);
		g.setFont(new Font("Serif", Font.BOLD, 25));
        g.setColor(Color.YELLOW); 
        g.drawString("x",  50, 40);
        g.drawString(Integer.toString(levels.get(currLev).player.getLives()),  60, 40);
		g.drawImage(levels.get(currLev).lives, 15, 60, driver);
        g.setColor(Color.YELLOW);
        g.drawString("x",  50, 60);
        g.drawString(Integer.toString(Player.getCoins()),  60, 60);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		levels.get(currLev).keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		levels.get(currLev).keyReleased(e);

	}

   

}
