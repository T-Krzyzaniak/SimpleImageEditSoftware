package krzywe;

import javax.swing.*;
import java.awt.*;

public class MatrixPanel extends JPanel {

    private final static int DEFAULT_WIDTH = 150;
    private final static int DEFAULT_HEIGHT = 65;
    private final static Color DEFAULT_BACKGROUND = Color.white;
    private final static Color DEFAULT_BORDER = Color.MAGENTA;

    private final static String NULL_ANGLE_ERROR = "Muszisz podać kąt";
    private final static String NULL_SX_ERROR = "Muszisz podać parametr sx";
    private final static String NULL_SY_ERROR = "Muszisz podać parametr sy";
    private final static String NULL_TX_ERROR = "Muszisz podać parametr tx";
    private final static String NULL_TY_ERROR = "Muszisz podać parametr ty";
    private final static String FORMAT_ANGLE_ERROR = "Kąt musi być liczbą";
    private final static String FORMAT_SX_ERROR = "Parametr sx musi być liczbą";
    private final static String FORMAT_SY_ERROR = "Parametr sy musi być liczbą";
    private final static String FORMAT_TX_ERROR = "Parametr tx musi być liczbą";
    private final static String FORMAT_TY_ERROR = "Parametr ty musi być liczbą";
    private final static String ANGLE_PARAM_INPUT_MESSAGE = "Podaj kąt:";
    private final static String SX_PARAM_INPUT_MESSAGE = "Podaj parametr sx:";
    private final static String SY_PARAM_INPUT_MESSAGE = "Podaj parametr sy:";
    private final static String TX_PARAM_INPUT_MESSAGE = "Podaj parametr tx:";
    private final static String TY_PARAM_INPUT_MESSAGE = "Podaj parametr ty:";

    private double[][] matrix = new double[3][3];

    public MatrixPanel() {
        super();
        setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        setBackground(DEFAULT_BACKGROUND);
        setBorder(BorderFactory.createLineBorder(DEFAULT_BORDER));

        setDefaultMatrix();
    }

    public void clearPanel() {
        setDefaultMatrix();
    }

    public void setDefaultMatrix() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (i == j) {
                    matrix[i][j] = 1;
                }
                else {
                    matrix[i][j] = 0;
                }
            }
        }
        repaint();
    }

    public void rotate() {
        try {
            try {
                double angle = Double.parseDouble(JOptionPane.showInputDialog(this, ANGLE_PARAM_INPUT_MESSAGE));
                angle = Math.toRadians(angle);

                double[][] elementaryMatrix = new double[3][3];
                elementaryMatrix[0][0] = Math.cos(angle);
                elementaryMatrix[0][1] = Math.sin(angle);
                elementaryMatrix[1][0] = -Math.sin(angle);
                elementaryMatrix[1][1] = Math.cos(angle);
                elementaryMatrix[2][2] = 1;

                transformMatrix(elementaryMatrix);
            }
            catch (NullPointerException e) {
                JOptionPane.showMessageDialog(
                        this, NULL_ANGLE_ERROR, "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this, FORMAT_ANGLE_ERROR, "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void scale() {
        double[][] elementaryMatrix = new double[3][3];
        try {
            try {
                double sx = Double.parseDouble(JOptionPane.showInputDialog(this, SX_PARAM_INPUT_MESSAGE));
                elementaryMatrix[0][0] = sx;
            }
            catch (NullPointerException e) {
                JOptionPane.showMessageDialog(
                        this, NULL_SX_ERROR, "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this, FORMAT_SX_ERROR, "Błąd", JOptionPane.ERROR_MESSAGE);
        }
        try {
            try {
                double sy = Double.parseDouble(JOptionPane.showInputDialog(this, SY_PARAM_INPUT_MESSAGE));
                elementaryMatrix[1][1] = sy;
            }
            catch (NullPointerException e) {
                JOptionPane.showMessageDialog(
                        this, NULL_SY_ERROR, "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this, FORMAT_SY_ERROR, "Błąd", JOptionPane.ERROR_MESSAGE);
        }

        elementaryMatrix[2][2] = 1;

        transformMatrix(elementaryMatrix);
    }

    public void move(){
        double[][] elementaryMatrix = new double[3][3];
        elementaryMatrix[0][0] = 1;
        elementaryMatrix[1][1] = 1;
        try {
            try {
                double tx = Double.parseDouble(JOptionPane.showInputDialog(this, TX_PARAM_INPUT_MESSAGE));
                elementaryMatrix[2][0] = tx;
            }
            catch (NullPointerException e) {
                JOptionPane.showMessageDialog(
                        this, NULL_TX_ERROR, "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this, FORMAT_TX_ERROR, "Błąd", JOptionPane.ERROR_MESSAGE);
        }
        try {
            try {
                double ty = Double.parseDouble(JOptionPane.showInputDialog(this, TY_PARAM_INPUT_MESSAGE));
                elementaryMatrix[2][1] = ty;
            }
            catch (NullPointerException e) {
                JOptionPane.showMessageDialog(
                        this, NULL_TY_ERROR, "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this, FORMAT_TY_ERROR, "Błąd", JOptionPane.ERROR_MESSAGE);
        }

        elementaryMatrix[2][2] = 1;

        transformMatrix(elementaryMatrix);
    }

    public void transformMatrix(double[][] elementaryMatrix) {
        double[][] tempMatrix = new double[3][3];
        System.arraycopy(matrix, 0, tempMatrix, 0, matrix.length);
        matrix = new double[3][3];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                for (int k = 0; k < matrix.length; k++) {
                    matrix[i][j] += tempMatrix[i][k] * elementaryMatrix[k][j];
                }
            }
        }
        repaint();
    }

    public double[][] getMatrix() {
        return matrix;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < matrix.length; i++) {
            sb.append("[ ");
            for (int j = 0; j < matrix[i].length; j++) {
                sb.append(String.format("%.2f", matrix[i][j]));
                if (j != matrix[i].length - 1) {
                    sb.append(" | ");
                }
            }
            sb.append(" ]");
            sb.append(System.lineSeparator());
        }
        int x = 10;
        int y = 5;
        for (String line : sb.toString().split("\n")) {
            g2d.drawString(line, x, y += g2d.getFontMetrics().getHeight());
        }
    }
}
