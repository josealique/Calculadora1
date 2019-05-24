package Clases;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;

/**
 * @author jmaliquer
 * @version 1.0
 * @since 13-05-2019
 */
public class CambioDeDivisas extends JDialog {
    private JPanel contentPane;
    private JButton Convertir;
    private JButton Cancelar;
    private JTextField CantidadDivisa;
    private JComboBox<String> MonedaOriginal;
    private JComboBox<String> MonedaFinal;
    private JTextField Resultado;
    private final double libra = 0.868000;
    private final double euro = 0.891902;
    private final double dolar = 1.121200;
    private final double yen = 122.879997;
    private final double dolarAustraliano = 1.613900;
    private Map<String,Double> mapa = new HashMap<>();

    public CambioDeDivisas() {
        setContentPane(contentPane);
        setModalityType(JDialog.DEFAULT_MODALITY_TYPE);
        getRootPane().setDefaultButton(Convertir);

        MonedaOriginal.addItem("Euro");

        MonedaFinal.addItem("Dolar");
        MonedaFinal.addItem("Yen");
        MonedaFinal.addItem("Dolar Australiano");
        MonedaFinal.addItem("Libra");

        mapa.put("Dolar",dolar);
        mapa.put("Yen",yen);
        mapa.put("Dolar Australiano",dolarAustraliano);
        mapa.put("Libra",libra);

        Convertir.addActionListener(e -> conversion());

        Cancelar.addActionListener(e -> onCancel());

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

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    /**
     * método que se encargará de convertir las monedas de Euro a su
     * respectiva selección
     * @throws Exception
     * Puede tirar una excepción al encontrar campos vacíos o con letras
     */
    private void conversion(){
        String s = CantidadDivisa.getText();
        if (s.isEmpty()){
            JOptionPane.showMessageDialog(null, "El campo no puede estar vacio", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            double cantidad = Double.parseDouble(CantidadDivisa.getText());
            double resultado;
            resultado =  cantidad * mapa.get(MonedaFinal.getSelectedItem().toString());
            Resultado.setText(String.valueOf(resultado));
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "El campo de divisa no puede contener letras", "Alerta", JOptionPane.WARNING_MESSAGE);
        }
    }
}