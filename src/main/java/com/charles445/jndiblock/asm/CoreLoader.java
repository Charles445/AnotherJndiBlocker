package com.charles445.jndiblock.asm;

import java.util.Map;

import com.charles445.jndiblock.asm.helper.ObfHelper;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.Name("AnotherJndiBlocker ASM")
@IFMLLoadingPlugin.SortingIndex(1002)
@IFMLLoadingPlugin.TransformerExclusions({ "com.charles445.jndiblock.asm", "com.charles445.jndiblock.asm." })

public class CoreLoader implements IFMLLoadingPlugin
{
	//
	// IFMLLoadingPlugin
	// 

	@Override
	public String[] getASMTransformerClass()
	{
		return new String[] { "com.charles445.jndiblock.asm.AnotherJndiBlockerASM" };
	}

	@Override
	public void injectData(Map<String, Object> data)
	{
		ObfHelper.setObfuscated((Boolean) data.get("runtimeDeobfuscationEnabled"));
		ObfHelper.setRunsAfterDeobfRemapper(true);
	}

	@Override
	public String getModContainerClass()
	{
		return null;
	}

	@Override
	public String getSetupClass()
	{
		return null;
	}

	@Override
	public String getAccessTransformerClass()
	{
		return null;
	}
}
