package Entity;

import java.awt.*;

import LevelRelated.Level;

public abstract class Enemy extends Entity{

    protected boolean falling;
    protected boolean setDirectionRight;
    
    public abstract void tick(Level level);

    public void rigidCollision(Level level) {
       
    }
    //
    public void nonRigidCollision(Level level) { 
        
    }


    public void flipDir() {
        xVelo *= -1;
    }
    
}

   