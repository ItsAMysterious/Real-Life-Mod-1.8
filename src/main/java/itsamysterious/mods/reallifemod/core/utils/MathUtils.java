package itsamysterious.mods.reallifemod.core.utils;

import org.lwjgl.util.vector.Vector3f;

import com.ibm.icu.text.DecimalFormat;

import net.minecraft.util.BlockPos;

public class MathUtils {

	public static Vector3f blockPosVec(BlockPos pos) {
		return new Vector3f((float) pos.getX(), (float) pos.getY(), (float) pos.getZ());
	}

	public static double roundToDecimal(double d) {
		DecimalFormat df2 = new DecimalFormat("###.##");
		return Double.valueOf(df2.format(d));
	}

	public static Vector3f vectorFromDouble(double x, double y, double z) {
		return new Vector3f((float) x, (float) y, (float) z);
	}

}
