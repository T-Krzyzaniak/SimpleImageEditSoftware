package views;
import models.RectangleModel;

import javax.swing.*;
import java.awt.*;

/**
 * Klasa okna dialogowego do wprowadzania parametrów prostokąta.
 *
 * <p>Umożliwia użytkownikowi podanie współrzędnych, wielkości oraz wybór koloru prostokąta.
 * Po zatwierdzeniu danych generowany jest obiekt {@link RectangleModel}, w którym przechowywane są informację
 * wykorzystywane do rysowania prostokąta na obrazie.</p>
 *
 * @see RectangleModel
 */
public class RectangleDialog extends JDialog {

    // TODO: Zaimplementuj pola tekstowe do wprowadzania współrzędnych i wymiarów prostokąta, zmienną do przechowywania wybranego kolor oraz flagę oznaczającą, czy użytkownik potwierdził dane.
    private final JTextField xField = new JTextField("200");
    private final JTextField yField = new JTextField("100");
    private final JTextField widthField = new JTextField("200");
    private final JTextField heightField = new JTextField("100");
    private Color selectedColor = Color.RED;
    private boolean confirmed = false;

    public RectangleDialog(JFrame parent) {
        // TODO: Zaimplementuj konstruktor, ustawiając tytuł, rozmiar i układ okna dialogowego.
        super(parent, "Podaj parametry", true);
        setSize(300, 300);
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
        panel.add(new JLabel("Szerokosc:"));
        panel.add(widthField);
        panel.add(new JLabel("Wysokość"));
        panel.add(heightField);

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

    public RectangleModel getRectangle() {
        // TODO: Jeśli użytkownik potwierdził dane, zwróć nowy obiekt RectangleModel z odpowiednimi wartościami.
        if (confirmed) {
            return new RectangleModel(
                    parseField(xField),
                    parseField(yField),
                    parseField(widthField),
                    parseField(heightField),
                    selectedColor
            );
        }
        return null;
    }


    private Boolean validateFields() {
        // TODO: Sprawdź, poprawność danych.
        return parseField(xField) != null && parseField(yField) != null &&
                parseField(widthField) != null && parseField(heightField) != null &&
                selectedColor != null;
    }

    private Integer parseField(JTextField field) {
        try {
            return Integer.parseInt(field.getText());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}