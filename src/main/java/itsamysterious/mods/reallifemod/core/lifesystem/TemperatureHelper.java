package itsamysterious.mods.reallifemod.core.lifesystem;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBeach;
import net.minecraft.world.biome.BiomeGenDesert;
import net.minecraft.world.biome.BiomeGenEnd;
import net.minecraft.world.biome.BiomeGenForest;
import net.minecraft.world.biome.BiomeGenHell;
import net.minecraft.world.biome.BiomeGenHills;
import net.minecraft.world.biome.BiomeGenJungle;
import net.minecraft.world.biome.BiomeGenMesa;
import net.minecraft.world.biome.BiomeGenMushroomIsland;
import net.minecraft.world.biome.BiomeGenMutated;
import net.minecraft.world.biome.BiomeGenOcean;
import net.minecraft.world.biome.BiomeGenPlains;
import net.minecraft.world.biome.BiomeGenRiver;
import net.minecraft.world.biome.BiomeGenSavanna;
import net.minecraft.world.biome.BiomeGenSnow;
import net.minecraft.world.biome.BiomeGenStoneBeach;
import net.minecraft.world.biome.BiomeGenSwamp;

public class TemperatureHelper {
	
	public float getTemperaturesForBiomes(BiomeGenBase biome, EntityPlayer player){
		if(biome instanceof BiomeGenBeach){
			return 30;
		}
		if(biome instanceof BiomeGenHills){
			if(player.posY>64){
				return (float) (18-player.posY-64/250);
			}
			else
			return 18;
		}
		if(biome instanceof BiomeGenDesert){
			return 40;
		}
		
		if(biome instanceof BiomeGenForest){
			return 22;
		}
		
		if(biome instanceof BiomeGenEnd){
			return -23;
		}
		
		if(biome instanceof BiomeGenHell){
			return 40;
		}
		
		if(biome instanceof BiomeGenJungle){
			return -25;
		}
		
		if(biome instanceof BiomeGenOcean){
			return 15;
		}
		
		if(biome instanceof BiomeGenPlains){
			return 23;
		}
		
		if(biome instanceof BiomeGenRiver){
			return 19;
		}
		
		if(biome instanceof BiomeGenSavanna){
			return 33;
		}
		
		if(biome instanceof BiomeGenSwamp){
			return 21;
		}
		
		if(biome instanceof BiomeGenSnow){
			return -5;
		}
		
		if(biome instanceof BiomeGenStoneBeach){
			return 28;
		}
		
		if(biome instanceof BiomeGenMushroomIsland){
			return 23;
		}
		
		if(biome instanceof BiomeGenMutated){
			return (float) (Math.random()*30);
		}
		
		if(biome instanceof BiomeGenMesa){
			return (float) (Math.random()*30);
		}
		
		return 21.3f;
	}

}
