package itsamysterious.mods.reallifemod.core;

import org.lwjgl.util.vector.Vector3f;

import com.ibm.icu.text.DecimalFormat;

import net.minecraft.util.BlockPos;

public class MathUtil {

	public static Vector3f blockPosVec(BlockPos pos) {
		return new Vector3f((float)pos.getX(), (float)pos.getY(), (float)pos.getZ());
	}
	
	public static double roundToDecimal(double d){
		DecimalFormat df2 = new DecimalFormat("###.##");
		return Double.valueOf(df2.format(d));
	}

}
