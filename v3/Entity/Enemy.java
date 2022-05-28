package Entity;

import java.awt.*;

import LevelRelated.Level;

public abstract class Enemy extends Entity{

    protected boolean falling;
    protected boolean setDirectionRight;
    
    public abstract void tick(Level level);

    public abstract Rectangle getBounds();
    
}

   