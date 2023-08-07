package co.edu.uptc.logica;

import java.util.Comparator;
import java.util.Stack;

public class Handling<T> {

    private TreeNode<T> root;
    //private Comparator<T>comparator;

    //public Handling(Comparator<T> comparator){ this.comparator = comparator;}

    //Metodo para organizara la expresion
    public double arrangeExpression(String expressionToEvaluate){
        Stack<Double> nums = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < expressionToEvaluate.length(); i++) {
            char c = expressionToEvaluate.charAt(i);

            if (Character.isDigit(c)) {//Si el caracter es un digito
                StringBuilder numStr = new StringBuilder();//Construye una cadena con todo el numero
                //El while verifica si el carácter en la posición i es un dígito o un punto decimal.
                //para construir el número que se va a guardar en numStr, ya que podría contener dígitos y un punto decimal.
                while (i < expressionToEvaluate.length() && (Character.isDigit(expressionToEvaluate.charAt(i)) || expressionToEvaluate.charAt(i) == '.')) {
                    numStr.append(expressionToEvaluate.charAt(i));//Va agregando el caracter de la posición i a la numero numStr
                    i++;//Avanza al siguiente caracter y seguira avanzando hasta que ya no encuetre mas numeros o puntos decimales
                }
                i--;//devuelve a i a la posicion del ultimo digito valido
                nums.push(Double.parseDouble(numStr.toString()));//Convierte el numero construido a un Double y lo agrega a la pila
            //Inicio de validacion de los parentesis
            } else if (c == '(') {
                operators.push(c);
            } else if (c == ')') {
                while (operators.peek() != '(') {
                    performOperation(nums, operators);
                }
                operators.pop(); // Eliminar el '('
            } else if (isOperator(c)) {//Evalua si el caracter encontrado pertenece a uno de los caracteres permitidos
                //El while se ejecuta mientras hayan operadores a evaluar
                //y evalua si el ultimo operador guardado en la pila es mayor o igual al operador actuar
                while (!operators.isEmpty() && operationsPriority(operators.peek()) >= operationsPriority(c)) {
                    performOperation(nums, operators);//Realizara la operacion con los dos ultimos numeros guardados en la pila
                    //y con el ultimo operador guardado en la pila
                }
                operators.push(c);//Se agrega el operador actual a la pila
            }
        }

        while (!operators.isEmpty()) {
            performOperation(nums, operators);//Realiza las operaciones necesarias con los ultimos elementos
            //de las respectivas filas hasta que se no hayan mas operadores
        }

        return nums.pop();//Elimina y devuleve el ultimo elemento de la pila nums
    }

    //Pregunta si el caracter entrante es pertenecea a alguno de los caracteres conocidos
    public static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }


    //Establece la prioridad de las operaciones
    public static int operationsPriority(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
        }
        return -1;
    }


    //Soluciona la expresión entrante
    public static void performOperation(Stack<Double> nums, Stack<Character> operators) {
        double b = nums.pop();
        double a = nums.pop();
        char operator = operators.pop();

        double solution = 0;
        switch (operator) {
            case '+':
                solution = a + b;
                break;
            case '-':
                solution = a - b;
                break;
            case '*':
                solution = a * b;
                break;
            case '/':
                solution = a / b;
                break;
            case '^':
                solution = Math.pow(a, b);
                break;
        }

        nums.push(solution);
    }

    /*
    public boolean isEmpty(){
        return root==null;
    }
    public void addNode(T info){
        if (isEmpty()){
            root = new TreeNode<>(info);
        }else {
            TreeNode<T> aux = root;
            TreeNode<T> ant = null;
            while (aux != null){
                ant = aux;
                aux = ((comparator.compare(info,aux.getInfo())<0) ? aux.getLeft() : aux.getRigth());
            }
            if (comparator.compare(info,ant.getInfo())<0){
                ant.setLeft(new TreeNode<>(info));
            }else {
                ant.setRigth(new TreeNode<>(info));
            }
        }
    }
    */

}
