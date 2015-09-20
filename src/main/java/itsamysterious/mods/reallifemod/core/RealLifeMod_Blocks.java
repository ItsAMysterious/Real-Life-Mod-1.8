package itsamysterious.mods.reallifemod.core;

import java.util.ArrayList;
import java.util.List;

import itsamysterious.mods.reallifemod.RealLifeMod;
import itsamysterious.mods.reallifemod.core.blocks.BlockComputer;
import itsamysterious.mods.reallifemod.core.blocks.BlockDrawer;
import itsamysterious.mods.reallifemod.core.blocks.BlockGasTank;
import itsamysterious.mods.reallifemod.core.blocks.BlockParquet;
import itsamysterious.mods.reallifemod.core.blocks.BlockPetrolPump;
import itsamysterious.mods.reallifemod.core.blocks.BlockRamp;
import itsamysterious.mods.reallifemod.core.blocks.BlockTarmac;
import itsamysterious.mods.reallifemod.core.blocks.BlockTelevision;
import itsamysterious.mods.reallifemod.core.blocks.BlockVendingMachine;
import itsamysterious.mods.reallifemod.core.blocks.Block_DrinksFridge;
import itsamysterious.mods.reallifemod.core.blocks.CopperOre;
import itsamysterious.mods.reallifemod.core.blocks.electrisity.BlockDigitalFrame;
import itsamysterious.mods.reallifemod.core.blocks.electrisity.BlockHiddenCable;
import itsamysterious.mods.reallifemod.core.blocks.electrisity.BlockLantern;
import itsamysterious.mods.reallifemod.core.blocks.electrisity.BlockPinwheel;
import itsamysterious.mods.reallifemod.core.blocks.electrisity.BlockPowerLine;
import itsamysterious.mods.reallifemod.core.blocks.electrisity.BlockTransformer;
import itsamysterious.mods.reallifemod.core.blocks.sanitary.BlockToilet;
import itsamysterious.mods.reallifemod.core.blocks.sanitary.BlockWashingbasin;
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
	public static Block block_TV, block_Drawer, block_Computer, parquet, toilet, washbasin, petrolPump, tarmac, gastank, lantern,
	vendingMachine, drinksFridge, powerLine, transformer, cable, pinwheel, copperore, pictureframe, ramp;

	public static void defineBlocks() {
		blockList.add(block_TV = new BlockTelevision());
		blockList.add(block_Drawer = new BlockDrawer());
		blockList.add(block_Computer = new BlockComputer());
		blockList.add(toilet=new BlockToilet().setCreativeTab(RealLifeMod.RLMFurniture));
		blockList.add(washbasin = new BlockWashingbasin().setCreativeTab(RealLifeMod.RLMFurniture));
		blockList.add(petrolPump = new BlockPetrolPump());
		blockList.add(parquet = new BlockParquet().setCreativeTab(RealLifeMod.RLMDeco));
		blockList.add(tarmac  = new BlockTarmac(Material.rock).setUnlocalizedName("blocktarmac").setCreativeTab(RealLifeMod.Cars));
		blockList.add(gastank  = new BlockGasTank().setUnlocalizedName("blockgastank").setCreativeTab(RealLifeMod.Cars));
		blockList.add(vendingMachine = new BlockVendingMachine().setCreativeTab(RealLifeMod.RLMDeco));
		blockList.add(drinksFridge = new Block_DrinksFridge().setCreativeTab(RealLifeMod.RLMDeco));
		blockList.add(powerLine = new BlockPowerLine());
		blockList.add(transformer = new BlockTransformer());
		blockList.add(cable = new BlockHiddenCable().setCreativeTab(RealLifeMod.Technologie));
		blockList.add(pinwheel = new BlockPinwheel());
		blockList.add(copperore=new CopperOre().setCreativeTab(RealLifeMod.Technologie));
		blockList.add(pictureframe=new BlockDigitalFrame(Material.circuits));
		blockList.add(ramp=new BlockRamp());

		blocks3d.add(lantern  = new BlockLantern().setCreativeTab(RealLifeMod.Cars));
		
	}
	
	public static void registerModels(){
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
		for(Block b:blockList){
				renderItem.getItemModelMesher().register(Item.getItemFromBlock(b), 0,
						new ModelResourceLocation(Reference.ID + ":" + b.getUnlocalizedName().substring(5), "inventory"));
			
		}
		
		for(Block b:blocks3d){
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(b), 0,
					new ModelResourceLocation(Reference.ID + ":" + b.getUnlocalizedName().substring(5), "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(b), 0, new ModelResourceLocation(Reference.ID + ":" +b.getUnlocalizedName().substring(5), "inventory"));
		
	}
		
	}

}
