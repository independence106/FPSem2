package Handlers;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

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

    public static void addScore(int level, int score) {
        doublyLinkedList.get(level).addScore(score);
    }

    public static void setDeaths(int level, int deaths) {
        doublyLinkedList.get(level).setDeaths(deaths);
    }

    public static void addDeath(int level) {
        doublyLinkedList.get(level).setDeaths(doublyLinkedList.get(level).deaths + 1);
    }


    @Override
    public void draw(Graphics g, DriverRunner driver) {
        LinkedList<Integer> scores = new LinkedList<Integer>();

        for (int i = 0; i < doublyLinkedList.get(currSelection).scores.size(); i++) {
            if (i > 5) {
                break;
            }
            scores.add(doublyLinkedList.get(currSelection).scores.get(i));
        }
        QuickSort.qsort(scores);
        System.out.println(doublyLinkedList.size());
        // TODO Auto-generated method stub
        g.clearRect(0, 0, 800, 600);
        g.setColor(Color.LIGHT_GRAY);
        g.setFont(new Font("Sans Serif", Font.PLAIN, 30));
        g.drawString("LEVEL " + Integer.toString(currSelection + 1), 50, 50);
        g.drawString("Deaths Total: " + doublyLinkedList.get(currSelection).deaths, 50, 100);
        g.drawString("Highest Scores:\n", 50, 150);
        for (int i = 0; i < scores.size(); i++) {
            g.drawString(Integer.toString(scores.get(i)), 70, 200 + i * 50);
            
        }
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
        if (e.getKeyCode() == KeyEvent.VK_D && currSelection < doublyLinkedList.size()) {
            currSelection++;
        }
        if (e.getKeyCode() == KeyEvent.VK_A && currSelection > 0) {
            currSelection--;
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
