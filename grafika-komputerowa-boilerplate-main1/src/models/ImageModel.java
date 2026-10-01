package models;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Model reprezentujący obraz, umożliwiający jego modyfikację oraz kopiowanie.
 *
 * <p>
 *     Przechowuje obraz w postaci obiektu {@link BufferedImage} i udostępnia metody do jego modyfikacji.
 *     Tylko ta klasa powinna udostępniać metody do modyfikacji obrazu.
 * </p>
 *
 */
public class ImageModel {

    private BufferedImage image;

    public ImageModel(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    /**
     * Tworzy i zwraca kopię obrazu. Nowy obraz jest kopiowany piksel po pikselu
     *
     * @return Kopia obrazu jako {@link BufferedImage}.
     */
    public BufferedImage getCopyImage() {
        BufferedImage copy = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        Graphics2D g = copy.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();

        return copy;
    }

    /**
     * Rysuje koło na obrazie na podstawie modelu {@link CircleModel}. Koło jest rysowane w miejscu określonym przez współrzędne środka i promień.
     *
     * <p>Iteracyjna implementacja metody:</p>
     * <pre>
     * {@code
     *      int imgWidth = image.getWidth();
     *      int imgHeight = image.getHeight();
     *      int centerX = circle.getCenterX();
     *      int centerY = circle.getCenterY();
     *      int radius = circle.getRadius();
     *      int radiusSquared = radius * radius;
     *      int colorRGB = circle.getColor().getRGB();
     *
     *      for (int y = Math.max(centerY - radius, 0); y < Math.min(centerY + radius, imgHeight); y++) {
     *          for (int x = Math.max(centerX - radius, 0); x < Math.min(centerX + radius, imgWidth); x++) {
     *              int dx = x - centerX;
     *              int dy = y - centerY;
     *              if (dx * dx + dy * dy <= radiusSquared) { // Sprawdzamy, czy punkt leży w kole
     *                  image.setRGB(x, y, colorRGB);
     *              }
     *          }
     *      }
     * }
     * </pre>
     *
     * @param circle Obiekt klasy {@link CircleModel}, określający parametry koła.
     */
    public void drawCircle(CircleModel circle) {
        if (image != null) {
            Graphics2D g2d = image.createGraphics();
            g2d.drawImage(image, 0, 0, null);
            g2d.setColor(circle.getColor());
            g2d.fillOval(circle.getCenterX() - circle.getRadius(), circle.getCenterY() - circle.getRadius(), circle.getRadius() * 2, circle.getRadius() * 2);
            g2d.dispose();
        }
    }

    /**
     * Rysuje prostokąt na obrazie na podstawie modelu {@link RectangleModel}.
     *
     * @param rectangle Obiekt klasy {@link RectangleModel}, określający parametry prostokąta.
     */
    public void drawRectangle(RectangleModel rectangle) {
        if (image != null) {
            Graphics2D g2d = image.createGraphics();
            g2d.drawImage(image, 0, 0, null);
            g2d.setColor(rectangle.getColor());
            g2d.fillRect(rectangle.getStartX(), rectangle.getStartY(), rectangle.getWidth(), rectangle.getHeight());
            g2d.dispose();

            // TODO: Implementacja metody rysowania prostokąta.
        }
    }
    public void drawLine(LineModel line){
        if(image!=null) {
            Graphics2D g2d = image.createGraphics();
                g2d.drawImage(image, 0, 0, null);
                g2d.setColor(line.getColor());
                g2d.fillRect(line.getStartX(), line.getStartY(), line.getWidth(), line.getHeight());
                g2d.dispose();

        }
    }
}

