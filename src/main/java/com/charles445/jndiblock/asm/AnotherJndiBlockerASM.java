package com.charles445.jndiblock.asm;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import com.charles445.jndiblock.asm.helper.ASMHelper;

import net.minecraft.launchwrapper.IClassTransformer;

public class AnotherJndiBlockerASM implements IClassTransformer
{
	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass)
	{
		if(transformedName.equals("net.minecraft.network.play.server.SPacketChat"))
		{
			return transformSPacketChat(basicClass);
		}
		return basicClass;
	}

	private byte[] transformSPacketChat(byte[] basicClass)
	{
		ClassNode clazzNode = ASMHelper.readClassFromBytes(basicClass);
		
		MethodNode m_init = null;
		
		for(MethodNode m : clazzNode.methods)
		{
			if(m.name.equals("<init>") && m.desc.equals("(Lnet/minecraft/util/text/ITextComponent;Lnet/minecraft/util/text/ChatType;)V"))
			{
				m_init = m;
				break;
			}
		}
		
		if(m_init == null)
		{
			System.out.println("AnotherJndiBlocker FAILED to patch SPacketChat - Couldn't find init");
			return basicClass;
		}
		
		AbstractInsnNode anchor = m_init.instructions.getFirst();
		
		boolean succeeded = false;
		
		while(anchor != null)
		{
			anchor = ASMHelper.findNextInstructionWithOpcode(anchor, Opcodes.ALOAD);
			if(anchor != null && ((VarInsnNode)anchor).var == 1)
			{
				//Intercept
				m_init.instructions.insert(anchor, new MethodInsnNode(Opcodes.INVOKESTATIC,
						"com/charles445/jndiblock/HookChat",
						"cleanChat",
						"(Lnet/minecraft/util/text/ITextComponent;)Lnet/minecraft/util/text/ITextComponent;",
						false
						));
				succeeded = true;
				break;
			}
		}
		
		if(!succeeded)
		{
			System.out.println("AnotherJndiBlocker FAILED to patch SPacketChat - Couldn't find ALOAD 1");
			return basicClass;
		}
		
		System.out.println("AnotherJndiBlocker is patching SPacketChat! Client side protection will run.");
		return ASMHelper.writeClassToBytes(clazzNode, ClassWriter.COMPUTE_MAXS);
	}
}
