package com.company;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Calculator {

    public static final String SPACES_BETWEEN_DIGITS_REGEX = "[0-9,.][ ]+[0-9,.]+";
    public static final String APPROPRIATE_SYMBOLS_REGEX = "[0-9,.+-/*^() ]+";
    public static final String OPERATOR_AT_THE_END_REGEX = "[0-9,.+-/*^() ]+[+-/*^]";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter expression:");
        String expression = sc.nextLine();
        sc.close();

        if (!isValidExpression(expression)) {
            System.out.println("invalid expression");
            return;
        }

        String[] separatedExpressionTokens = convertToExpressionTokens(expression);
        Deque<String> reverseNotationOutput = buildReverseNotation(separatedExpressionTokens);
        String result = calculateReverseNotation(reverseNotationOutput);

        if (result.equals("division by zero")) {
            System.out.println("division by zero");
        } else {
            System.out.println("Result: " + result);
        }
    }

    private static boolean isValidExpression(String expression) {
        Pattern pattern = Pattern.compile(SPACES_BETWEEN_DIGITS_REGEX);
        Matcher matcher = pattern.matcher(expression);
        return !matcher.find() && Pattern.matches(APPROPRIATE_SYMBOLS_REGEX, expression) && !Pattern.matches(OPERATOR_AT_THE_END_REGEX, expression);
    }

    private static String calculateReverseNotation(Deque<String> reverseNotationOutput) {
        Deque<String> stack = new LinkedList<>();
        while (reverseNotationOutput.peek() != null) {
            String currentItem = reverseNotationOutput.poll();
            if (currentItem.matches("[-+/*^]")) {
                String second = stack.pollLast();
                String first = stack.pollLast();
                String res = doOperation(first, second, currentItem);
                if (res.equals("division by zero")) {
                    return res;
                }
                stack.add(res);
            } else {
                stack.add(currentItem);
            }
        }
        return stack.poll();
    }

    private static Deque<String> buildReverseNotation(String[] separatedExpressionTokens) {
        Deque<String> reverseNotationOutput = new LinkedList<>();
        Deque<String> operators = new LinkedList<>();
        for (String currentToken : separatedExpressionTokens) {
            if (currentToken.matches("[+-/*^(]")) {
                if (operators.peekLast() != null && !operators.peekLast().equals("(") && (getPriority(currentToken) <= getPriority(operators.peekLast()))) {
                    reverseNotationOutput.add(operators.pollLast());
                }
                operators.add(currentToken);
            } else if (currentToken.matches("[)]")) {
                while (operators.peekLast() != null && !operators.peekLast().equals("(")) {
                    reverseNotationOutput.add(operators.pollLast());
                }
                operators.pollLast();
            } else {
                reverseNotationOutput.add(currentToken);
            }
        }
        while (operators.peekLast() != null) {
            reverseNotationOutput.add(operators.pollLast());
        }
        return reverseNotationOutput;
    }

    private static String[] convertToExpressionTokens(String expression) {
        String exp = expression.replaceAll("\\s+", "");
        StringBuilder stringBuilder = new StringBuilder();
        Boolean isPow = false;
        for (int i = 0; i < exp.length(); i++) {
            String currentSymbol = Character.toString(exp.charAt(i));
            if (currentSymbol.matches("[-+/*^()]")) {
                if (isPow) {
                    stringBuilder.append(" ").append(currentSymbol);
                } else {
                    stringBuilder.append(" ").append(currentSymbol).append(" ");
                }
                if (currentSymbol.equals("^")) {
                    isPow = true;
                }
            } else {
                if (currentSymbol.equals(",")) {
                    stringBuilder.append(".");
                } else {
                    stringBuilder.append(currentSymbol);
                }
                isPow = false;
            }
        }
        return stringBuilder.toString().trim().split("\\s+");
    }

    private static int getPriority(String value) {
        int out = 0;
        switch (value) {
            case "(":
                out = 4;
                break;
            case "^":
                out = 3;
                break;
            case "*":
                out = 2;
                break;
            case "/":
                out = 2;
                break;
            case "+":
                out = 1;
                break;
            case "-":
                out = 1;
                break;
        }
        return out;

    }

    private static String doOperation(String first, String second, String operator) {
        Number out = 0;
        switch (operator) {
            case "^":
                out = Math.pow(Double.parseDouble(first), Double.parseDouble(second));
                break;
            case "*":
                out = performOperation(first, second, Operations.MUL);
                break;
            case "/":
                if (second.equals("0") || second.equals("0.0")) {
                    return "division by zero";
                }
                BigDecimal fst = new BigDecimal(first);
                BigDecimal snd = new BigDecimal(second);
                out = fst.divide(snd);
                break;
            case "+":
                out = performOperation(first, second, Operations.ADD);
                break;
            case "-":
                out = performOperation(first, second, Operations.SUBTRACT);
                break;
        }
        return out.toString();


    }

    private static Number performOperation(String first, String second, Operations operation) {
        Number out = null;
        if (first.matches("[0-9-]+") && second.matches("[0-9-]+")) {
            BigInteger fst = new BigInteger(first);
            BigInteger snd = new BigInteger(second);
            out = process(operation, fst, snd);
        } else {
            BigDecimal fst = new BigDecimal(first);
            BigDecimal snd = new BigDecimal(second);
            out = process(operation, fst, snd);
        }
        return out;
    }

    private static Number process(Operations operation, Number fst, Number snd) {

        if (operation == Operations.MUL) {
            return fst.getClass() == BigInteger.class ?
                    ((BigInteger) fst).multiply((BigInteger) snd) : ((BigDecimal) fst).multiply((BigDecimal) snd);
        } else if (operation == Operations.ADD) {
            return fst.getClass() == BigInteger.class ?
                    ((BigInteger) fst).add((BigInteger) snd) : ((BigDecimal) fst).add((BigDecimal) snd);
        } else if (operation == Operations.SUBTRACT) {
            return fst.getClass() == BigInteger.class ?
                    ((BigInteger) fst).subtract((BigInteger) snd) : ((BigDecimal) fst).subtract((BigDecimal) snd);
        }
        return null;
    }

    public enum Operations {
        MUL, ADD, SUBTRACT
    }

}
