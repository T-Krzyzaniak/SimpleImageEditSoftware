package krzywe;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PointsPanel extends JPanel {


    private final static int DEFAULT_WIDTH = 250;
    private final static int DEFAULT_HEIGHT = 475;
    private final static Color DEFAULT_BACKGROUND = Color.white;
    private final static Color DEFAULT_BORDER = Color.MAGENTA;
    private static int ammount = -1;

    private final ArrayList<Point> points = new ArrayList<>();
    private ArrayList<Point> transformPoints = new ArrayList<>();

    public PointsPanel() {
        super();
        setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        clearPanel();
    }

    public void addPoint(Point point) {
        points.add(point);
        transformPoints.add(point);
        ammount++;
        repaint();
    }

    public void removePoint() {
        if (ammount != -1) {
            points.remove(ammount);
            transformPoints.remove(ammount);
            ammount--;
            repaint();
        }
        repaint();

    }

    public void clearPanel() {
        points.clear();
        transformPoints.clear();
        setBackground(DEFAULT_BACKGROUND);
        setBorder(BorderFactory.createLineBorder(DEFAULT_BORDER));
        repaint();
    }
    public void refresh(){
        repaint();

    }

    public Point getPoint(int index) {
        return points.get(index);
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public ArrayList<Point> getTransformPoints() {
        return transformPoints;
    }

    public void setPoint(int index, Point point) {
        points.set(index, point);
    }

    public void setTransformPoints(ArrayList<Point> transformPoints) {
        this.transformPoints = transformPoints;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        for (int i = 0; i < points.size(); i++) {
            int x = points.get(i).x;
            int y = points.get(i).y;
            String s = "x = " + x + " y = " + y;
            g2d.drawString(s, 10, (15 * i) + 15);
        }
        for (int i = 0; i < transformPoints.size(); i++) {
            int x = transformPoints.get(i).x;
            int y = transformPoints.get(i).y;
            String s = "x = " + x + " y = " + y;
            g2d.drawString(s, 100, (15 * i) + 15);
        }
    }
}
