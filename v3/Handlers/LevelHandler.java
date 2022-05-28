package Handlers;


import LevelRelated.Level;

/***
 * Handler Class for Levels
 */

import LevelRelated.TileMap;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class LevelHandler extends Handler { // Graphics to handle events during a level

	public ArrayList<Level> levels;

    public DriverRunner driverRunner; // I don't think this is needed atm

	public int currLev;

    public LevelHandler(DriverRunner driverRunner) {
		levels = new ArrayList<Level>();
        currLev = 0;
        loadLev();
    }

	public void loadLev() {
		levels.add(new Level(1)); 
		levels.add(new Level(2));
		// levels.add(new Level(3));
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

		}
		if (levels.get(currLev).isDead()) {
			// levels.set(currLev, new Level(currLev + 1));
			driver.overworldHandler.player.setLives(driver.overworldHandler.player.getLives() - 1);

			driver.gameStack.pop();
			
			
		}
		// empty here will add sutff later
	}

    public void draw(Graphics g, DriverRunner driver) {
		g.clearRect(0, 0, 800, 600);
		g.fillRect(0, 0, 800, 600);

		
		tick(driver);
        levels.get(currLev).draw(g, driver);
		g.drawImage(levels.get(currLev).lives, 15, 20, driver);
        g.setColor(Color.WHITE);
        g.drawString("x",  50, 40);
        g.drawString(Integer.toString(levels.get(currLev).player.getLives()),  60, 40);
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
