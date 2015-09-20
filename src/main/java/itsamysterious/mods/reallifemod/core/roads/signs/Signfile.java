package itsamysterious.mods.reallifemod.core.roads.signs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import itsamysterious.mods.reallifemod.init.Reference;
import net.minecraft.util.ResourceLocation;

public class Signfile {

	public String name;
	public SignTypes type;
	public ResourceLocation texture;
	
	
	public Signfile(File f) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String line;
			while ((line = reader.readLine()) != null) {
				if(line.startsWith("name:")){
					this.name=line.split(":")[1].trim();
				}
				
				if(line.startsWith("type:")){
					this.type=SignTypes.valueOf(line.split(":")[1].trim());
				}
				
				if(line.startsWith("texture:")){
					this.texture=new ResourceLocation(Reference.ID+":textures/signs/"+line.split(":")[1].trim());
				}
			}
			reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
				
	}

}
