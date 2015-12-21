package itsamysterious.mods.reallifemod.api.forgeobjmodelported.techne;

import itsamysterious.mods.reallifemod.api.forgeobjmodelported.IModelCustom;
import itsamysterious.mods.reallifemod.api.forgeobjmodelported.IModelCustomLoader;
import itsamysterious.mods.reallifemod.api.forgeobjmodelported.ModelFormatException;
import net.minecraft.util.ResourceLocation;

public class TechneModelLoader implements IModelCustomLoader {

  @Override
  public String getType()
  {
    return "Techne model";
  }

  private static final String[] types = { "tcn" };
  @Override
  public String[] getSuffixes()
  {
    return types;
  }

  @Override
  public IModelCustom loadInstance(ResourceLocation resource) throws ModelFormatException
  {
    return new TechneModel(resource);
  }

}