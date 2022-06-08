package Handlers;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.imageio.ImageIO;

import LevelRelated.Level;
import LevelRelated.LevelStat;
import Stats.*;

public class Stats extends Handler {

    public static LLList<LevelStat> doublyLinkedList;

    public int currSelection;

    public boolean setQuit;

    public Stats() {
        doublyLinkedList = new LList<LevelStat>();
        currSelection = 0;
        setQuit = false;
        startup();
    }

    public void startup() {
        for (int i = 0; i < OverworldHandler.finalLev; i++) {
            doublyLinkedList.add(new LevelStat());
        }
    }

    public void loadImg() {
    }

    public static void setScore(int level, int score) {
        doublyLinkedList.get(level).setScore(score);
    }

    public static void setDeaths(int level, int deaths) {
        doublyLinkedList.get(level).setDeaths(deaths);
    }

    public static void addDeath(int level) {
        doublyLinkedList.get(level).setDeaths(doublyLinkedList.get(level).deaths + 1);
    }


    @Override
    public void draw(Graphics g, DriverRunner driver) {
        // TODO Auto-generated method stub
        g.drawString("Deaths: " + doublyLinkedList.get(selec), arg1, arg2);
        tick(driver);
    }

    @Override
    public void tick(DriverRunner driver) {
        // TODO Auto-generated method stub
        if (setQuit) {
            setQuit = false;
            driver.gameStack.pop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        if (e.getKeyCode() == KeyEvent.VK_D) {

        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            setQuit = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }
    
}
