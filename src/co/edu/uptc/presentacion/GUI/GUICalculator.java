package co.edu.uptc.presentacion.GUI;

import co.edu.uptc.logica.Calculator;
import co.edu.uptc.logica.TreeNode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

public class GUICalculator extends JFrame {
    private JTextField textField;
    private JButton evaluateButton;
    private JPanel buttonPanel;
    private Calculator builder;

    public GUICalculator() {
        setTitle("Calculadora usando arbol binario");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        textField = new JTextField();
        evaluateButton = new JButton("=");
        buttonPanel = new JPanel();


        String[] buttons = {
                "1", "2", "3", "4", "5",
                "6", "7", "8", "9", "0",
                "+", "-", "*", "/", "^",
                "(", ")", ".", "C", "="
        };

        buttonPanel.setLayout(new GridLayout(5, 5));
        for (String text : buttons) {
            JButton button = new JButton(text);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (text.equals("=")) {
                        evaluateExpression();
                    } else if (text.equals("C")) {
                        textField.setText("");
                    } else {
                        textField.setText(textField.getText() + text);
                    }
                }
            });
            buttonPanel.add(button);
        }

        setLayout(new BorderLayout());
        add(textField, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
    }

    private void evaluateExpression() {
        try {
            String expression = textField.getText();
            if (!validarParentesis(expression)) {
                JOptionPane.showMessageDialog(null, "Error: Los parentesis no están balanceados");
                return;
            }
            builder = new Calculator(expression);
            TreeNode<String> root = builder.build();
            double result = builder.evaluateTree(root);
            textField.setText(Double.toString(result));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: Expresion invalida");
        }
    }

    public boolean validarParentesis(String expression) {
        Stack<Character> pila = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char caracter = expression.charAt(i);

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

}
