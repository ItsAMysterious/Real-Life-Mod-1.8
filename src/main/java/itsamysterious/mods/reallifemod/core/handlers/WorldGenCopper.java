package itsamysterious.mods.reallifemod.core.handlers;

import java.util.Random;

import itsamysterious.mods.reallifemod.core.RealLifeMod_Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGenCopper extends WorldGenerator implements IWorldGenerator {

	private WorldGenerator gen_copperore; 

	public WorldGenCopper() {
		this.gen_copperore = new WorldGenMinable(RealLifeMod_Blocks.copperore.getDefaultState(), 8);
	}

	@Override
	public boolean generate(World world, Random random, BlockPos pos) {
		switch (world.provider.getDimensionId()) {
		case 0: // Overworld
			Chunk chunk=world.getChunkFromBlockCoords(pos);
			this.runGenerator(this.gen_copperore, world, random, chunk.xPosition, chunk.zPosition, 20, 0, 64);
			break;
		case -1: // Nether

			break;
		case 1: // End

			break;
		}
		return true;
	}

	private void runGenerator(WorldGenerator generator, World world, Random rand, int chunk_X, int chunk_Z,
			int chancesToSpawn, int minHeight, int maxHeight) {
		if (minHeight < 0 || maxHeight > 256 || minHeight > maxHeight)
			throw new IllegalArgumentException("Illegal Height Arguments for WorldGenerator");

		int heightDiff = maxHeight - minHeight + 1;
		for (int i = 0; i < chancesToSpawn; i++) {
			int x = chunk_X * 16 + rand.nextInt(16);
			int y = minHeight + rand.nextInt(heightDiff);
			int z = chunk_Z * 16 + rand.nextInt(16);
			generator.generate(world, rand, new BlockPos(x, y, z));
		}
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
			IChunkProvider chunkProvider) {
		
	}

}
