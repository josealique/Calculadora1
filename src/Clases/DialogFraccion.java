package Clases;

import javax.swing.*;
import java.awt.event.*;

/**
 * @author jmaliquer
 * @version 1.0
 * @since 13-05-2019
 */

public class DialogFraccion extends JDialog {
    private JPanel contentPane;
    private JButton Calcular;
    private JButton buttonCancel;
    private JTextField Numerador;
    private JTextField Denominador;
    private JTextField Resultado;
    private JButton borrarButton;

    public DialogFraccion() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(Calcular);

        Calcular.addActionListener(e -> calcularFraccion());

        buttonCancel.addActionListener(e -> onCancel());

        borrarButton.addActionListener(e -> {
            Numerador.setText("");
            Denominador.setText("");
            Resultado.setText("");
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void calcularFraccion(){
        Fraccion f;
        int numerador = Integer.parseInt(Numerador.getText());
        int denominador = Integer.parseInt(Denominador.getText());
        f = new Fraccion(numerador,denominador);
        f.simplificar();
        Resultado.setText(String.valueOf(f));
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}