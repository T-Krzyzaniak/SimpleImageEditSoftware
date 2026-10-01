package krzywe;

import javax.swing.*;

public class FrameMenu extends JMenuBar {

    public final static String DRAW_LINES_TEXT = "Linie";
    public final static String DRAW_CURVED_TEXT = "Krzywa";
    public final static String ROTATE_TEXT = "Obroc";
    public final static String SCALE_TEXT = "Up/Down";
    public final static String MOVE_TEXT = "Przesun";
    public final static String CLEAR_TEXT = "Wyczysc";
    public final static String CLEAR_LAST = "Cofnij";
    public final static String REFRESH = "Odswiez";

    JButton drawLines = new JButton(DRAW_LINES_TEXT);
    JButton drawCurve = new JButton(DRAW_CURVED_TEXT);
    JButton rotate = new JButton(ROTATE_TEXT);
    JButton scale = new JButton(SCALE_TEXT);
    JButton move = new JButton(MOVE_TEXT);
    JButton clear = new JButton(CLEAR_TEXT);
    JButton last = new JButton(CLEAR_LAST);
    JButton refresh = new JButton(REFRESH);

    public FrameMenu() {
        add(drawLines);
        add(drawCurve);
        add(rotate);
        add(scale);
        add(move);
        add(clear);
        add(last);
        add(refresh);
    }
}
