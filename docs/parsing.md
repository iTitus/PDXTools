---
permalink: "/parsing"
layout: default
---

# Parsing

1. Input paths
2. If there is a patch for that path the whole file is read and the patch is applied, else we open a reader.
3. The reader gets converted into an int iterator. Every int is a character.
4. Tokenizer: Returns an iterator of strings - the tokens.
5. Take the token iterator and wrap it in a buffer structure. Right now we have a forward buffer of 2.
6. Parser: take that buffered token stream and parse it. Outputs an IPdxScript object.


## Tokenizer

Let's just not talk about it :)

[Source](https://github.com/iTitus/PDXTools/blob/36893445e4f081b5a817ed6f66d9c6af2846c285/pdx-tools/src/main/java/io/github/ititus/pdx/pdxscript/PdxScriptParser.java#L276)


## Parser

Recursive Descent type parser, but mostly in one method with 2x look-ahead.

[Source](https://github.com/iTitus/PDXTools/blob/36893445e4f081b5a817ed6f66d9c6af2846c285/pdx-tools/src/main/java/io/github/ititus/pdx/pdxscript/PdxScriptParser.java#L30)