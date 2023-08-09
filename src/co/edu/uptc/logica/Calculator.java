package co.edu.uptc.logica;

import java.util.Stack;
import java.util.ArrayList;
import java.util.List;

public class Calculator {
    private String inputExpression;

    public Calculator(String expression) {
        this.inputExpression = expression;
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

    /*
     *Es el punto de entrada para construir el arbol arbol binario a partir de la cadena
     *entrante, es el puente entre la clase GUICalculator y el método privado buildExpressionTree()
     */
    public TreeNode<String> build() {
        return buildExpressionTree(inputExpression);
    }


    /*
     *Recibe una expresión y devuelve un nodo de arbol que la representa
     */
    private TreeNode<String> buildExpressionTree(String expression) {
        char mainOperation = findMainOperation(expression);//Busca el operador principal de la expresión, el de menor nivel
        if (mainOperation == ' ') {//Si no encuentra un operador principal significa que es un numero y por lo tanto una
            //hoja del arbol
            return new TreeNode<String>(expression.trim());
        } else {
            String[] parts = splitAtMainOperation(expression, mainOperation);//Divide la expresión en dos partes alrededor
            //del operador
            //Acá se filtran esas dos nuevas expresiones quitandole los parentesis exteriores
            parts[0] = removeOuterParentheses(parts[0]);
            parts[1] = removeOuterParentheses(parts[1]);
            /*
            System.out.println("Dividing expression: " + expression);
            System.out.println("Main operation: " + mainOperation);
            System.out.println("Left expression: " + parts[0]);
            System.out.println("Right expression: " + parts[1]);
            System.out.println("----------------------");
            */

            //Inicia el arbol binario con el caracter principal como cabeza
            TreeNode<String> node = new TreeNode<String>(Character.toString(mainOperation));
            //A partir de aquí se vuelve a llamar el método para construir los subarboles apartir del principal
            node.setLeft(buildExpressionTree(parts[0]));
            node.setRight(buildExpressionTree(parts[1]));
            return node;//Devuelve el arbol construido
        }
    }

    /*
     *Elimina los parentesis exteriores de la expresión
     */
    private String removeOuterParentheses(String s) {
        s = s.trim();//Elimina los espacios en blanco al inicio y al final de la cadena
        if (s.startsWith("(") && s.endsWith(")")) {//Pregunta si la expresión está envuelta en paretesis
            return s.substring(1, s.length() - 1);//Devuelve la cadena sin parentesis
        }
        return s;
    }

    /*
     *Este método encuentra el operador principal del la expresión entrante y el operador que debe encontrar
     * es el que tiene menor nivel de anidamiento en la expresión
     */
    private char findMainOperation(String expression) {
        char[] operations = {'-', '+', '*', '/', '^'};//Orden de prioridad de operaciones de menor a mayor
        Stack<Character> stack = new Stack<>();//Se crea una pila para guardar los parentesis
        for (char operation : operations) {//Recorre el arreglo de las operaciones
            for (int i = 0; i < expression.length(); i++) {//Recorre la expresión para encontrar el operador
                //o parentesis para validarlos en la pila
                char c = expression.charAt(i);
                if (c == '(') {
                    stack.push(c);
                } else if (c == ')') {
                    stack.pop();
                } else if (c == operation && stack.isEmpty()) {//Si el operador es igual y la pila de parentesis
                    //está vacia devuelve el operador encotrado
                    return operation;
                }
            }
        }
        return ' ';//Devuelve un espacio en blanco porque no entontro un operador principal en esta expresion
    }

    /*
     *Se encarga de dividir la expresión en dos partes a partir del operador principal
     */
    private String[] splitAtMainOperation(String expression, char mainOperation) {
        int index = expression.lastIndexOf(mainOperation);//Busca la ultima aparición del operador principal en
        //la expresión
        if (index != -1) {
            return new String[] {
                    //Divid la expresion en dos partes
                    expression.substring(0, index),//La primera desde el inicio hasta una posición antes
                    // de la ubicación del operador
                    expression.substring(index + 1)
            };
        }
        return new String[] {expression, ""};//Si no encutentra el operador devuelve toda la expresión
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
