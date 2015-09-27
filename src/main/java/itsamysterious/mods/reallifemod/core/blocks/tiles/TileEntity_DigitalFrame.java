package itsamysterious.mods.reallifemod.core.blocks.tiles;

import itsamysterious.mods.reallifemod.Screenshotspack;
import itsamysterious.mods.reallifemod.core.tiles.RLMTileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.gui.IUpdatePlayerListBox;

public class TileEntity_DigitalFrame extends RLMTileEntity implements IUpdatePlayerListBox {
	private ItemStack item;
	public int image;
	private int age;
	private boolean isVideomode;

	public TileEntity_DigitalFrame() {
		this.image = 0;
	}

	@Override
	public void update() {
		age++;

		int numImages = Screenshotspack.filenames.size() - 1;
		if (numImages > 0) {
			if (Screenshotspack.filenames.get(image).contains("VID_")) {
				image++;
			} else if (image < numImages && age % 160 == 0) {
				image++;
			}
			if (image >= numImages) {
				image = 0;
			}
		}
		// }else
		// {
		// int numImages=Screenshotspack.videos.get(currentVideo).size()-1;
		/// }
	}

	/*
	 * @Override public int getSizeInventory() { return 1; }
	 * 
	 * @Override public ItemStack getStackInSlot(int index) { return item; }
	 * 
	 * @Override public ItemStack decrStackSize(int index, int count) { return
	 * item; }
	 * 
	 * @Override public ItemStack getStackInSlotOnClosing(int index) { return
	 * item; }
	 * 
	 * @Override public void setInventorySlotContents(int index, ItemStack
	 * stack) { this.item=stack; }
	 * 
	 * @Override public int getInventoryStackLimit() { return 1; }
	 * 
	 * @Override public boolean isUseableByPlayer(EntityPlayer player) { return
	 * true; }
	 * 
	 * @Override public void openInventory(EntityPlayer player) {}
	 * 
	 * @Override public void closeInventory(EntityPlayer player) {}
	 * 
	 * @Override public boolean isItemValidForSlot(int index, ItemStack stack) {
	 * return stack.getItem() instanceof ItemBattery; }
	 * 
	 * @Override public int getField(int id) { return 0; }
	 * 
	 * @Override public void setField(int id, int value) {}
	 * 
	 * @Override public int getFieldCount() { return 1; }
	 * 
	 * @Override public void clear() {}
	 * 
	 * @Override public String getName() { return "Digital Frame"; }
	 * 
	 * @Override public boolean hasCustomName() { return true; }
	 * 
	 * @Override public IChatComponent getDisplayName() { return new
	 * ChatComponentText("Digital Frame"); }
	 * 
	 * @Override public int[] getSlotsForFace(EnumFacing side) { return new
	 * int[]{0}; }
	 * 
	 * @Override public boolean canInsertItem(int index, ItemStack itemStackIn,
	 * EnumFacing direction) { return itemStackIn.getItem() instanceof
	 * ItemBattery; }
	 * 
	 * @Override public boolean canExtractItem(int index, ItemStack stack,
	 * EnumFacing direction) { return false; }
	 */

	public String getNameOfFile() {
		return Screenshotspack.filenames.get(image);
	}

}
