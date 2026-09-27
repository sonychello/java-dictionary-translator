package org.example;

import java.io.IOException;
import java.nio.file.*;
import java.util.Scanner;
import java.util.regex.Pattern;


public class Main {

    public static String readFileWithCustomException(String path) throws FileReadException {
        try {
            Path filePath = Paths.get(path);
            return Files.readString(filePath);
        } catch (NoSuchFileException e) {
            // Код ошибки 404: файл не найден
            throw new FileReadException(404);
        } catch (AccessDeniedException e) {
            // Код ошибки 403: нет доступа
            throw new FileReadException(403);
        } catch (IOException e) {
            // Код ошибки 500: общая ошибка ввода-вывода
            throw new FileReadException(500);
        }
    }

    public static void main(String[] args) {
        String newWord;
        String newTranslation;
        String path;
        String readDictionary;
        String[][] dictionary;
        Scanner scanner = new Scanner(System.in);

        try {
            readDictionary = readFileWithCustomException("src/test/dictionary");
            String[] lines = readDictionary.split("\\r?\\n");
            dictionary = new String[lines.length][2];
            int i = 0;
            for (String line : lines) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                int index = line.indexOf("|");
                if (index == -1 || index != line.lastIndexOf("|")) {
                    throw new InvalidFileFormatException(1);
                }

                newWord = line.substring(0, index).trim();
                newTranslation = line.substring(index + 1).trim();

                if (newWord.isEmpty() || newTranslation.isEmpty()) {
                    throw new InvalidFileFormatException(2);
                }

                dictionary[i][0] = newWord;
                dictionary[i][1] = newTranslation;
                i++;
            }

        } catch (InvalidFileFormatException e) {
            System.out.println(e.toString());
            return;
        } catch (FileReadException e) {
            System.out.println(e.toString());
            return;
        }

        for (int i = 0; i < dictionary.length; i++) {
            for (int j = 0; j < dictionary.length - i - 1; j++) {
                int len1 = dictionary[j][0].length();
                int len2 = dictionary[j+1][0].length();
                if (len1 < len2) {
                    String[] temp = dictionary[j];
                    dictionary[j] = dictionary[j + 1];
                    dictionary[j + 1] = temp;
                }
            }
        }

        String text;
        try {
            path = scanner.nextLine();
            text = readFileWithCustomException(path);
        } catch (FileReadException e) {
            System.out.println(e.toString());
            return;
        }

        String lowerText = text.toLowerCase();

        for (String[] current : dictionary) {
            String word = current[0].toLowerCase();
            String translation = current[1];

            int index = 0;
            while (index < lowerText.length()) {

                int foundIndex = lowerText.indexOf(word, index);

                if (foundIndex == -1) {
                    break;
                }

                boolean leftBoundary = (foundIndex == 0) ||
                        !Character.isLetter(lowerText.charAt(foundIndex - 1));

                boolean rightBoundary = (foundIndex + word.length() == lowerText.length()) ||
                        !Character.isLetter(lowerText.charAt(foundIndex + word.length()));

                if (leftBoundary && rightBoundary) {

                    text = text.substring(0, foundIndex) +
                            translation +
                            text.substring(foundIndex + word.length());

                    lowerText = lowerText.substring(0, foundIndex) +
                            translation.toLowerCase() +
                            lowerText.substring(foundIndex + word.length());
                }
                index = foundIndex+1;
            }
        }

        System.out.println(text);
    }
}