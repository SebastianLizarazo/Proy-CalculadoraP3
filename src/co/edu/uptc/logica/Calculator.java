package co.edu.uptc.logica;

import java.util.Stack;
import java.util.ArrayList;
import java.util.List;

public class Calculator {
    private String inputExpression;

    public Calculator(String expression) {
        this.inputExpression = expression;
    }

    public boolean validarParentesis() {//esta validación se hace antes de cualquier operación
        Stack<Character> pila = new Stack<>();

        for (int i = 0; i < inputExpression.length(); i++) {
            char caracter = inputExpression.charAt(i);

            if (caracter == '(') {
                pila.push(caracter);
            } else if (caracter == ')') {
                if (pila.isEmpty() || pila.pop() != '(') {
                    return false;
                }
            }
        }

        return pila.isEmpty();
    }

    public List<String> tokenizeExpression() {
        List<String> tokens = new ArrayList<>();
        String number = "";
        for (int i = 0; i < inputExpression.length(); i++) {
            char currentChar = inputExpression.charAt(i);
            if (Character.isDigit(currentChar) || currentChar == '.') {
                number += currentChar; // Agrega a la cadena del número actual
            } else {
                if (!number.isEmpty()) {
                    tokens.add(number); // Añade el número actual a los tokens
                    number = ""; // Reinicia la cadena del número
                }
                tokens.add(Character.toString(currentChar)); // Añade el operador a los tokens
            }
        }

        if (!number.isEmpty()) {
            tokens.add(number); // Añade el último número a los tokens
        }

        return tokens;
    }

    public TreeNode<String> build() {
        return buildExpressionTree(inputExpression);
    }

    private TreeNode<String> buildExpressionTree(String expression) {
        char mainOperation = findMainOperation(expression);
        if (mainOperation == ' ') {
            // Si no hay operación principal, la expresión es una hoja
            return new TreeNode<String>(expression.trim());
        } else {

            String[] parts = splitAtMainOperation(expression, mainOperation);


            //cambiando
            parts[0] = removeOuterParentheses(parts[0]);
            parts[1] = removeOuterParentheses(parts[1]);

            //
            System.out.println("Dividing expression: " + expression);
            System.out.println("Main operation: " + mainOperation);
            System.out.println("Left expression: " + parts[0]);
            System.out.println("Right expression: " + parts[1]);
            System.out.println("----------------------");

            TreeNode<String> node = new TreeNode<String>(Character.toString(mainOperation));
            node.setLeft(buildExpressionTree(parts[0]));
            node.setRight(buildExpressionTree(parts[1]));
            return node;
        }
    }

    private String removeOuterParentheses(String s) {
        s = s.trim();  // Elimina los espacios en blanco al principio y al final
        if (s.startsWith("(") && s.endsWith(")")) {
            return s.substring(1, s.length() - 1);
        }
        return s;
    }

    private char findMainOperation(String expression) {
        // Orden de la importancia de las operaciones
        char[] operations = {'-', '+', '*', '/', '^'};
        // Pila para rastrear la profundidad de los paréntesis
        Stack<Character> stack = new Stack<>();
        for (char operation : operations) {
            for (int i = 0; i < expression.length(); i++) {
                char c = expression.charAt(i);
                if (c == '(') {
                    stack.push(c);
                } else if (c == ')') {
                    stack.pop();
                } else if (c == operation && stack.isEmpty()) {
                    return operation;
                }
            }
        }
        return ' ';
    }

    private String[] splitAtMainOperation(String expression, char mainOperation) {
        int index = expression.lastIndexOf(mainOperation);
        if (index != -1) {
            return new String[] {
                    expression.substring(0, index),
                    expression.substring(index + 1)
            };
        }
        return new String[] {expression, ""};
    }

    public double evaluateTree(TreeNode<String> node) {


        if (node == null) {
            return 0;
        }

        // Si el nodo es una hoja (un número), se devuelve su valor
        if (node.getLeft() == null && node.getRight() == null) {

            return Double.parseDouble(node.getInfo());
        }

        // Evaluamos los hijos del nodo
        double leftValue = evaluateTree(node.getLeft());
        double rightValue = evaluateTree(node.getRight());

        // Dependiendo del operador del nodo, realizamos la operación correspondiente
        switch (node.getInfo()) {
            case "+":
                return leftValue + rightValue;
            case "-":
                return leftValue - rightValue;
            case "*":
                return leftValue * rightValue;
            case "/":
                if (rightValue == 0) {
                    throw new UnsupportedOperationException("Cannot divide by zero");
                }
                return leftValue / rightValue;
            case "^":
                return Math.pow(leftValue, rightValue);
            default:
                throw new UnsupportedOperationException("Unknown operator: " + node.getInfo());
        }
    }






}
