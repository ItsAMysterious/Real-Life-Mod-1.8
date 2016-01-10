package itsamysterious.mods.reallifemod.core;

import java.util.ArrayList;
import java.util.List;

import itsamysterious.mods.reallifemod.RealLifeMod;
import itsamysterious.mods.reallifemod.core.blocks.Block_Computer;
import itsamysterious.mods.reallifemod.core.blocks.Block_CopperOre;
import itsamysterious.mods.reallifemod.core.blocks.Block_Drawer;
import itsamysterious.mods.reallifemod.core.blocks.Block_DrinksFridge;
import itsamysterious.mods.reallifemod.core.blocks.Block_GasTank;
import itsamysterious.mods.reallifemod.core.blocks.Block_IronFence;
import itsamysterious.mods.reallifemod.core.blocks.Block_Parquet;
import itsamysterious.mods.reallifemod.core.blocks.Block_PetrolPump;
import itsamysterious.mods.reallifemod.core.blocks.Block_Railing;
import itsamysterious.mods.reallifemod.core.blocks.Block_Ramp;
import itsamysterious.mods.reallifemod.core.blocks.Block_Tarmac;
import itsamysterious.mods.reallifemod.core.blocks.Block_Television;
import itsamysterious.mods.reallifemod.core.blocks.Block_VendingMachine;
import itsamysterious.mods.reallifemod.core.blocks.electrisity.Block_Cable;
import itsamysterious.mods.reallifemod.core.blocks.electrisity.Block_PictureFrame;
import itsamysterious.mods.reallifemod.core.blocks.electrisity.Block_Pinwheel;
import itsamysterious.mods.reallifemod.core.blocks.electrisity.Block_PowerLine;
import itsamysterious.mods.reallifemod.core.blocks.electrisity.Block_Streetlight;
import itsamysterious.mods.reallifemod.core.blocks.electrisity.Block_Transformer;
import itsamysterious.mods.reallifemod.core.blocks.furniture.Block_Dartboard;
import itsamysterious.mods.reallifemod.core.blocks.furniture.Block_Shelf;
import itsamysterious.mods.reallifemod.core.blocks.furniture.Block_TVTable;
import itsamysterious.mods.reallifemod.core.blocks.furniture.Block_Table;
import itsamysterious.mods.reallifemod.core.blocks.sanitary.Block_Washingbasin;
import itsamysterious.mods.reallifemod.core.blocks.sanitary.Block_Toilet;
import itsamysterious.mods.reallifemod.core.blocks.sanitary.Block_Urinal;
import itsamysterious.mods.reallifemod.init.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class RealLifeMod_Blocks {
	public static List<Block> blockList = new ArrayList<Block>();
	public static List<Block> blocks3d = new ArrayList<Block>();

	// Blockvariables
	public static Block block_TV, block_Drawer, block_Computer, parquet, toilet, washbasin, petrolPump, tarmac, gastank,
			lantern, vendingMachine, drinksFridge, powerLine, transformer, cable, pinwheel, copperore, pictureframe,
			ramp, tvtable, shelf, blockironfence, railing, dartBoard, table, chair,urinal;

	public static void defineBlocks() {
		blockList.add(block_TV = new Block_Television());
		blockList.add(block_Drawer = new Block_Drawer());
		blockList.add(block_Computer = new Block_Computer());
		blockList.add(toilet = new Block_Toilet().setCreativeTab(RealLifeMod.RLMFurniture));
		blockList.add(washbasin = new Block_Washingbasin().setCreativeTab(RealLifeMod.RLMFurniture));
		blockList.add(petrolPump = new Block_PetrolPump());
		blockList.add(parquet = new Block_Parquet().setCreativeTab(RealLifeMod.RLMDeco));
		blockList.add(tarmac = new Block_Tarmac(Material.rock).setUnlocalizedName("blocktarmac")
				.setCreativeTab(RealLifeMod.Cars));
		blockList.add(gastank = new Block_GasTank().setUnlocalizedName("blockgastank").setCreativeTab(RealLifeMod.Cars));
		blockList.add(vendingMachine = new Block_VendingMachine().setCreativeTab(RealLifeMod.RLMDeco));
		blockList.add(drinksFridge = new Block_DrinksFridge().setCreativeTab(RealLifeMod.RLMDeco));
		blockList.add(powerLine = new Block_PowerLine());
		blockList.add(transformer = new Block_Transformer());
		blockList.add(cable = new Block_Cable().setCreativeTab(RealLifeMod.Technologie));
		blockList.add(pinwheel = new Block_Pinwheel());
		blockList.add(copperore = new Block_CopperOre().setCreativeTab(RealLifeMod.Technologie));
		blockList.add(pictureframe = new Block_PictureFrame(Material.circuits));
		blockList.add(ramp = new Block_Ramp());
		blockList.add(tvtable = new Block_TVTable());
		blockList.add(shelf = new Block_Shelf());
		blockList.add(blockironfence = new Block_IronFence(Material.iron));
		blockList.add(railing = new Block_Railing());
		blockList.add(table=new Block_Table());
		blockList.add(urinal = new Block_Urinal().setCreativeTab(RealLifeMod.RLMFurniture));
	//	blockList.add(chair=new BlockChair());

		blocks3d.add(dartBoard = new Block_Dartboard());
		blocks3d.add(lantern = new Block_Streetlight().setCreativeTab(RealLifeMod.Cars));

	}

	public static void registerModels() {
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
		for (Block b : blockList) {
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(b), 0,
					new ModelResourceLocation(Reference.ID + ":" + b.getUnlocalizedName().substring(5), "inventory"));

		}

		for (Block b : blocks3d) {
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(b), 0,
					new ModelResourceLocation(Reference.ID + ":" + b.getUnlocalizedName().substring(5), "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(b), 0,
					new ModelResourceLocation(Reference.ID + ":" + b.getUnlocalizedName().substring(5), "inventory"));

		}

	}

}
