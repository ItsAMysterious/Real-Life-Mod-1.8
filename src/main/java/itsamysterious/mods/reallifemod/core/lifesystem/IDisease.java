package itsamysterious.mods.reallifemod.core.lifesystem;

import net.minecraft.potion.PotionEffect;

public interface IDisease {
	public PotionEffect[]getEffects();
	public void onActive();
	
}
