package itsamysterious.mods.reallifemod.client.forgeobjmodelported.obj;

import itsamysterious.mods.reallifemod.client.forgeobjmodelported.IModelCustom;
import itsamysterious.mods.reallifemod.client.forgeobjmodelported.IModelCustomLoader;
import itsamysterious.mods.reallifemod.client.forgeobjmodelported.ModelFormatException;
import net.minecraft.util.ResourceLocation;

public class ObjModelLoader implements IModelCustomLoader
{

  @Override
  public String getType()
  {
    return "OBJ model";
  }

  private static final String[] types = { "obj" };
  @Override
  public String[] getSuffixes()
  {
    return types;
  }

  @Override
  public IModelCustom loadInstance(ResourceLocation resource) throws ModelFormatException
  {
    return new WavefrontObject(resource);
  }
}