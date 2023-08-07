package co.edu.uptc.presentacion.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorGUI extends JFrame implements ActionListener {
    private JTextField textField;

    public CalculatorGUI() {
        setTitle("Calculator");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 24));
        add(textField, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(5, 4, 10, 10));

        String[] buttonLabels = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", "(", ")", "+",
                "C", "^", "=", "."
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.PLAIN, 18));
            button.addActionListener(this);
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if ("=".equals(command)) {
            String expression = textField.getText();
            try {
                double result = evaluateExpression(expression);
                textField.setText(Double.toString(result));
            } catch (Exception ex) {
                textField.setText("Error");
            }
        } else if ("C".equals(command)) {
            textField.setText("");
        } else {
            textField.setText(textField.getText() + command);
        }
    }

    private double evaluateExpression(String expression) {
        // Aquí deberías implementar la lógica para evaluar la expresión matemática
        // Puedes utilizar la clase ScriptEngine, por ejemplo
        // En este ejemplo, se devuelve un valor aleatorio para fines demostrativos
        return Math.random() * 100;
    }

    /*
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CalculatorGUI());
    }
    */
}
