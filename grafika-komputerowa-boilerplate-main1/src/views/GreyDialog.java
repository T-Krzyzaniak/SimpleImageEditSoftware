package views;

import controllers.ImageController;

import javax.swing.*;
import java.awt.*;

public class GreyDialog extends JDialog {
    private final JButton srednia = new JButton("Średnia");

    private final JButton wartoscR = new JButton("Wartość R");
    private final JButton wartoscG = new JButton("Wartość G");
    private final JButton wartoscB = new JButton("Wartość B");

    private final JButton yuv = new JButton("Wartość YUV");

    private final ImageController controller;

    public GreyDialog(JFrame parent, ImageController controller) {

        super(parent, "Wybierz metodę", true);
        setSize(150, 200);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel panel = getMainPanel();
        add(panel, BorderLayout.CENTER);

        JPanel buttonPanel = getActionPanel();
        add(buttonPanel, BorderLayout.SOUTH);
        this.controller = controller;
    }



    private JPanel getMainPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 10, 5, 10));

        srednia.addActionListener(e -> {
            controller.greyAverage();
            dispose();
        });
        wartoscR.addActionListener(e -> {
            controller.greyRBased();
            dispose();
        });
        wartoscG.addActionListener(e->{
            controller.greyGBased();
            dispose();
        });
        wartoscB.addActionListener(e->{
            controller.greyBBased();
            dispose();
        });
        yuv.addActionListener(e -> {
            controller.greyYUV();
            dispose();
        });

        panel.add(srednia);
        panel.add(wartoscR);
        panel.add(wartoscG);
        panel.add(wartoscB);
        panel.add(yuv);

        return panel;
    }
    private JPanel getActionPanel() {
        JPanel buttonPanel = new JPanel();

        JButton cancelButton = new JButton("Anuluj");

        cancelButton.addActionListener(e -> dispose());
        buttonPanel.add(cancelButton);
        return buttonPanel;
    }
}
