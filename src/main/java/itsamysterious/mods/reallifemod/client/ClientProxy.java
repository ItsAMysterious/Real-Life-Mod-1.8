package itsamysterious.mods.reallifemod.client;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.commons.io.FileUtils;

import itsamysterious.mods.reallifemod.Screenshotspack;
import itsamysterious.mods.reallifemod.common.CommonProxy;
import itsamysterious.mods.reallifemod.core.RealLifeMod_Blocks;
import itsamysterious.mods.reallifemod.core.RealLifeMod_Items;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Computer;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_DartBoard;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_DigitalFrame;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Drawer;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_DrinksFridge;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_IronFence;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Lantern;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Pinwheel;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_PowerLine;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Railing;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Ramp;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Shelf;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Sign;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_TV;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_TVTable;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Table;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Toilet;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Transformer;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Urinal;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_VendingMachine;
import itsamysterious.mods.reallifemod.core.entities.EntityPylon;
import itsamysterious.mods.reallifemod.core.eventhandlers.KeyHandler;
import itsamysterious.mods.reallifemod.core.gui.lifesystem.Overlay.RLMOverlay;
import itsamysterious.mods.reallifemod.core.rendering.Entities.RenderPylon;
import itsamysterious.mods.reallifemod.core.rendering.Entities.RenderVehicle;
import itsamysterious.mods.reallifemod.core.rendering.Entities.RenderWheel;
import itsamysterious.mods.reallifemod.core.rendering.items.GenericBlockItemRenderer;
import itsamysterious.mods.reallifemod.core.rendering.tileEntitys.RenderDartboard;
import itsamysterious.mods.reallifemod.core.rendering.tileEntitys.RenderIronFence;
import itsamysterious.mods.reallifemod.core.rendering.tileEntitys.RenderLantern;
import itsamysterious.mods.reallifemod.core.rendering.tileEntitys.RenderPictureFrame;
import itsamysterious.mods.reallifemod.core.rendering.tileEntitys.RenderPinwheel;
import itsamysterious.mods.reallifemod.core.rendering.tileEntitys.RenderRailing;
import itsamysterious.mods.reallifemod.core.rendering.tileEntitys.RenderRamp;
import itsamysterious.mods.reallifemod.core.rendering.tileEntitys.RenderShelf;
import itsamysterious.mods.reallifemod.core.rendering.tileEntitys.RenderTVTable;
import itsamysterious.mods.reallifemod.core.rendering.tileEntitys.RenderTable;
import itsamysterious.mods.reallifemod.core.rendering.tileEntitys.RenderTransformer;
import itsamysterious.mods.reallifemod.core.rendering.tileEntitys.Render_DrinksFridge;
import itsamysterious.mods.reallifemod.core.rendering.tileEntitys.Render_GasTank;
import itsamysterious.mods.reallifemod.core.rendering.tileEntitys.Render_PowerLine;
import itsamysterious.mods.reallifemod.core.rendering.tileEntitys.Render_VendingMachine;
import itsamysterious.mods.reallifemod.core.rendering.tileEntitys.renderUrinal;
import itsamysterious.mods.reallifemod.core.rendering.tileEntitys.render_Computer;
import itsamysterious.mods.reallifemod.core.rendering.tileEntitys.render_Drawer;
import itsamysterious.mods.reallifemod.core.rendering.tileEntitys.render_GasPump;
import itsamysterious.mods.reallifemod.core.rendering.tileEntitys.render_TV;
import itsamysterious.mods.reallifemod.core.rendering.tileEntitys.render_Toilet;
import itsamysterious.mods.reallifemod.core.roads.signs.RenderSign;
import itsamysterious.mods.reallifemod.core.tiles.TileEntity_GasPump;
import itsamysterious.mods.reallifemod.core.tiles.TileEntity_GasTank;
import itsamysterious.mods.reallifemod.core.vehicles.EntityDriveable;
import itsamysterious.mods.reallifemod.core.vehicles.EntityWheel;
import itsamysterious.mods.reallifemod.init.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.resources.FolderResourcePack;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetworkManager;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.handshake.NetworkDispatcher;
import net.minecraftforge.fml.common.network.internal.NetworkModHolder.NetworkChecker;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ClientProxy extends CommonProxy {

	private Minecraft mc;
	private int ModEntityID;

	public ClientProxy() {
		loadCoreModules();
		registerCraftingRecipes();
	}

	@Override
	public void registerRenderThings() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntity_DigitalFrame.class, new RenderPictureFrame());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntity_TV.class, new render_TV());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntity_Drawer.class, new render_Drawer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntity_Computer.class, new render_Computer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntity_Toilet.class, new render_Toilet());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntity_GasPump.class, new render_GasPump());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntity_GasTank.class, new Render_GasTank());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntity_Sign.class, new RenderSign());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntity_Lantern.class, new RenderLantern());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntity_VendingMachine.class, new Render_VendingMachine());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntity_DrinksFridge.class, new Render_DrinksFridge());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntity_PowerLine.class, new Render_PowerLine());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntity_Transformer.class, new RenderTransformer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntity_Pinwheel.class, new RenderPinwheel());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntity_Ramp.class, new RenderRamp());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntity_TVTable.class, new RenderTVTable());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntity_Shelf.class, new RenderShelf());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntity_IronFence.class, new RenderIronFence());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntity_Railing.class, new RenderRailing());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntity_DartBoard.class, new RenderDartboard());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntity_Table.class, new RenderTable());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntity_Toilet.class, new render_Toilet());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntity_Urinal.class, new renderUrinal());

		RenderingRegistry.registerEntityRenderingHandler(EntityDriveable.class, new RenderVehicle());
		RenderingRegistry.registerEntityRenderingHandler(EntityPylon.class, new RenderPylon());
		RenderingRegistry.registerEntityRenderingHandler(EntityWheel.class, new RenderWheel());

		MinecraftForge.EVENT_BUS.register(new RLMOverlay(Minecraft.getMinecraft()));
		MinecraftForge.EVENT_BUS.register(new KeyHandler());

	}

	public void registerTiles() {

	}

	@Override
	public void loadCoreModules() {

		List<IResourcePack> defaultResourcePacks = ObfuscationReflectionHelper.getPrivateValue(Minecraft.class,
				Minecraft.getMinecraft(), "defaultResourcePacks", "field_110449_ao");
		defaultResourcePacks.add(new FolderResourcePack(new File(Minecraft.getMinecraft().mcDataDir, "/RLM")));
		File screenshots = new File(Minecraft.getMinecraft().mcDataDir, "screenshots");
		if (!screenshots.exists()) {
			screenshots.mkdirs();
		}
		for (File f : screenshots.listFiles()) {
			Screenshotspack.filenames.add(f.getName());
		}
		defaultResourcePacks.add(new Screenshotspack());
		Minecraft.getMinecraft().refreshResources();

		File RLMDirectory = new File(Minecraft.getMinecraft().mcDataDir, "RLM/texts");
		if (RLMDirectory.exists()) {
			File jobfile = new File(RLMDirectory, "Jobs.txt");
			File newjobfile = null;
			try {
				FileUtils.copyURLToFile(new URL("http://themoddingparadise.de/RealLifeMod/Jobs.txt"), jobfile);
				jobfile.setLastModified(System.currentTimeMillis());
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (jobfile.exists()) {
				System.out.println("Succesfully downloaded Resources");
			}
		} else

		{
			RLMDirectory.mkdirs();
		}

	}

	/**
	 * -------------------------------------------------------------------------
	 */
	@Override
	public void registerItemModels() {
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();

		for (Item item : RealLifeMod_Items.itemList) {
			renderItem.getItemModelMesher().register(item, 0, new ModelResourceLocation(
					Reference.ID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
		}

		for (Block b : RealLifeMod_Blocks.blockList) {
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(b), 0,
					new ModelResourceLocation(Reference.ID + ":" + b.getUnlocalizedName().substring(5), "inventory"));
		}

		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(RealLifeMod_Blocks.transformer), 0,
				new ModelResourceLocation(Reference.ID + ":blockTransformer", "inventory"));
	}

	public void register3DItem(BlockContainer theBlock, TileEntitySpecialRenderer renderer) {
		try {
			MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(theBlock),
					new GenericBlockItemRenderer(renderer, theBlock.createNewTileEntity(null, 0)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadSongs() {

	}

	@SubscribeEvent
	public void blockHighlightEvent(DrawBlockHighlightEvent e) {
		if (e.target.entityHit instanceof EntityPlayer) {
		}
	}

	@Override
	public boolean isThePlayer(EntityPlayer player) {
		return player == FMLClientHandler.instance().getClient().thePlayer;
	}

	public void registerCraftingRecipes() {
		GameRegistry.addShapedRecipe(new ItemStack(RealLifeMod_Items.bottle, 16), "#W#", "G#G", "GGG", 'G',
				Blocks.glass_pane, 'W', Blocks.wooden_button);
		GameRegistry.addShapedRecipe(new ItemStack(RealLifeMod_Items.bottle, 16), "#W#", "G#G", "GGG", 'G',
				Blocks.glass_pane, 'W', Blocks.stone_button);

	}

}