package itsamysterious.mods.reallifemod.core.blocks.tiles;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import itsamysterious.mods.reallifemod.core.tiles.RLMTileEntity;
import itsamysterious.mods.reallifemod.init.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TileEntity_Ramp extends RLMTileEntity implements IUpdatePlayerListBox {
	public final List<Entity> entities = new ArrayList<Entity>();
	private int depth;
	private int width;
	private boolean whiteHigh;
	private int numPoints;
	private float widthScale;
	private float heightScale;
	private static BufferedImage heightfile;
	private static float[][] heights;

	public TileEntity_Ramp() {
		try {
			InputStream imagefile = Minecraft.getMinecraft().getResourceManager()
					.getResource(new ResourceLocation(Reference.ID + ":textures/blocks/rampmap.png")).getInputStream();
			this.heightfile = ImageIO.read(imagefile);
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.heightScale = 1;
		this.widthScale = 1;
		if (heightfile != null) {
			setupHeights();
		}
	}

	public void setupHeights() {
		int pixWidth = heightfile.getWidth();
		int pixHeight = heightfile.getHeight();
		int w = pixWidth;
		int h = pixHeight;
		this.heights = new float[w][h];
		boolean countWidth = true;
		for (int z = 0; z < pixHeight; z++) {
			depth++;
			for (int x = 0; x < pixWidth; x++) {
				if (countWidth)
					width++;
				int height;
				if (whiteHigh) {
					height = 256 - (-1 * heightfile.getRGB(x, z));
				} else {
					height = -1 * heightfile.getRGB(x, z);
				}
				heights[x][z] = height;
				numPoints++;
			}
			countWidth = false;
		}
		System.out.println("Heights have been set up!");
	}

	@Override
	public void update() {
		if (!this.hasWorldObj())
			return;
		for (Entity e : entities) {
			if (e == null || (e.posX < pos.getX() || e.posZ < pos.getZ() || e.posX > pos.getX() + 1
					|| e.posZ > pos.getZ() + 1)) {
				return;
			}
			if (e != null) {
				{
					double localX = e.posX % 1;
					double localZ = e.posZ % 1;
					System.out.println(rotation);
					localX = Math.abs(localX*Math.sin(Math.toRadians(rotation*90)));
					localZ = Math.abs(localZ*Math.cos(Math.toRadians(rotation*90)));
					System.out.println(localX);

					int x = (int) (localX * width);
					int y = (int) (localZ * depth);
					if (heightfile != null) {
						if (e.posY < pos.getY() + getHeight(x, y)) {
							e.posY = pos.getY() + getHeight(x, y);
							if (e.riddenByEntity != null) {
								e.riddenByEntity.posY = pos.getY() + getHeight(x, y);
							}
						}
					}

				}
			}
		}
	}

	public float getHeight(float xf, float zf) {
		int x = worldCoordToIndex(xf);
		int z = worldCoordToIndex(zf);
		if (x < 0)
			x = 0;
		if (z < 0)
			z = 0;
		if (z >= heights.length) {
			z = heights.length - 1;
		}
		if (x >= heights[z].length) {
			x = heights[z].length - 1;
		}
		return heights[z][x] / 256 / 256 / 256;

	}

	private int worldCoordToIndex(float f) {
		return (int) Math.floor(f / 1);
	}
}