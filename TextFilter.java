package com.company;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class TextFilter {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter argument (or several args space separated):");
        String argumentLine = sc.nextLine();
        boolean isWord = isWord(argumentLine);
        String[] arguments = argumentLine.replaceAll("\\s+", " ").split(" ");
        System.out.println("Enter text (press key 'enter' to exit):");

        List<String> appropriatedStrings = new ArrayList<>();
        String next = sc.nextLine();

        while (!next.isEmpty()) {
            String[] words = next.split(" ");
            boolean isFound;
            if (isWord) {
                isFound = compareWordsWithStringArgs(words, arguments);
            } else {
                isFound = compareWordsWithRegex(words, argumentLine);
            }

            if (isFound) {
                appropriatedStrings.add(next);
            }
            next = sc.nextLine();
        }
        System.out.println("---------output------------output---------output---------");
        for (String string : appropriatedStrings) {
            System.out.println(string);
        }
        sc.close();

    }

    private static boolean compareWordsWithRegex(String[] words,String argumentLine) {
        boolean isFound = false;
        for (String word : words) {
            isFound = Pattern.matches(argumentLine, word);
            if (isFound) break;
        }
        return isFound;
    }

    private static boolean compareWordsWithStringArgs(String[] words, String[] arguments) {
        boolean isFound = false;
        for (String word : words) {
            for (String argument : arguments) {
                isFound = argument.equals(word);
                if (isFound) break;
            }
            if (isFound) break;
        }
        return isFound;
    }


    private static boolean isWord(final String argumentLine) {
        boolean isWord = Pattern.matches("[A-Za-z ]+", argumentLine);
        if (!isWord) {
            try {
                Pattern.compile(argumentLine);
            } catch (PatternSyntaxException e) {
                isWord = true;
            }
        }
        return isWord;
    }
}
