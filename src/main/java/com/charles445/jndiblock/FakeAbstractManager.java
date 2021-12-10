package com.charles445.jndiblock;

import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.AbstractManager;

public class FakeAbstractManager extends AbstractManager
{
	public FakeAbstractManager(LoggerContext loggerContext, String name)
	{
		super(loggerContext, name);
		this.count = 2000;
	}
	
	@Override
	public void updateData(Object data)
	{
		this.count = 2000;
	}
}
