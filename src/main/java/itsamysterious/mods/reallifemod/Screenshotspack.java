package itsamysterious.mods.reallifemod;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.DefaultResourcePack;
import net.minecraft.client.resources.FolderResourcePack;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;

public class Screenshotspack extends FolderResourcePack implements IResourcePack,IResourceManagerReloadListener{
    public static final Set defaultResourceDomains = ImmutableSet.of("screenshots");
    public static List<String> filenames=new ArrayList<String>();
    
	public Screenshotspack() {
		super(new File(Minecraft.getMinecraft().mcDataDir+"/screenshots"));
	}

	@Override
	public InputStream getInputStream(ResourceLocation p_110590_1_) throws IOException {
		return getResourceStream(p_110590_1_);
	}

	@Override
	public boolean resourceExists(ResourceLocation p_110589_1_) {
		return getResourceStream(p_110589_1_) != null;
	}
	
    private InputStream getResourceStream(ResourceLocation p_110605_1_)
    {
    	try {
			return new FileInputStream(Minecraft.getMinecraft().mcDataDir+"/screenshots/"+p_110605_1_.getResourcePath());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
    
    }

	@Override
	public Set getResourceDomains() {
		return defaultResourceDomains;
	}

	@Override
	public IMetadataSection getPackMetadata(IMetadataSerializer p_135058_1_, String p_135058_2_) throws IOException {
		return null;
	}

	@Override
	public BufferedImage getPackImage() throws IOException {
		return null;
	}

	@Override
	public String getPackName() {
		return "Screenshots";
	}

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {
	}
}
