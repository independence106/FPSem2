package LevelRelated;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.text.AttributeSet.ColorAttribute;

import java.awt.*;
import java.awt.image.*;

public class BackGroundDrawer {
    public Image imag2;
    public int imag2x;
    public int imag2y;	

	public BackGroundDrawer() {

	}

	public BackGroundDrawer(String filepath) {
		loadImg(filepath);
	}


	// loads image specified by path
    public void loadImg(String path) {
		try {
			imag2 = ImageIO.read(new File("logo.png"));

		} catch (Exception e) {
			//TODO: handle exception
		}
		imag2 = imag2.getScaledInstance(800, 600, imag2.SCALE_DEFAULT);
		

	}

    public void draw(Graphics g, ImageObserver o) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 800, 600);
        g.drawImage(imag2, 0, 0, o);
    }

}