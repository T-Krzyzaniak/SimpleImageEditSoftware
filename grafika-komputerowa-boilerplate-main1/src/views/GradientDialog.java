package views;

import javax.swing.*;
import java.awt.*;
import controllers.ImageController;
import models.GradientModel;
import models.LightModel;

public class GradientDialog extends JDialog {
    private final JButton discrete = new JButton("G.dyskretny");
    private final JButton roberts = new JButton("G.Robertsa");

    private final JButton robertsNegated = new JButton("G.Robertsa (negacja)");
    private final JButton discreteNegated = new JButton("G.dyskretny (negacja)");

    private final JButton edgesOriginal = new JButton("Krawędzie na oryginale");
    private final JTextField threshold = new JTextField("50");
    private Boolean confirmed = false;
    private final ImageController controller;

    public GradientDialog(JFrame parent, ImageController controller) {
        super(parent, "Podaj parametry", true);
        setSize(600, 250);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel panel = getMainPanel();
        add(panel, BorderLayout.CENTER);

        JPanel buttonPanel = getActionPanel();
        add(buttonPanel, BorderLayout.SOUTH);
        this.controller = controller;
    }


    private JPanel getMainPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        panel.add(new JLabel("Zmień wartość progu"));
        panel.add(threshold);

        discrete.addActionListener(e -> {
            controller.discreteGradient();
            dispose();
        });
        roberts.addActionListener(e -> {
            if (validateFields()) {
                confirmed = true;
                controller.robertsGradient(getThreshold());
            } else {
                JOptionPane.showMessageDialog(this, "Nieprawidłowe dane!", "Błąd", JOptionPane.ERROR_MESSAGE);
            }
            dispose();
        });
        discreteNegated.addActionListener(e -> {

            controller.discreteGradient();
            controller.negateRightPanel();
            dispose();
        });

        robertsNegated.addActionListener(e -> {
            if (validateFields()) {
                confirmed = true;
                controller.robertsGradient(getThreshold());
                controller.negateRightPanel();
            } else {
                JOptionPane.showMessageDialog(this, "Nieprawidłowe dane!", "Błąd", JOptionPane.ERROR_MESSAGE);
            }
            dispose();
        });


        edgesOriginal.addActionListener(e -> {
            if (validateFields()) {
                confirmed = true;
                controller.edgesOnOriginal(getThreshold());
            } else {
                JOptionPane.showMessageDialog(this, "Nieprawidłowe dane!", "Błąd", JOptionPane.ERROR_MESSAGE);
            }
            dispose();
        });
        panel.add(discrete);
        panel.add(roberts);
        panel.add(discreteNegated);
        panel.add(robertsNegated);
        panel.add(edgesOriginal);
        return panel;
    }

    private JPanel getActionPanel() {
        JPanel buttonPanel = new JPanel();

        JButton cancelButton = new JButton("Anuluj");

        cancelButton.addActionListener(e -> dispose());
        buttonPanel.add(cancelButton);
        return buttonPanel;
    }
    private boolean validateFields() {
        return parseField(threshold) != null &&
                parseField(threshold) >= 0 &&
                parseField(threshold) <= 255;
    }
    public int getThreshold() {
        if (!confirmed) {
            throw new IllegalStateException("Nie zatwierdzono wartości progu!");
        }
        return parseField(threshold);
    }

    private Integer parseField(JTextField field) {
        try {
            return Integer.parseInt(field.getText());
        } catch (NumberFormatException e) {
            return null;
        }
    }

}
