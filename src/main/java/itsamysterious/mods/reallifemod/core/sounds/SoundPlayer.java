package itsamysterious.mods.reallifemod.core.sounds;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;

public class SoundPlayer {
	
	private static List<ISound> sounds = new ArrayList<ISound>();
	private ISound currentSound;
	private int ticksPlaying;
	
	
	public void addSound(ISound s){
		sounds.add(s);
	}
	
	public void playSoundAtEntity(Entity e, String s, float volume, float pitch){
		for(ISound i:sounds){
			if(i.name==s){
				currentSound=i;
				e.playSound(i.name, volume, pitch);
			}
		}
	}
	
	public boolean isPlaying(){
		return ticksPlaying<currentSound.length;
	}
	
	public  void updatePlaying(){
		if(currentSound!=null){
			ticksPlaying++;
		}
		if(!isPlaying()){
			currentSound=null;
			ticksPlaying=0;
		}
	}
}
