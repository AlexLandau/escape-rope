# escape-rope

Still figuring out exactly what the terminology and semantics here should be, but I'm looking at the intersection of escaped strings and delimited lists.

Basically, I'm looking to make it easy and reliable to go back and forth between structured state and a single string representing that state. I expect this to be more useful for hacking stuff together than for polished code, so brevity will be important. (Other options can give better performance, but sometimes this is a convenient way to store information, or offer information to clients when they need to persist it themselves. This is especially the case when we can reformat the strings they see to remove gremlins like null characters and newlines.)

Escaping characters is often used for a couple of reasons: either there are control characters that need to be treated differently, or we're trying to express a wider variety of characters in our semantic content than the characters we're allowed to use (as with % codes in URLs). Delimited lists are a special case of the control characters, in that we can essentially ignore those characters and just return a list of the strings with content.

One possibility for returning control characters mixed with non-control characters is to return a list of these things, like a low-budget lexer.

One key idea here is that the Delimiter that joins strings together should be doing the escaping and unescaping, instead of relying on some other part of the program to handle that escaping correctly.

The current philosophy is that minimizing the number of bugs that are in the code (or arise in client applications from using the code, even in unexpected ways) is more important than performance. Use cases that require high performance should be using something else anyway.

TODO: More options around null string handling.

TODO: Signing strings.

TODO: Limited output character sets.

TODO: Consider arbitrary byte array conversion in addition to String conversion? Is the use of code points actually harmful because it prevents invalid strings from being persisted?

TODO: Map<String, String> conversion in addition to List<String>?

TODO: Document recently added code

TODO: Make it possible to append Rope entries to a file one at a time, and then be able to read the entire file back as a ListRope consisting of those entries, including "read one line at a time" instead of processing all the way through.

TODO: Implementation(s) of Restricter

TODO: Delimiter and RopeDelimiter implementations that take advantage of Restricter to replace the delimiter character

TODO: More stream-friendliness

The goal, currently, is an easy-to-use, reliable way to map from List<String> to String and back again, and additional tools building on that to make this a relatively powerful tool compared to the effort to get it working.

Rope: A Rope is either a String or a list of Ropes. The idea is that it's fairly easy to come up with a set of converters ("weavers") between object types and Ropes, and this library can provide robust conversion between Ropes and Strings. This provides quick-and-dirty serialization that can be adapted for the various places strings can go (e.g. removing special characters) and is easy to look through. The downside is that there is no structured support for backwards-compatibility as in formats like protocol buffers, and it may also be verbose compared with such formats. It's also expected to be much slower at scale.