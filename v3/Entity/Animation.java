package Entity;
import java.awt.*;
import java.io.File;

import javax.imageio.ImageIO;

public class Animation {
    public static Image babyPineappleLeft1;
    public static Image babyPineappleLeft2;
    public static Image babyPineappleRight1;
    public static Image babyPineappleRight2;
    public static Image babyPineappleNoMove;

    public Animation() {

    }

    public static void loadImg() {
        try {
			babyPineappleLeft1 = ImageIO.read(new File("./images/walkLeft1.png"));

		} catch (Exception e) {
			//TODO: handle exception
		}
        try {
			babyPineappleLeft2 = ImageIO.read(new File("./images/walkLeft2.png"));

		} catch (Exception e) {
			//TODO: handle exception
		}
        try {
			babyPineappleRight1 = ImageIO.read(new File("./images/walkRight1.png"));

		} catch (Exception e) {
			//TODO: handle exception
		}
        try {
			babyPineappleRight2 = ImageIO.read(new File("./images/walkRight2.png"));

		} catch (Exception e) {
			//TODO: handle exception
		}
        try {
			babyPineappleNoMove = ImageIO.read(new File("./images/normal.png"));

		} catch (Exception e) {
			//TODO: handle exception
		}
		
		
    }

    public static Image getLeftWalk1() {
        return babyPineappleLeft1;
    }

    public static Image getLeftWalk2() {
        return babyPineappleLeft2;
    }

    public static Image getRightWalk1() {
        return babyPineappleRight1;
    }

    public static Image getRightWalk2() {
        return babyPineappleRight2;
    }

    public static Image getNormalBaby() {
        return babyPineappleNoMove;
    }
}
