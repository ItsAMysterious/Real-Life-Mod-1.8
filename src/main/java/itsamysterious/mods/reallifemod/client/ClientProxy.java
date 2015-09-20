package itsamysterious.mods.reallifemod.client;

import java.io.File;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import itsamysterious.mods.reallifemod.RLMPack;
import itsamysterious.mods.reallifemod.RealLifeMod;
import itsamysterious.mods.reallifemod.Screenshotspack;
import itsamysterious.mods.reallifemod.common.CommonProxy;
import itsamysterious.mods.reallifemod.core.RealLifeMod_Blocks;
import itsamysterious.mods.reallifemod.core.RealLifeMod_Items;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntityDigitalFrame;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntityRamp;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Computer;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Drawer;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_DrinksFridge;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Electric;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Lantern;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Pinwheel;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_PowerLine;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Sign;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_TV;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Toilet;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Transformer;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_VendingMachine;
import itsamysterious.mods.reallifemod.core.entities.EntityPylon;
import itsamysterious.mods.reallifemod.core.gui.lifesystem.RLMOverlay;
import itsamysterious.mods.reallifemod.core.handlers.Keybindings;
import itsamysterious.mods.reallifemod.core.packets.MountVehicleMessage;
import itsamysterious.mods.reallifemod.core.rendering.Entities.RenderPylon;
import itsamysterious.mods.reallifemod.core.rendering.Entities.RenderVehicle;
import itsamysterious.mods.reallifemod.core.rendering.items.GenericBlockItemRenderer;
import itsamysterious.mods.reallifemod.core.rendering.tileEntitys.RenderLantern;
import itsamysterious.mods.reallifemod.core.rendering.tileEntitys.RenderPictureFrame;
import itsamysterious.mods.reallifemod.core.rendering.tileEntitys.RenderPinwheel;
import itsamysterious.mods.reallifemod.core.rendering.tileEntitys.RenderRamp;
import itsamysterious.mods.reallifemod.core.rendering.tileEntitys.RenderTransformer;
import itsamysterious.mods.reallifemod.core.rendering.tileEntitys.Render_DrinksFridge;
import itsamysterious.mods.reallifemod.core.rendering.tileEntitys.Render_GasTank;
import itsamysterious.mods.reallifemod.core.rendering.tileEntitys.Render_PowerLine;
import itsamysterious.mods.reallifemod.core.rendering.tileEntitys.Render_VendingMachine;
import itsamysterious.mods.reallifemod.core.rendering.tileEntitys.render_Computer;
import itsamysterious.mods.reallifemod.core.rendering.tileEntitys.render_Drawer;
import itsamysterious.mods.reallifemod.core.rendering.tileEntitys.render_GasPump;
import itsamysterious.mods.reallifemod.core.rendering.tileEntitys.render_TV;
import itsamysterious.mods.reallifemod.core.rendering.tileEntitys.render_Toilet;
import itsamysterious.mods.reallifemod.core.roads.signs.RenderSign;
import itsamysterious.mods.reallifemod.core.roads.signs.Signfile;
import itsamysterious.mods.reallifemod.core.tiles.RLMTileEntity;
import itsamysterious.mods.reallifemod.core.tiles.TileEntity_GasPump;
import itsamysterious.mods.reallifemod.core.tiles.TileEntity_GasTank;
import itsamysterious.mods.reallifemod.core.vehicles.EntityVehicle;
import itsamysterious.mods.reallifemod.core.vehicles.VehicleFile;
import itsamysterious.mods.reallifemod.init.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.resources.FolderResourcePack;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClientProxy extends CommonProxy {

	private Minecraft mc;
	private int ModEntityID;
	
	public ClientProxy() {
		loadCoreModules();
		Keybindings.init();
	}

	@Override
	public void registerRenderThings() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDigitalFrame.class, new RenderPictureFrame());
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
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRamp.class, new RenderRamp());

		RenderingRegistry.registerEntityRenderingHandler(EntityVehicle.class, new RenderVehicle());
		RenderingRegistry.registerEntityRenderingHandler(EntityPylon.class, new RenderPylon());
		MinecraftForge.EVENT_BUS.register(new RLMOverlay(Minecraft.getMinecraft()));
	}

	public void registerTiles() {

	}
	
	@Override
	public void loadCoreModules() {

		List<IResourcePack> defaultResourcePacks = ObfuscationReflectionHelper.getPrivateValue(Minecraft.class,
				Minecraft.getMinecraft(), "defaultResourcePacks", "field_110449_ao");
		defaultResourcePacks.add(new FolderResourcePack(new File(Minecraft.getMinecraft().mcDataDir,"/RLM")));
		File screenshots = new File(Minecraft.getMinecraft().mcDataDir, "screenshots");
		if(!screenshots.exists()){
			screenshots.mkdirs();
		}
		for (File f : screenshots.listFiles()) {
			Screenshotspack.filenames.add(f.getName());
		}
		defaultResourcePacks.add(new Screenshotspack());
		Minecraft.getMinecraft().refreshResources();
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

	@SubscribeEvent
	public void onKeyPressed(ClientTickEvent e) {
		if (e.side == Side.CLIENT&&e.phase==e.phase.END) {
			if (Keybindings.EnterVehicleKey.isKeyDown()) {
				if (getClosestEntity() != null) {
					EntityVehicle v = getClosestEntity();
					RealLifeMod.network.sendToServer(new MountVehicleMessage(v.getEntityId()));
				}
			}
			if (Keybindings.CharacterKey.isKeyDown()) {
				Minecraft.getMinecraft().thePlayer.onKillCommand();
			}
		}

	}
	

	@SideOnly(Side.CLIENT)
	private final EntityVehicle getClosestEntity() {
		World world = FMLClientHandler.instance().getServer().getEntityWorld();
		List<Entity> entities = world.getLoadedEntityList();
		EntityVehicle i = null;
		EntityPlayer p = FMLClientHandler.instance().getClientPlayerEntity();
		for (Entity e : entities) {
			if (e instanceof EntityVehicle) {
				if (i == null) {
					i = (EntityVehicle) e;
				} else if (p.getDistanceSqToEntity(e) < p.getDistanceSqToEntity(i)) {
					i = (EntityVehicle) e;
				}
			}
		}
		return i;
	}

	
	public void loadSongs() {

	}

	@SubscribeEvent
	public void blockHighlightEvent(DrawBlockHighlightEvent e) {
		if (e.target.entityHit instanceof EntityPlayer) {
		}
	}

}