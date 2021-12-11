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
	public static final String VERSION = "0.1.3";
	
	public static final Logger logger = LogManager.getLogger("AnotherJndiBlocker");
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		try
		{
			Class.forName("org.apache.logging.log4j.core.net.JndiManager");
			Class.forName("org.apache.logging.log4j.core.lookup.JndiLookup");
		}
		catch (ClassNotFoundException e)
		{
			logger.warn("AnotherJndiBlocker cannot find JndiManager or JndiLookup on the server. The mod will assume the classes have been manually deleted, and so server protection will not run.");
			recommendForge();
			return;
		}
		
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

			if(managersToReplace.size() > 1)
			{
				logger.error("AnotherJndiBlocker server protection is running, but the replaced manager size was unexpectedly large. The server protection may be unreliable.");
				finalError();
			}
			else if(managersToReplace.size() > 0)
			{
				logger.info("AnotherJndiBlocker server protection is running!");
				logger.info("This protection is rudimentary, updating forge is your best option.");
				recommendForge();
			}
			else
			{
				logger.error("AnotherJndiBlocker could not find the JndiManager instance being used. Server protection will not work.");
				finalError();
			}
		}
		catch(Exception e)
		{
			logger.error("Failed to run AnotherJndiBlocker on the server, but JndiLookup still exists. Server protection will not work.", e);
			finalError();
		}
	}
	
	private void finalError()
	{
		logger.error("If you are running a forge version earlier than 1.12.2 - 14.23.5.2858, update forge right now! Do not keep the server running!");
	}
	
	private void recommendForge()
	{
		logger.info("If you are running a forge version earlier than 1.12.2 - 14.23.5.2858, update forge as soon as you can.");
	}
}
