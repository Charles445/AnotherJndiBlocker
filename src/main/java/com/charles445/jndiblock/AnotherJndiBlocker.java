package com.charles445.jndiblock;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.appender.AbstractManager;
import org.apache.logging.log4j.core.net.JndiManager;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(
		modid = AnotherJndiBlocker.MODID, 
		name = AnotherJndiBlocker.NAME, 
		version = AnotherJndiBlocker.VERSION,
		acceptableRemoteVersions = "*"
)
public class AnotherJndiBlocker
{
	public static final String MODID = "jndiblock";
	public static final String NAME = "Another Jndi Blocker";
	public static final String VERSION = "0.1.2";
	
	public static final Logger logger = LogManager.getLogger("AnotherJndiBlocker");
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		//Written by Charles445
		try
		{
			//Let's begin
			
			//Load the Jndi default instance a couple of times so the manager's count increments a bunch
			//Safety measure to avoid the map getting cleaned up while we work
			for(int i=0;i<8;i++)
				JndiManager.getDefaultManager();
			
			//Now it's in the MAP for good
			//Let's replace it
			
			//Grab the MAP
			Field f_AbstractManager_MAP = AbstractManager.class.getDeclaredField("MAP");
			f_AbstractManager_MAP.setAccessible(true);
			Map<String, AbstractManager> managerMap = (Map<String, AbstractManager>) f_AbstractManager_MAP.get(null);
			
			List<String> managersToReplace = new ArrayList<>();
			
			//Gather matches in the map
			for(Map.Entry<String, AbstractManager> entry : managerMap.entrySet())
			{
				if(entry.getValue() instanceof JndiManager)
				{
					managersToReplace.add(entry.getKey());
				}
			}
			
			logger.info("Replacing "+managersToReplace.size()+" JndiManager instances");
			
			for(String replacementKey : managersToReplace)
			{
				managerMap.put(replacementKey, new FakeAbstractManager(null, "FakeJndiManager"));
			}
		}
		catch(Exception e)
		{
			throw new RuntimeException("Failed to run AnotherJndiBlocker!", e);
		}
	}
}
