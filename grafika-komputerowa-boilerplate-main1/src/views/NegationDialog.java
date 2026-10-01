package views;

import controllers.ImageController;

import javax.swing.*;
import java.awt.*;

    public class NegationDialog extends JDialog {
        private final JButton negation = new JButton("Negacja");

        private final ImageController controller;

        public NegationDialog(JFrame parent, ImageController controller) {
            super(parent, "Podaj parametry", true);
            setSize(100, 150);
            setLocationRelativeTo(parent);
            setLayout(new BorderLayout());

            JPanel panel = getMainPanel();
            add(panel, BorderLayout.CENTER);

            JPanel buttonPanel = getActionPanel();
            add(buttonPanel, BorderLayout.SOUTH);
            this.controller = controller;
        }

        private JPanel getMainPanel() {
            JPanel panel = new JPanel(new GridLayout(1, 1, 10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(10, 5, 0, 5));

            negation.addActionListener(e -> {
                controller.negation();
                dispose();
            });
            panel.add(negation);
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
