# Parser
Provides the tools and framework to create any parser.
## Framework
The framework consists of the Parser, ParseRes, Binder, BParser, and StrBParser classes and interfaces.
The Parser class defines a Parser object, that has a ```parse``` function which takes a string to parse. ```parse``` returns a ParseRes object, which contains the results of the parse. These results consist of a string (```strRem```) and a value (```parseVal```), the string being the remains of the string being parsed, and the value being the parsed value.
ParseRes has a ```failed``` function that informs whether the parse failed. If it did fail, ```strRem``` and ```parseVal``` are both ```null```.

BParser, short for Bind Parser, extends the Parser class and gives the added functionality of being able to take a ParseRes object, parsing ```strRem``` from ParseRes, and combining (or binding) the value obtained from the parse to the parse value in the ParseRes given.
BParser requires an object implementing the Binder interface, which performs the binding of the parse value.

StrBParser is the version of BParser where the parsed values are strings. There is no need to give StrBParser a Binder, since StrBParser has one precoded.

## Tools
To make parsing easier, this library contains a util package, containing pre-defined Parsers as well as a PUtil (Parser Util) class containing useful utility functions.
A quick list of the pre-defined Parsers and their functionality:
* ONE - consumes the first character in the string given
* SAT - consumes the first character in the string given if the string fulfills a predicate (a Pred object)
* CHR - consumes the first character in the string given if it is a certain character (which is given to CHR's constructor)
* STR - same as CHR, but consumes and checks for a String instead of a char
* SPACES - consumes all the spaces at the beginning of the string
* TOKEN - takes a Parser. It runs SPACES before and after the Parser given is used
* SYMBOL - same as TOKEN, but takes a string to parse isntead of a Parser
* SURROUND - looks for and parses a string within two characters (given to SURROUND). For ex, a new SURROUND('@') object would consume any string that starts with something like @Hi@ or @Hello There World!@
* ONE_OF - takes a list of Parsers and runs each Parser against a string until it finds a Parser that successfully parses
* ZERO_OR_MORE - takes a Parser and uses it to parse a string _zero or more times_ until the Parser returns a failed ParseRes
* ONE_OR_MORE - same as ZERO_OR_MORE, but is only successful if the Parser parses a string successfully at least once (hence, _one or more_)
