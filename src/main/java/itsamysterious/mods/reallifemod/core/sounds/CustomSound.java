package itsamysterious.mods.reallifemod.core.sounds;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class CustomSound extends MovingSound {


	public CustomSound(ResourceLocation location) {
		super(location);
	}

	@Override
	public void update() {
	}
	
	public void stop(){
		donePlaying=true;
	}
	
	public void setPitch(float newPitch){
		this.pitch=newPitch;
	}
	
	public void addPitch(float f){
		this.pitch+=f;
	}

}
