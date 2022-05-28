package Handlers;


import java.awt.*;
import java.awt.event.*;

public abstract class Handler {
    public abstract void draw(Graphics g, DriverRunner driver);
    public abstract void tick(DriverRunner driver);

    public abstract void keyPressed(KeyEvent e);

    public abstract void keyReleased(KeyEvent e);
}
