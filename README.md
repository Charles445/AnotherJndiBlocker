# AnotherJndiBlocker
 Blocks Jndi from running by causing Jndi lookups to silently error instead of run properly.

 This is mostly for fun because this fix is really silly
 
 JmsAppender makes up its own name for the JndiManager, so if anything uses a Jms receiver it'll still be vulnerable, I think.
 (Nothing uses that, right?)
 
 Hopefully a proper fix gets made and rolled out by someone, somehow.
 
 This is for 1.12.2 but this concept probably works for other versions too.
