
package Entity;
import java.awt.*;

import Handlers.DriverRunner;
import LevelRelated.Level;

public abstract class Entity {

    public double xPos;
    public double yPos;
    public int height;
    public int width;
    public int yVelo;
    public int xVelo;
    public boolean falling;
    public abstract void tick(Level level);
    public abstract void draw(Graphics g, DriverRunner driverRunner);

    public abstract Rectangle getBounds();

    public String getId() {
        return "entity";
    }

}
