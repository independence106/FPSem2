package Entity;

import LevelRelated.Level;
import java.awt.*;

public abstract class Enemy extends Entity{

    protected boolean falling;
    protected boolean setDirectionRight;
    
    public abstract void tick(Level level);

    public abstract Rectangle getBounds();
    
}

   