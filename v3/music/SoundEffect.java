package music;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundEffect implements Runnable{

	AudioInputStream stream;
	Clip clip;
	String path;

	public enum Types {
		JUMP,
		COIN
	}
	
	public SoundEffect() {
		this.path = "./music/jump.wav";
		load();
	}

	public void load() {
		try {
			stream = AudioSystem.getAudioInputStream(new File(path));
			clip = AudioSystem.getClip();
			clip.open(stream);
		} catch (Exception e) {
			//TODO: handle exception
		}
		
	}

	public void setCoin() {
		this.path = "./music/coin.wav";
		load();
	}

	public void setPowerUp() {
		this.path = "./music/powerUp.wav";
		load();
	}

	public void setJump() {
		this.path = "./music/jump.wav";
		load();
	}

	public void setCourseClear() {
		this.path = "./music/courseclear.mid";
		load();
	}

	public void setCredits() {
		this.path = "./music/credits.wav";
		load();
	}

	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		clip.start();
		
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                //TODO: handle exception
        }	
		clip.setFramePosition(0);	
	}
}