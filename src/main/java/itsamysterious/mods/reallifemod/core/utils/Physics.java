package itsamysterious.mods.reallifemod.core.utils;

import java.util.HashMap;
import java.util.Map;

import itsamysterious.mods.reallifemod.core.RealLifeMod_Blocks;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class Physics {
	private static final Map<Block, Float> rollDragCoefficients = new HashMap<Block, Float>();
	public static final double g = -9.81;

	public Physics() {

	}

	public static float getRollDragCoefficientForBlock(Block block) {
		if (rollDragCoefficients.containsKey(block)) {
			return rollDragCoefficients.get(block);
		} else
			return 0.050f;
	}

	public static void init() {
		rollDragCoefficients.put(Blocks.dirt, 0.050f);
		rollDragCoefficients.put(Blocks.grass, 0.055f);
		rollDragCoefficients.put(RealLifeMod_Blocks.tarmac, 0.013f);
		rollDragCoefficients.put(Blocks.stone, 0.015f);
		rollDragCoefficients.put(Blocks.cobblestone, 0.0225f);
		rollDragCoefficients.put(Blocks.sand, 0.3f);
		rollDragCoefficients.put(Blocks.gravel, 0.02f);
	}

}
