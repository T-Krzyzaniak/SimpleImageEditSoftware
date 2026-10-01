package views;

import models.LightModel;

import javax.swing.*;
import java.awt.*;

public class LightDialog extends JDialog {
    private final JTextField light = new JTextField("0");
    private final JTextField contrast = new JTextField("1");
    private Boolean confirmed = false;




    public LightDialog(JFrame parent) {

        super(parent, "Wprowadź parametr", true);
        setSize(150, 200);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel panel = getMainPanel();
        add(panel, BorderLayout.CENTER);

        JPanel buttonPanel = getActionPanel();
        add(buttonPanel, BorderLayout.SOUTH);

    }



    private JPanel getMainPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        panel.add(new JLabel("Zmień jasność"));
        panel.add(light);
        panel.add(new JLabel("Zmień kontrast"));
        panel.add(contrast);

        return panel;
    }

    private JPanel getActionPanel() {

        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Anuluj");

        okButton.addActionListener(e -> {
            if (validateFields()) {
                confirmed = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Nieprawidłowe dane!", "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Obsługa przycisku Anuluj przez zamknięcie okna dialogowego.
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        return buttonPanel;
    }


    private Boolean validateFields() {
        return parseField(light) != null &&
                parseField2(contrast) != null &&
                parseField2(contrast)!=0;
    }
    public LightModel getLight() {
        if (confirmed) {
            return new LightModel(
                    parseField(light),
                    parseField2(contrast)

            );
        }
        return null;
    }


    private Integer parseField(JTextField field) {
        try {
            return Integer.parseInt(field.getText());
        } catch (NumberFormatException e) {
            return null;
        }
    }
    private Double parseField2(JTextField field) {
        try {
            return Double.parseDouble(field.getText());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
