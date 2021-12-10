package com.charles445.jndiblock;

import java.util.concurrent.TimeUnit;

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
	
	//To be EXTRA sure that this manager does not get cleaned up, replace the stop function entirely
	@Override
	public boolean stop(final long timeout, final TimeUnit timeUnit)
	{
		return true;
	}
}
