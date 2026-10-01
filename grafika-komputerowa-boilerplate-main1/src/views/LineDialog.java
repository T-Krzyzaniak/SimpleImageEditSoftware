package views;

import models.LineModel;

import javax.swing.*;
import java.awt.*;

public class LineDialog extends JDialog {
    // TODO: Zaimplementuj pola tekstowe do wprowadzania współrzędnych i wymiarów prostokąta, zmienną do przechowywania wybranego kolor oraz flagę oznaczającą, czy użytkownik potwierdził dane.
    private final JTextField xField = new JTextField("0");
    private final JTextField yField = new JTextField("20");
    private final JTextField widthField = new JTextField("125");
    private Color selectedColor = Color.RED;
    private boolean confirmed = false;

    public LineDialog(JFrame parent) {
        // TODO: Zaimplementuj konstruktor, ustawiając tytuł, rozmiar i układ okna dialogowego.
        super(parent, "Podaj parametry", true);
        setSize(300, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel panel = getMainPanel();
        add(panel, BorderLayout.CENTER);

        JPanel buttonPanel = getActionPanel();
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel getMainPanel() {
        // TODO: Utwórz panel i dodaj do niego etykiety oraz pola tekstowe.
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panel.add(new JLabel("Środek X:"));
        panel.add(xField);
        panel.add(new JLabel("Środek Y:"));
        panel.add(yField);
        panel.add(new JLabel("Długość:"));
        panel.add(widthField);

        // Wybór koloru
        JButton colorButton = new JButton("Wybierz kolor");
        colorButton.setBackground(selectedColor);

        /**
         *  Ustawnie nasłuchu na przycisk Wybierz kolor.
         *  Po kliknięciu otwiera okno dialogowe wyboru koloru ({@link JColorChooser}).
         */
        colorButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(this, "Wybierz kolor", selectedColor);
            if (newColor != null) {
                selectedColor = newColor;
                colorButton.setBackground(selectedColor);
            }
        });

        panel.add(new JLabel("Kolor:"));
        panel.add(colorButton);

        return panel;
    }

    private JPanel getActionPanel() {
        // TODO: Utwórz panel z przyciskami OK i Anuluj.
        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Anuluj");

        // Obsługa przycisku OK. Sprawdzenie poprawności danych i zamknięcie okna. Jeśli dane są niepoprawne, wyświetlany jest komunikat z błędem.
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

    public LineModel getLine() {
        // TODO: Jeśli użytkownik potwierdził dane, zwróć nowy obiekt RectangleModel z odpowiednimi wartościami.
        if (confirmed) {
            return new LineModel(
                    parseField(xField),
                    parseField(yField),
                    parseField(widthField),
                    selectedColor
            );
        }
        return null;
    }


    private Boolean validateFields() {
        // TODO: Sprawdź, poprawność danych.
        return parseField(xField) != null && parseField(yField) != null &&
                parseField(widthField) != null && selectedColor != null;
    }

    private Integer parseField(JTextField field) {
        try {
            return Integer.parseInt(field.getText());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}

