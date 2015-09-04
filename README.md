# escape-rope

Still figuring out exactly what the terminology and semantics here should be, but I'm looking at the intersection of escaped strings and delimited lists.

Basically, I'm looking to make it easy and reliable to go back and forth between structured state and a single string representing that state. I expect this to be more useful for hacking stuff together than for polished code, so brevity will be important. (Other options can give better performance, but sometimes this is a convenient way to store information, or offer information to clients when they need to persist it themselves. This is especially the case when we can reformat the strings they see to remove gremlins like null characters and newlines.)

Escaping characters is often used for a couple of reasons: either there are control characters that need to be treated differently, or we're trying to express a wider variety of characters in our semantic content than the characters we're allowed to use (as with % codes in URLs). Delimited lists are a special case of the control characters, in that we can essentially ignore those characters and just return a list of the strings with content.

One possibility for returning control characters mixed with non-control characters is to return a list of these things, like a low-budget lexer.

One key idea here is that the Delimiter that joins strings together should be doing the escaping and unescaping, instead of relying on some other part of the program to handle that escaping correctly.

The current philosophy is that minimizing the number of bugs that are in the code (or arise in client applications from using the code, even in unexpected ways) is more important than performance. Use cases that require high performance should be using something else anyway.

TODO: NULL STRINGS. I'm guessing we'll sometimes want to force these into being empty strings and other times maintain their null-ness.

TODO: Delimiters as a class for obtaining Delimiter instances.

TODO: Signing strings.

TODO: Limited output character sets.

TODO: Consider arbitrary byte array conversion in addition to String conversion? Is the use of code points actually harmful because it prevents invalid strings from being persisted?

TODO: Map<String, String> conversion in addition to List<String>?