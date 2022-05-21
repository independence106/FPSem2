package LevelRelated;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;

public class BackGroundDrawer {
    public BufferedImage imag2;
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
			imag2 = ImageIO.read(new File(path));

		} catch (Exception e) {
			//TODO: handle exception
		}
		imag2x = imag2.getWidth();
		imag2y = imag2.getHeight();
	}

    public void draw(Graphics g, ImageObserver o) {
        g.drawImage(imag2, 0, 0, o);
    }

}