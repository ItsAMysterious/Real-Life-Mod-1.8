package itsamysterious.mods.reallifemod.core.sounds;


import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.util.ResourceLocation;

public class CustomSound extends MovingSound {
	public static Map<String, CustomSound> Sounds = new HashMap<String, CustomSound>();
	public int length;

	public CustomSound(ResourceLocation location, int i) {
		super(location);
		this.length = i;
		Sounds.put(location.getResourcePath(), this);
	}

	@Override
	public void update() {
	}

	public void stop() {
		donePlaying = true;
	}

	public void setPosition(float x, float y, float z) {
		this.xPosF = x;
		this.yPosF = y;
		this.zPosF = z;
	}

	public void setPitch(float newPitch) {
		this.pitch = newPitch;
	}

	public void addPitch(float f) {
		this.pitch += f;
	}

	public static CustomSound getSoundWithPosition(String sound, float posX, float posY, float posZ,  float volume, float pitch) {
		CustomSound s = Sounds.get(sound);
		if(s!=null){
			Sounds.get(sound).setPosition(posX, posY, posZ);
			Sounds.get(sound).setPitch(pitch);
			Sounds.get(sound).volume=volume;
			return Sounds.get(sound);
		}else
		return null;
	}

}
