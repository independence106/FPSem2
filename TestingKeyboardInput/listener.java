import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.lang.*;

public class listener extends KeyAdapter{
  long press;//when a key was pressed (milliseconds)
  long release;//when a key was released (milliseconds)

  public void keyPressed(KeyEvent e){
    press = System.currentTimeMillis();
  }

  public void keyReleased(KeyEvent e){
    release = System.currentTimeMillis();
    System.out.println( release - press);
  }
}
