
package Entity;
import java.awt.*;

public abstract class Entity {

    public double xPos;
    public double yPos;
    public int height;
    public int width;
    public int yVelo;
    public int xVelo;
    public boolean falling;
    public abstract void draw(Graphics g);
}
