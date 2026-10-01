package views;

import controllers.FileController;
import controllers.ImageController;
import models.CircleModel;
import models.LightModel;
import models.RectangleModel;
import models.LineModel;
import krzywe.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class MainFrame extends JFrame {

    // Domyślne wymiary okna aplikacji
    private final static Integer DEFAULT_WIDTH = 800;
    private final static Integer DEFAULT_HEIGHT = 600;

    // Panele do wyświetlania obrazów
    private final ImagePanel leftPanel;
    private final ImagePanel rightPanel;

    // Pasek menu aplikacji
    private final MenuBar menuBar;

    // Kontrolery obsługujące obrazy oraz pliki
    private final ImageController imageController;
    private final FileController fileController;

    public MainFrame() {
        super("Grafika komputerowa");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Inicjalizacja komponentów
        leftPanel = new ImagePanel("Obraz wczytany");
        rightPanel = new ImagePanel("Obraz zmodyfikowany");
        menuBar = new MenuBar();

        // Inicjalizacja kontrolerów
        imageController = new ImageController(this);
        fileController = new FileController(this);

        // Utworzenie kontenera do organizacji komponentów interfejsu użytkownika, ustawiamy siatkę 1x2 dla 2 paneli z obrazami
        JPanel contentPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        contentPanel.add(leftPanel);
        contentPanel.add(rightPanel);
        add(contentPanel, BorderLayout.CENTER);


        setJMenuBar(menuBar); // Dodanie menu do okna
        setMenuBarListeners(); // Ustawienie nasłuchu na zdarzenia wywołania opcji z menubar

        setLocationRelativeTo(null); // Centrowanie okna na ekranie
        setVisible(true); // Ustawienie widoczności okna
    }

    public ImagePanel getRightPanel() {
        return rightPanel;
    }

    public ImagePanel getLeftPanel() {
        return leftPanel;
    }

    /**
     * Dostosowuje rozmiar okna do załadowanego obrazu z lewego panelu.
     *
     * <p>
     * Metoda pobiera obraz z lewego panelu i sprawdza jego rozmiary.
     * Szerokość i wysokość okna są zwiększane w zależności od wymiarów obrazu
     * </p>
     *
     * @see javax.swing.JFrame#setSize(int, int)
     * @see javax.swing.JFrame#setLocationRelativeTo(java.awt.Component)
     */
    public void adjustWindowSize() {
        var image = leftPanel.getModel().getImage();
        if (image == null) {
            return;
        }

        // Obliczenie nowej szerokości okna.
        // Okno powinno mieć co najmniej dwukrotność szerokości obrazu.
        // + 100 - dodaje dodatkową przestrzeń dla marginesów.
        int newWidth = Math.max(getWidth(), image.getWidth() * 2 + 100);

        // Obliczenie nowej wysokości okna.
        // + 100 - dodaje dodatkową przestrzeń dla marginesów.
        int newHeight = Math.max(getHeight(), image.getHeight() + 100);
        setSize(newWidth, newHeight);
        setLocationRelativeTo(null);
    }

    /**
     * <p>Metoda inicjalizację obsługę zdarzeń dla elementów menu w pasku narzędzi.
     * Wykorzystuje wyrażenia lambda jako implementację interfejsu {@code java.awt.event.ActionListener}.<p>
     *
     * <p>Każde wywołanie {@code addActionListener(...)} wymaga przekazania obiektu implementującego interfejs {@code ActionListener}.
     * Zamiast tworzenia anonimowych klas wewnętrznych, wykorzystujemy wyrażenia lambda {@code (_ -> metoda())}.
     * _ jest tutaj symbolem oznaczającym, że argument (obiekt zdarzenia {@code ActionEvent}) nie jest wykorzystywany.
     * Po prawej stronie operatora {@code ->} znajduje się wywołanie metody, które zostanie wykonane po kliknięciu elementu menu.</p>
     *
     * <p>Jako argument {@code addActionListener(...)} można przekazać również anonimową klasę wewnętrzną.</p>
     *
     * <pre>
     * {@code
     *      menuBar.getOpenFileMenuItem().addActionListener(new ActionListener() {
     *          @Override
     *          public void actionPerformed(ActionEvent e) {
     *              showFileChooserDialog();
     *          }
     *      });
     * }
     * </pre>
     *
     * @see java.awt.event.ActionListener
     * @see java.awt.event.ActionListener#actionPerformed(ActionEvent)
     * @see javax.swing.AbstractButton#addActionListener(ActionListener)
     */
    private void setMenuBarListeners(){
        menuBar.getOpenFileMenuItem().addActionListener(_ -> showFileChooserDialog());

        menuBar.getSaveFileMenuItem().addActionListener(_ -> showSaveFileDialog());
        menuBar.getExitMenuItem().addActionListener(_ -> System.exit(0));

        menuBar.getCopyLeftPanelMenuItem().addActionListener(_ -> imageController.copyLeftPanel());
        menuBar.getClearLeftPanelMenuItem().addActionListener(_ -> imageController.clearLeftPanel());

        menuBar.getClearRightPanelMenuItem().addActionListener(_ -> imageController.clearRightPanel());
        menuBar.getCopyRightPanelMenuItem().addActionListener(_->imageController.copyRightPanel());
        // TODO: Dodać nasłuch na opcję kopiowania obrazu z prawego panelu do lewego panelu. W addActionListener należy wywołać metodę copyRightPanel() z kontrolera ImageController.

        menuBar.getDrawCircleMenuItem().addActionListener(_ -> showCircleDialog());
        menuBar.getDrawRectangleMenuItem().addActionListener(_ -> showRectangleDialog());
        menuBar.getDrawLineMenuItem().addActionListener(_-> showLineDialog());
        menuBar.getGreyMenuItem().addActionListener(_ -> showGreyDialog());
        menuBar.getLightMenuItem().addActionListener(_ -> showLightDialog());
        menuBar.getNegationMenuItem().addActionListener(_ -> showNegationDialog());
        menuBar.getBrightnessMenuItem().addActionListener(_->imageController.brightness());
        menuBar.getFilterMenuItem().addActionListener(_ -> showFilterChooserDialog());
        menuBar.getGradientMenuItem().addActionListener(_ -> showGradientDialog());
        menuBar.getCurvesMenuItem().addActionListener(_ -> EventQueue.invokeLater(PanelKrzywe::new));

        // TODO: Dodać nasłuch na opcję rysowania prostokąta. W addActionListener należy wywołać metodę showRectangleDialog().

    }

    /**
     * Metoda otwiera okno dialogowe umożliwiające użytkownikowi wprowadzenie parametrów prostokąta, który zostanie narysowany na wczytanym obrazie.
     */
    private void showRectangleDialog() {
        RectangleDialog dialog = new RectangleDialog(this);
        dialog.setVisible(true);
        RectangleModel rectangle = dialog.getRectangle();

        if (rectangle != null) {
            imageController.drawRectangle(rectangle);
        }
    }

    private void showLineDialog() {
        LineDialog dialog = new LineDialog(this);
        dialog.setVisible(true);
        LineModel line = dialog.getLine();

        if (line != null) {
            imageController.drawLine(line);
        }
    }

            // TODO: Wyświetlić okno dialogowe z formularzem dla parametrów prostokąta. Wywołać metodę kontrolera drawRectangle().

            /**
             * Metoda otwiera okno dialogowe umożliwiające użytkownikowi wprowadzenie parametrów koła, który zostanie narysowane na wczytanym obrazie.
             *
             * <p>
             * Tworzy instancję okna dialogowego {@code CircleDialog}, który wyświetla formularz do wprowadzania parametrów koła.
             * Po utworzeniu instancji okna dialogowego należy pokazać komponent przez wywołanie funkcji {@code dialog.setVisible(true)}
             * </p>
             *
             * @see CircleDialog
             * @see CircleModel
             * @see ImageController#drawCircle(CircleModel)
             */
            private void showCircleDialog(){
                CircleDialog dialog = new CircleDialog(this);
                dialog.setVisible(true);
                CircleModel circle = dialog.getCircle();

                if (circle != null) {
                    imageController.drawCircle(circle);
                }
            }

            /**
             * Metoda otwiera okno dialogowe wyboru pliku graficznego.
             *
             * <p>Tworzy instancję {@code JFileChooser}, umożliwiając użytkownikowi wybór pliku.
             * Przekazuje plik do kontrolera {@code ImageController} w celu załadowania zdjęcia do lewego panelu graficznego.</p>
             *
             * @see JFileChooser
             * @see ImageController#loadImage(File)
             */
            private void showFileChooserDialog() {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Wybierz plik graficzny");
                int returnValue = fileChooser.showOpenDialog(this);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    imageController.loadImage(file);
                }
            }

            /**
             * Metoda otwiera okno dialogowe zapisu pliku graficznego.
             *
             * <p>
             * Tworzy instancję {@code JFileChooser}, ustawiając filtr plików obsługujący formaty BMP i PNG.
             * Po wybraniu miejscu zapisu przekazuje plik do kontrolera {@code FileController} w celu zapisania obrazu z prawego panelu graficznego.
             * </p>
             *
             * @see JFileChooser
             * @see FileController#saveFile(File)
             */
            private void showSaveFileDialog(){
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Zapisz obraz");
                fileChooser.setFileFilter(new FileNameExtensionFilter("BMP & PNG Images", "bmp", "png"));
                int returnValue = fileChooser.showSaveDialog(this);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    fileController.saveFile(file);
                }
            }
    private void showGreyDialog() {
        GreyDialog dialog = new GreyDialog(this,imageController);
        dialog.setVisible(true);

    }
    private void showNegationDialog() {
        NegationDialog dialog = new NegationDialog(this,imageController);
        dialog.setVisible(true);
    }
    private void showLightDialog(){
        LightDialog dialog = new LightDialog(this);
        dialog.setVisible(true);
        LightModel lightModel = dialog.getLight();

        if (lightModel != null) {
            imageController.lightCont(lightModel);
        }
    }

    private void showGradientDialog() {
        GradientDialog dialog = new GradientDialog(this,imageController);
        dialog.setVisible(true);
    }

    private void showFilterChooserDialog() {
        if (leftPanel.getModel() == null || leftPanel.getModel().getImage() == null) {
            JOptionPane.showMessageDialog(this, "Brak załadowanego obrazu!", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Wybierz plik z maską");
        FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("Pliki tekstowe (*.txt)", "txt");
        fileChooser.setFileFilter(txtFilter);
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                Map.Entry<double[][], Double> entry =
                        imageController.loadMaskFromFile(file.getPath());
                double[][] mask = entry.getKey();
                double norm = entry.getValue();
                imageController.applyMask(mask, norm);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Nie udało się wczytac maski:\n" + ex.getMessage(),
                        "Błąd ładowania maski",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
}

