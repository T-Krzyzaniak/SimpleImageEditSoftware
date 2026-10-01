package krzywe;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class MainPanel extends JPanel {

    private final static int DEFAULT_WIDTH = 500;
    private final static int DEFAULT_HEIGHT = 550;
    private final static Color DEFAULT_BACKGROUND = Color.white;
    private final static Color DEFAULT_BORDER = Color.MAGENTA;

    private final PointsPanel pointsPanel;
    private final MatrixPanel matrixPanel;

    public boolean linesAllowed = false;
    public boolean curveAllowed = false;
    private int delta;

    public MainPanel(MatrixPanel matrixPanel,PointsPanel pointsPanel) {
        super();
        setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        this.matrixPanel = matrixPanel;
        this.pointsPanel = pointsPanel;
        clearPanel();
    }
    public void refresh(){
        repaint();
    }

    public void clearPanel() {
        setBackground(DEFAULT_BACKGROUND);
        setBorder(BorderFactory.createLineBorder(DEFAULT_BORDER));

        linesAllowed = false;
        curveAllowed = false;
        repaint();
    }

    private void drawPoints(Graphics2D g2d) {
        ArrayList<Point> points = pointsPanel.getTransformPoints();
        for (int i = 0; i < points.size(); i++) {
            int x = points.get(i).x;
            int y = points.get(i).y;
            g2d.setColor(Color.BLACK);
            g2d.fillOval(x, y, 4, 4);
        }
    }

    private void drawLines(Graphics2D g2d) {
        ArrayList<Point> points = pointsPanel.getTransformPoints();
        for (int i = 0; i < points.size() - 1; i++) {
            int x1 = points.get(i).x;
            int y1 = points.get(i).y;
            int x2 = points.get(i + 1).x;
            int y2 = points.get(i + 1).y;
            Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
            g2d.setStroke(dashed);
            g2d.setColor(Color.GREEN);
            g2d.drawLine(x1 + 2,y1 + 2,x2 + 2,y2 + 2);
        }
    }
    public void drawCurve(Graphics2D g){

        int n = pointsPanel.getPoints().size();
        int m;
        double t = 1 / delta;

        double [][] newP = new double[n][2];
        double [][] tempP = new double[n][2];
        int a = 1;
        if (delta == 1) {
            a = 0;
        }

        Point[] P = new Point[delta + a];

        for(int j = 0; j < delta + a; j++) {
            for (int i = 0; i < n; i++) {
                newP[i][0] = pointsPanel.getPoint(i).x;
                newP[i][1] = pointsPanel.getPoint(i).y;
            }
            m=n;
            while (m > 0) {
                for (int i = 0; i < m - 1; i++) {
                    tempP[i][0] = newP[i][0] + t * (newP[i + 1][0] - newP[i][0]);
                    tempP[i][1] = newP[i][1] + t * (newP[i + 1][1] - newP[i][1]);
                }
                m--;
                for (int i = 0; i < m; i++) {
                    newP[i] = tempP[i];
                }
            }
            P[j] = new Point((int) Math.round(newP[0][0]),(int) Math.round(newP[0][1]));
            t += 1.0 / delta;
        }
        Point p = pointsPanel.getPoint(0);
        //g.drawLine(p.x + 2,p.y + 2,P[0].x + 2,P[0].y + 2);
        Line2D.Double line = new Line2D.Double(p.x + 2,p.y + 2,P[0].x + 2,P[0].y + 2);
        g.setColor(Color.RED);
        g.draw(line);

        for(int i = 1;i < P.length; i++){
            //g.drawLine(P[i - 1].x + 2,P[i - 1].y + 2,P[i].x + 2,P[i].y + 2);
            line = new Line2D.Double(P[i - 1].x + 2,P[i - 1].y + 2,P[i].x + 2,P[i].y + 2);
            g.setColor(Color.RED);
            g.draw(line);
        }

    }



    public boolean setDelta() {
        try {
            try {
                int delta = Integer.parseInt(JOptionPane.showInputDialog(this, "podaj wartość"));
                if (delta <= 0) {
                    drawError("negative delta");
                } else {
                    this.delta = delta;
                    return true;
                }
            }
            catch (NumberFormatException e) {
                drawError("delta format");
            }
        }
        catch (NullPointerException e) {
            drawError("delta is null");
        }
        return false;
    }

    public void drawError(String c) {
        switch (c) {
            case "line" ->
                JOptionPane.showMessageDialog(
                        this, "Zbyt mało punktów"
                                + pointsPanel.getPoints().size(), "Błąd", JOptionPane.ERROR_MESSAGE);
            case "curve" ->
                JOptionPane.showMessageDialog(
                        this, "Zbyt mało punktów"
                                + pointsPanel.getPoints().size(), "Błąd", JOptionPane.ERROR_MESSAGE);
            case "negative delta" ->
                    JOptionPane.showMessageDialog(
                            this, "Minusowa delta", "Błąd", JOptionPane.ERROR_MESSAGE);
            case "delta format" ->
                    JOptionPane.showMessageDialog(
                            this, "Liczba musi być naturalna", "Błąd", JOptionPane.ERROR_MESSAGE);
            case "delta is null" ->
                    JOptionPane.showMessageDialog(
                            this, "Brak delty", "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void transformPoints() {
        int n = pointsPanel.getPoints().size();
        double[][] matrix = matrixPanel.getMatrix();
        ArrayList<Point> transformPoints = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Point point = pointsPanel.getPoint(i);
            double x = point.x * matrix[0][0] + point.y * matrix[1][0] + matrix[2][0];
            double y = point.x * matrix[0][1] + point.y * matrix[1][1] + matrix[2][1];
            double divider = point.x * matrix[0][2] + point.y * matrix[1][2] + matrix[2][2];

            x /= divider;
            y /= divider;

            transformPoints.add(new Point((int)Math.round(x),(int)Math.round(y)));
        }
        pointsPanel.setTransformPoints(transformPoints);
    }

    public void rotate() {
        matrixPanel.rotate();
        transformPoints();
        repaint();
    }

    public void scale() {
        matrixPanel.scale();
        transformPoints();
        repaint();
    }

    public void move() {
        matrixPanel.move();
        transformPoints();
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        drawPoints(g2d);
        if (linesAllowed) {
            drawLines(g2d);
        }
        if (curveAllowed) {
            drawCurve(g2d);
        }
    }
}
