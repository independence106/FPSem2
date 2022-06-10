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

    public static Image normalPineappleLeft1;
    public static Image normalPineappleLeft2;
    public static Image normalPineappleRight1;
    public static Image normalPineappleRight2;
    public static Image normalPineappleNoMove;

    public static Image bossLeft;
    public static Image bossRight;
    public static Image bossDead;

    public static Image dabLeft;
    public static Image dabRight;

    public Animation() {
        loadImg();
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
        try {
			normalPineappleLeft1 = ImageIO.read(new File("./images/walkLeftAdult1.png"));

		} catch (Exception e) {
			//TODO: handle exception
		}
        try {
			normalPineappleLeft2 = ImageIO.read(new File("./images/walkLeftAdult2.png"));

		} catch (Exception e) {
			//TODO: handle exception
		}
        try {
			normalPineappleRight1 = ImageIO.read(new File("./images/walkRightAdult1.png"));

		} catch (Exception e) {
			//TODO: handle exception
		}
        try {
			normalPineappleRight2 = ImageIO.read(new File("./images/walkRightAdult2.png"));

		} catch (Exception e) {
			//TODO: handle exception
		}
        try {
			normalPineappleNoMove = ImageIO.read(new File("./images/normalAdult.png"));

		} catch (Exception e) {
			//TODO: handle exception
		}
        try {
			bossLeft = ImageIO.read(new File("./images/bossLeft.png"));

		} catch (Exception e) {
			//TODO: handle exception
		}
        bossLeft = bossLeft.getScaledInstance(80, 80, bossLeft.SCALE_DEFAULT);
        try {
			bossRight = ImageIO.read(new File("./images/bossRight.png"));

		} catch (Exception e) {
			//TODO: handle exception
		}
        bossRight = bossRight.getScaledInstance(80, 80, bossRight.SCALE_DEFAULT);
        try {
			bossDead = ImageIO.read(new File("./images/bossDead.png"));

		} catch (Exception e) {
			//TODO: handle exception
		}
        bossDead = bossDead.getScaledInstance(80, 80, bossDead.SCALE_DEFAULT);

        try {
			dabRight = ImageIO.read(new File("./images/dabRight.png"));

		} catch (Exception e) {
			//TODO: handle exception
		}
        dabRight = dabRight.getScaledInstance(400, 400, dabRight.SCALE_DEFAULT);

        try {
			dabLeft = ImageIO.read(new File("./images/dabLeft.png"));

		} catch (Exception e) {
			//TODO: handle exception
		}
        dabLeft = dabLeft.getScaledInstance(400, 400, dabLeft.SCALE_DEFAULT);

		
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
    public static Image getLeftWalkAdult1() {
        return normalPineappleLeft1;
    }

    public static Image getLeftWalkAdult2() {
        return normalPineappleLeft2;
    }

    public static Image getRightWalkAdult1() {
        return normalPineappleRight1;
    }

    public static Image getRightWalkAdult2() {
        return normalPineappleRight2;
    }

    public static Image getNormalAdult() {
        return normalPineappleNoMove;
    }

    public static Image getBossLeft() {
        return bossLeft;
    }

    public static Image getBossRight() {
        return bossRight;
    }

    public static Image getBossDead() {
        return bossDead;
    }

    public static Image getDabRight() {
        return dabRight;
    }

    public static Image getDabLeft() {
        return dabLeft;
    }
}
