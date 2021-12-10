# AnotherJndiBlocker
 Blocks Jndi from running in Minecraft 1.12.2 by causing Jndi lookups to silently error instead of run properly.
 
 Don't download this, go get https://github.com/Glease/Healer instead

 This is mostly for fun because this fix is really silly
 
 JmsAppender makes up its own name for the JndiManager, so if anything uses a Jms receiver it'll still be vulnerable, I think.
 (Nothing uses that, right?)
