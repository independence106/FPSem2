package Entity;
import java.awt.*;
import java.io.File;

import javax.imageio.ImageIO;

public class Animation {
    public static Image babyPineapple1;
    public static Image babyPineapple2;

    public Animation() {

    }

    public static void loadImg() {
        try {
			babyPineapple1 = ImageIO.read(new File("./images/walk1.png"));

		} catch (Exception e) {
			//TODO: handle exception
		}
        try {
			babyPineapple2 = ImageIO.read(new File("./images/walk2.png"));

		} catch (Exception e) {
			//TODO: handle exception
		}
		
		
    }

    public static Image getWalk1() {
        return babyPineapple1;
    }

    public static Image getWalk2() {
        return babyPineapple2;
    }
}
