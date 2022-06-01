import javax.swing.*;

import Handlers.DriverRunner;

public class Woo extends JFrame{

	DriverRunner panel;
	
	Woo(){
		panel = new DriverRunner();
		this.add(panel);
		this.setTitle("The Quest For The Purple Pineapple");
		this.setResizable(true);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	}
    public static void main(String[] args) {
        Woo driver = new Woo();
    }
}