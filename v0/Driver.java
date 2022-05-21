import javax.swing.*;

import Handler.DriverRunner;

public class Driver extends JFrame{

	DriverRunner panel;
	
	Driver(){
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
        Driver driver = new Driver();
    }
}