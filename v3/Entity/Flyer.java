//first type of enemy(gomba)
package Entity;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import Blocks.Block;
import Blocks.ButtonFlag;
import LevelRelated.Level;
import LevelRelated.TileMap;
import Settings.MapSettings;

public class Flyer extends Enemy {

    public Flyer() {
        startup();
    }

    public Flyer(int x, int y) {
        startup();
        xPos = x;
        yPos = y;
    }

    public void startup() {
        width = 30;
        height = 30;
        yVelo = -4;
        xVelo = 0;
        setDirectionRight = true;
        falling = true;
    }

    public void rigidCollision(Level level) {
        for (int i = 0; i < level.levMap.rigidBlocks.size(); i++) {

            if (getBottomBounds().intersects(level.levMap.rigidBlocks.get(i).getBounds())) {
                yVelo = 0;
            }
            if (getRightBounds().intersects(level.levMap.rigidBlocks.get(i).getBounds())) {
                xPos = level.levMap.rigidBlocks.get(i).getX() - width;
                xVelo *= -1;
            }

            if (getLeftBounds().intersects(level.levMap.rigidBlocks.get(i).getBounds())) {
                xPos = level.levMap.rigidBlocks.get(i).getX() + MapSettings.tileSize;
                xVelo *= -1;
            }

        }
    }

    //
    public void nonRigidCollision(Level level) {

    }

    public Rectangle getBounds() {
        return new Rectangle((int) xPos, (int) yPos, width, height);
    }

    public Rectangle getRightBounds() {
        return new Rectangle((int) xPos + width - 4, (int) yPos, 4, height - 4);
    }

    public Rectangle getLeftBounds() {
        return new Rectangle((int) xPos + 1, (int) yPos, 4, height - 4);
    }

    public Rectangle getBottomBounds() {
        return new Rectangle((int) xPos + 1, (int) yPos + height - 4, width - 1, 5); // 4 is arbitrary
    }

    public double getX() {
        return xPos;
    }

    @Override
    public void tick(Level level) {
        // TODO Auto-generated method stub

        if (falling) {
            yVelo = -6;
        }
        rigidCollision(level);
        xPos -= xVelo;
        yPos += yVelo;
        rigidCollision(level);
    }

    @Override
    public void draw(Graphics g) {

        // TODO Auto-generated method stub

        g.setColor(Color.ORANGE);
        g.fillRect((int) xPos, (int) yPos, width, height);
    }

}