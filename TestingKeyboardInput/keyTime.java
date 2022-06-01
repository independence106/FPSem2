import javax.swing.JFrame;

public class keyTime{
  private JFrame game;

  public keyTime(){
    game = new JFrame();
    game.addKeyListener( new listener() );
    game.setVisible(true);
  }

  public static void main(String[] args) {
    keyTime bob = new keyTime();
  }
}
