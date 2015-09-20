package itsamysterious.mods.reallifemod.core.vehicles;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioFileFormat.Type;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class VehicleRadio {
	private static List<String> songs =new ArrayList<String>();
	public boolean isPlaying;
	private Clip currentTrack;
	public VehicleRadio() {
	}
	
	public void play(String file){
		try {
	         File f = new File(file);
	         AudioInputStream audioIn = AudioSystem.getAudioInputStream(f);
	         currentTrack = AudioSystem.getClip();
	         currentTrack.open(audioIn);
	         currentTrack.setFramePosition(0);
	         currentTrack.start();
	      } catch (UnsupportedAudioFileException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      } catch (LineUnavailableException e) {
	         e.printStackTrace();
	      }	
	}
	
	public void stop(){
		currentTrack.stop();
	}

	public static void addSong(String path) {
		songs.add(path);
	}
}
