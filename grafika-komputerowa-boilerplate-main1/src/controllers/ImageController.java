package controllers;

import models.*;
import views.ImagePanel;
import views.MainFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

/**
 * Kontroler odpowiedzialny za zarządzanie operacjami na obrazach w aplikacji.
 * Obsługuje wczytywanie, czyszczenie, kopiowanie oraz rysowanie kształtów na obrazach.
 */
public class ImageController {

    private final MainFrame mainFrame;

    private final ImagePanel leftPanel;

    private final ImagePanel rightPanel;

    public ImageController(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.leftPanel = mainFrame.getLeftPanel();
        this.rightPanel = mainFrame.getRightPanel();
    }

    /**
     * Wczytuje obraz z pliku i ustawia go w lewym panelu.
     *
     * @param file Plik obrazu do wczytania.
     */
    public void loadImage(File file) {
        try {
            var image = ImageIO.read(file);

            var model = new ImageModel(image);
            leftPanel.setModel(model);
            leftPanel.repaint();

            mainFrame.adjustWindowSize(); // Dopasowanie rozmiaru okna po załadowaniu obrazu
        } catch (IOException e) {
            JOptionPane.showMessageDialog(mainFrame, "Nieznany błąd!", "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Usuwa obraz z lewego panelu.
     */
    public void clearLeftPanel() {
        leftPanel.setModel(null);
        leftPanel.repaint();
    }

    /**
     * Usuwa obraz z prawego panelu.
     */
    public void clearRightPanel() {
        rightPanel.setModel(null);
        rightPanel.repaint();
    }

    /**
     * Kopiuje obraz z lewego panelu do prawego panelu.
     * Jeśli lewy panel nie zawiera obrazu, operacja nie jest wykonywana.
     */
    public void copyLeftPanel() {
        if (leftPanel.getModel() == null) {
            return;
        }

        // Utworzenie kopii obrazu z lewego panelu, aby modyfikacje nie wpłynęły na oryginalny obraz.
        var image = leftPanel.getModel().getCopyImage();
        var model = new ImageModel(image);

        rightPanel.setModel(model);
        rightPanel.repaint();
    }

    /**
     * Kopiuje obraz z prawego panelu do lewego panelu.
     */
    public void copyRightPanel() {
        if (rightPanel.getModel() == null) {
            return;
        }

        // Utworzenie kopii obrazu z lewego panelu, aby modyfikacje nie wpłynęły na oryginalny obraz.
        var image = rightPanel.getModel().getCopyImage();
        var model = new ImageModel(image);

        leftPanel.setModel(model);
        leftPanel.repaint();
        // TODO: Zaimplementować metodę kopiowania obrazu z panelu prawego do panelu lewego.
    }

    /**
     * Rysuje koło na obrazie znajdującym się w lewym panelu i umieszcza wynik w prawym panelu.
     * Jeśli lewy panel nie zawiera obrazu, wyświetlane jest komunikat z błędem.
     *
     * @param circle Model kola.
     */
    public void drawCircle(CircleModel circle) {
        if (leftPanel.getModel() == null || leftPanel.getModel().getImage() == null) {
            JOptionPane.showMessageDialog(mainFrame, "Brak załadowanego obrazu!", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }

        var image = leftPanel.getModel().getCopyImage(); // Utworzenie kopii obrazu z lewego panelu

        var model = new ImageModel(image); // Nowa instancje modelu, utworzona z obrazem z panelu lewego.
        model.drawCircle(circle); // Modyfikacja modelu. Narysowanie koła na skopiowany obrazie.

        rightPanel.setModel(model); // Ustawienie zmodyfikowanego modelu w prawym panelu.

        rightPanel.repaint(); // Ponownie narysowanie komponentu.
    }

    /**
     * Rysuje prostokąt na obrazie znajdującym się w lewym panelu i umieszcza wynik w prawym panelu.
     * Jeśli lewy panel nie zawiera obrazu, operacja nie jest wykonywana.
     *
     * @param rectangle Model prostokąta do narysowania.
     */
    public void drawRectangle(RectangleModel rectangle) {
        // TODO: Zaimplementować rysowanie prostokąta na obrazie.
        if (leftPanel.getModel() == null || leftPanel.getModel().getImage() == null) {
            JOptionPane.showMessageDialog(mainFrame, "Brak załadowanego obrazu!", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }
        var image = leftPanel.getModel().getCopyImage(); // Utworzenie kopii obrazu z lewego panelu

        var model = new ImageModel(image); // Nowa instancje modelu, utworzona z obrazem z panelu lewego.
        model.drawRectangle(rectangle); // Modyfikacja modelu. Narysowanie prostokata na skopiowany obrazie.

        rightPanel.setModel(model); // Ustawienie zmodyfikowanego modelu w prawym panelu.
        rightPanel.repaint(); // Ponownie narysowanie komponentu.
    }
    public void drawLine(LineModel line){
        if (leftPanel.getModel() == null || leftPanel.getModel().getImage() == null) {
            JOptionPane.showMessageDialog(mainFrame, "Brak załadowanego obrazu!", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }
        var image = leftPanel.getModel().getCopyImage();
        var model = new ImageModel(image);
        model.drawLine(line);
        rightPanel.setModel(model);
        rightPanel.repaint();
    }
    public void greyAverage() {
        if (leftPanel.getModel() == null || leftPanel.getModel().getImage() == null) {
            JOptionPane.showMessageDialog(mainFrame, "Brak załadowanego obrazu!", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }
        var image = leftPanel.getModel().getCopyImage();
        var model = new ImageModel(image);
        int width = image.getWidth();
        int height = image.getHeight();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                Color color = new Color(rgb);
                int gray = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                Color grayColor = new Color(gray, gray, gray);
                image.setRGB(x, y, grayColor.getRGB());
            }
        }
        rightPanel.setModel(model);
        rightPanel.repaint();
    }
    public void greyRBased() {
        if (leftPanel.getModel() == null || leftPanel.getModel().getImage() == null) {
            JOptionPane.showMessageDialog(mainFrame, "Brak załadowanego obrazu!", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }
        var image = leftPanel.getModel().getCopyImage();
        var model = new ImageModel(image);
        int width = image.getWidth();
        int height = image.getHeight();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                Color color = new Color(rgb);
                int gray = (color.getRed());
                Color grayColor = new Color(gray, gray, gray);
                image.setRGB(x, y, grayColor.getRGB());
            }
        }
        rightPanel.setModel(model);
        rightPanel.repaint();
    }
    public void greyGBased() {
        if (leftPanel.getModel() == null || leftPanel.getModel().getImage() == null) {
            JOptionPane.showMessageDialog(mainFrame, "Brak załadowanego obrazu!", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }
        var image = leftPanel.getModel().getCopyImage();
        var model = new ImageModel(image);
        int width = image.getWidth();
        int height = image.getHeight();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                Color color = new Color(rgb);
                int gray = (color.getGreen());
                Color grayColor = new Color(gray, gray, gray);
                image.setRGB(x, y, grayColor.getRGB());
            }
        }
        rightPanel.setModel(model);
        rightPanel.repaint();
    }
    public void greyBBased() {
        if (leftPanel.getModel() == null || leftPanel.getModel().getImage() == null) {
            JOptionPane.showMessageDialog(mainFrame, "Brak załadowanego obrazu!", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }
        var image = leftPanel.getModel().getCopyImage();
        var model = new ImageModel(image);
        int width = image.getWidth();
        int height = image.getHeight();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                Color color = new Color(rgb);
                int gray = (color.getBlue());
                Color grayColor = new Color(gray, gray, gray);
                image.setRGB(x, y, grayColor.getRGB());
            }
        }
        rightPanel.setModel(model);
        rightPanel.repaint();
    }

    public void greyYUV() {
        if (leftPanel.getModel() == null || leftPanel.getModel().getImage() == null) {
            JOptionPane.showMessageDialog(mainFrame, "Brak załadowanego obrazu!", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }
        var image = leftPanel.getModel().getCopyImage();
        var model = new ImageModel(image);
        int width = image.getWidth();
        int height = image.getHeight();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                Color color = new Color(rgb);
                int gray = (int) Math.round((color.getRed()*0.299) + (color.getGreen()*0.587) + (color.getBlue()*0.114));
                Color grayColor = new Color(gray, gray, gray);
                image.setRGB(x, y, grayColor.getRGB());
            }
        }
        rightPanel.setModel(model);
        rightPanel.repaint();
    }
    public void lightCont(LightModel lightComp) {
        if (leftPanel.getModel() == null || leftPanel.getModel().getImage() == null) {
            JOptionPane.showMessageDialog(mainFrame, "Brak załadowanego obrazu!", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }
        var image = leftPanel.getModel().getCopyImage();
        int light = lightComp.getLight();
        double contrast = lightComp.getContrast();
        var model = new ImageModel(image);
        int width = image.getWidth();
        int height = image.getHeight();
        //pętla zmiany światła
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                Color color = new Color(rgb);
                int red = Math.min(255, Math.max(0, color.getRed() + light));
                int green = Math.min(255, Math.max(0, color.getGreen() + light));
                int blue = Math.min(255, Math.max(0, color.getBlue() + light));
                Color lightenedColor = new Color(red,green,blue);
                image.setRGB(x, y, lightenedColor.getRGB());
            }
        }
        //pętla zmiany kontrastu
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                Color color2 = new Color(rgb);
                int red = (int)(color2.getRed()*contrast);
                if(red>255){red = 255;}
                if(red<0){red = 0;}
                int green = (int)(color2.getGreen()*contrast);
                if(green>255){green = 255;}
                if(green<0){green = 0;}
                int blue =(int)(color2.getBlue()*contrast);
                if(blue>255){blue = 255;}
                if(blue<0){blue = 0;}
                Color contrastColor = new Color(red, green, blue);
                image.setRGB(x, y, contrastColor.getRGB());
            }
        }
        rightPanel.setModel(model);
        rightPanel.repaint();
    }
    public Color lMin(BufferedImage image) {
        int minRed = 255;
        int minGreen = 255;
        int minBlue = 255;
        int height = image.getHeight();
        int width = image.getWidth();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                Color color = new Color(rgb);
                if (minRed > color.getRed()) {
                    minRed = color.getRed();
                }

                if (minGreen > color.getGreen()) {
                    minGreen = color.getGreen();
                }

                if (minBlue > color.getBlue()) {
                    minBlue = color.getBlue();
                }
            }
        }
        return new Color(minRed,minGreen,minBlue);
    }
    public Color lMax(BufferedImage image) {
        int maxRed = 0;
        int maxGreen = 0;
        int maxBlue = 0;
        int height = image.getHeight();
        int width = image.getWidth();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                Color color = new Color(rgb);
                if (maxRed < color.getRed()) {
                    maxRed = color.getRed();
                }
                if (maxGreen < color.getGreen()) {
                    maxGreen = color.getGreen();
                }
                if (maxBlue < color.getBlue()) {
                    maxBlue = color.getBlue();
                }
            }
        }
        return new Color(maxRed,maxGreen,maxBlue);
    }
    public void negation(){
        if (leftPanel.getModel() == null || leftPanel.getModel().getImage() == null) {
            JOptionPane.showMessageDialog(mainFrame, "Brak załadowanego obrazu!", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }
        var image = leftPanel.getModel().getCopyImage();
        var model = new ImageModel(image);
        int width = image.getWidth();
        int height = image.getHeight();
        Color MaxL = lMax(image);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                Color color = new Color(rgb);
                Color negatedColor = new Color((MaxL.getRed()-color.getRed()),(MaxL.getGreen()-color.getGreen()),(MaxL.getBlue()-color.getBlue()));
                image.setRGB(x, y, negatedColor.getRGB());
            }
        }
        rightPanel.setModel(model);
        rightPanel.repaint();
    }

    public void brightness(){
        if (leftPanel.getModel() == null || leftPanel.getModel().getImage() == null) {
            JOptionPane.showMessageDialog(mainFrame, "Brak załadowanego obrazu!", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }
        var image = leftPanel.getModel().getCopyImage();
        var model = new ImageModel(image);
        int width = image.getWidth();
        int height = image.getHeight();
        Color MaxL = lMax(image);
        Color MinL = lMin(image);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                Color color = new Color(rgb);
                Color negatedColor = new Color((255*(color.getRed()-MinL.getRed())/(MaxL.getRed()-MinL.getRed())), (255*(color.getGreen()-MinL.getGreen())/(MaxL.getGreen()-MinL.getGreen())), (255*(color.getBlue()-MinL.getBlue())/(MaxL.getBlue()-MinL.getBlue())));
                image.setRGB(x, y, negatedColor.getRGB());
            }
        }
        rightPanel.setModel(model);
        rightPanel.repaint();
    }

    public void applyMask(double[][] mask, double norm) {
        var image = leftPanel.getModel().getCopyImage();
        var newImage = leftPanel.getModel().getCopyImage();
        var model = new ImageModel(newImage);
        int width = newImage.getWidth();
        int height = newImage.getHeight();

        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                int[] r = new int[9];
                int[] g = new int[9];
                int[] b = new int[9];
                int k = 0;
                for (int j = -1; j <= 1; j++) {
                    for (int i = -1; i <= 1; i++) {
                        int xi = x + i;
                        int yj = y + j;
                        xi = Math.max(0, Math.min(xi, width - 1));
                        yj = Math.max(0, Math.min(yj, height - 1));
                        Color color = new Color(image.getRGB(xi, yj));
                        r[k] = color.getRed();
                        g[k] = color.getGreen();
                        b[k] = color.getBlue();
                        k++;
                    }
                }
                int newR = calculateMaskedValue(r, mask, norm);
                int newG = calculateMaskedValue(g, mask, norm);
                int newB = calculateMaskedValue(b, mask, norm);
                newImage.setRGB(x, y, new Color(newR, newG, newB).getRGB());
            }
        }
        rightPanel.setModel(model);
        rightPanel.repaint();
    }



    private int calculateMaskedValue(int[] values, double[][] mask, double norm) {
        double sum = 0;
        int k = 0;
        for (int j = -1; j <= 1; j++) {
            for (int i = -1; i <= 1; i++) {
                sum += values[k] * mask[j + 1][i + 1];
                k++;
            }
        }
        int result = (int) Math.round(sum / norm);
        result = Math.max(0, Math.min(255, result));
        return result;
    }

    public static Map.Entry<double[][], Double> loadMaskFromFile(String path) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(path));

        double[][] m = new double[3][3];
        double n = 1.0;
        int r = 0;

        for (String line : lines) {
            line = line.strip();
            if (line.isEmpty() || line.startsWith("#")) continue;

            if (line.startsWith("norm")) {
                n = Double.parseDouble(line.split("\\s+")[1]);
                double[][] copiedMask = new double[3][3];
                for (int i = 0; i < 3; i++) {
                    System.arraycopy(m[i], 0, copiedMask[i], 0, 3);
                }
                return new AbstractMap.SimpleEntry<>(copiedMask, n);
            } else {
                String[] parts = line.split("\\s+");
                for (int i = 0; i < 3; i++) {
                    m[r][i] = Double.parseDouble(parts[i]);
                }
                r++;
            }
        }

        throw new IOException("Nie ma poprawnej maski.");
    }

    public void discreteGradient() {
        greyAverage();
        BufferedImage src = rightPanel.getModel().getCopyImage();
        int width  = src.getWidth();
        int height = src.getHeight();
        BufferedImage dst = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        ImageModel model = new ImageModel(dst);
        for (int y = 0; y < height - 1; y++) {
            for (int x = 0; x < width - 1; x++) {
                int current = (src.getRGB(x,     y    ) >> 16) & 0xFF;
                int right   = (src.getRGB(x,     y + 1) >> 16) & 0xFF;
                int bottom  = (src.getRGB(x + 1, y    ) >> 16) & 0xFF;
                int deltaY   = Math.abs(current - right);
                int deltaX   = Math.abs(current - bottom);
                int gradient = deltaX + deltaY;
                gradient = Math.min(255, Math.max(0, gradient));
                int rgb = (gradient << 16) | (gradient << 8) | gradient;
                dst.setRGB(x, y, rgb);
            }
        }

        // 5) Podmieniamy model w prawym panelu i odświeżamy
        rightPanel.setModel(model);
        rightPanel.repaint();
    }

    public void robertsGradient(int threshold) {
        greyAverage();
        BufferedImage src = rightPanel.getModel().getCopyImage();
        int width = src.getWidth();
        int height = src.getHeight();
        BufferedImage dst = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        ImageModel model = new ImageModel(dst);


        for (int y = 0; y < height - 1; y++) {
            for (int x = 0; x < width - 1; x++) {
                int l00 = (src.getRGB(x,     y    ) >> 16) & 0xFF;
                int l10 = (src.getRGB(x + 1, y    ) >> 16) & 0xFF;
                int l01 = (src.getRGB(x,     y + 1) >> 16) & 0xFF;
                int l11 = (src.getRGB(x + 1, y + 1) >> 16) & 0xFF;

                int grad = Math.abs(l00 - l11) + Math.abs(l10 - l01);
                int value = (grad >= threshold) ? 255 : 0;

                int rgb = (value << 16) | (value << 8) | value;
                dst.setRGB(x, y, rgb);
            }
        }
        for (int x = 0; x < width; x++) {
            dst.setRGB(x, height - 1, dst.getRGB(x, height - 2));
        }
        for (int y = 0; y < height; y++) {
            dst.setRGB(width - 1, y, dst.getRGB(width - 2, y));
        }

        rightPanel.setModel(model);
        rightPanel.repaint();
    }

    public BufferedImage negationHelper(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage negated = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Color maxL = lMax(image);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = new Color(image.getRGB(x, y));
                int red = clamp(maxL.getRed() - color.getRed());
                int green = clamp(maxL.getGreen() - color.getGreen());
                int blue = clamp(maxL.getBlue() - color.getBlue());
                Color negatedColor = new Color(red, green, blue);
                negated.setRGB(x, y, negatedColor.getRGB());
            }
        }
        return negated;
    }

    public void negateRightPanel() {
        BufferedImage image = rightPanel.getModel().getCopyImage();
        BufferedImage negated = negationHelper(image);
        rightPanel.setModel(new ImageModel(negated));
        rightPanel.repaint();
    }

    private int clamp(int value) {
        return Math.max(0, Math.min(255, value));
    }

    public void edgesOnOriginal(int threshold) {

        BufferedImage original = leftPanel.getModel().getCopyImage();
        robertsGradient(threshold);
        BufferedImage edges = rightPanel.getModel().getCopyImage();
        BufferedImage negEdges = negationHelper(edges);
        int width  = original.getWidth();
        int height = original.getHeight();
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int edgePixel = negEdges.getRGB(x, y) & 0xFFFFFF;
                if (edgePixel != 0xFFFFFF) {
                    result.setRGB(x, y, edgePixel);
                } else {
                    result.setRGB(x, y, original.getRGB(x, y));
                }
            }
        }
        rightPanel.setModel(new ImageModel(result));
        rightPanel.repaint();
    }
}


