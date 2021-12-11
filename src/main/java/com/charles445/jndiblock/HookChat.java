package com.charles445.jndiblock;

import java.util.Locale;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class HookChat
{
	//com/charles445/jndiblock/HookChat
	//cleanChat
	//(Lnet/minecraft/util/text/ITextComponent;)Lnet/minecraft/util/text/ITextComponent;
	public static ITextComponent cleanChat(ITextComponent message)
	{	
		if(checkInvalid(message.getFormattedText()) || checkInvalid(message.getUnformattedComponentText()) || checkInvalid(message.getUnformattedText()))
			return new TextComponentString(" - cough - ");
		return message;
	}
	
	private static boolean checkInvalid(String text)
	{
		return (text.toLowerCase(Locale.ENGLISH).contains("jndi"));
	}
}
