# Java Dictionary Translator

A Java console application implementing a simple dictionary-based translator.

The program reads a dictionary from a file, reads an input file, translates its contents according to the dictionary, and outputs the result to the console.

## Dictionary Format

Each dictionary entry has the following format:

```text
word or expression | translation
```

## Translation Rules

The translator:

* ignores letter case;
* leaves words unchanged if they are not found in the dictionary;
* selects the longest matching dictionary expression when several variants are possible.

For example, if the dictionary contains:

```text
look | смотреть
look forward to | ждать с нетерпением
```

then `look forward to` is translated using the longer matching expression.

## Exceptions

The project defines and uses custom exceptions:

* `InvalidFileFormatException` — thrown when the dictionary format is invalid;
* `FileReadException` — thrown when a file cannot be read, does not exist, or cannot be accessed.

## Program Workflow

1. Read the dictionary file.
2. Validate its format.
3. Read the input file.
4. Translate the text according to the dictionary.
5. Output the translated text to the console.

## Technologies

* Java
* File I/O
* Collections
* Custom exceptions
* String processing

## Demonstration

The program demonstrates dictionary loading, file reading, translation, longest-match selection, and handling of invalid files.
