package krzywe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class PanelKrzywe extends JFrame implements ActionListener {

    private final static String TITLE = "Okno 2 -Krzywe";

    private final FrameMenu menu = new FrameMenu();
    private final PointsPanel pointsPanel = new PointsPanel();
    private final MatrixPanel matrixPanel = new MatrixPanel();
    private final MainPanel mainPanel = new MainPanel(matrixPanel, pointsPanel);

    private final JPanel container = new JPanel(new BorderLayout());
    private final JPanel rightPanel = new JPanel(new GridBagLayout());

    public PanelKrzywe() {
        super(TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        setJMenuBar(menu);

        GridBagConstraints pointsPanelConstraints = new GridBagConstraints();
        GridBagConstraints matrixPanelConstraints = new GridBagConstraints();
        pointsPanelConstraints.fill = GridBagConstraints.BOTH;
        matrixPanelConstraints.fill = GridBagConstraints.BOTH;

        pointsPanelConstraints.gridx = 0;
        pointsPanelConstraints.gridy = 1;
        pointsPanelConstraints.weighty = 1.0;

        rightPanel.add(pointsPanel, pointsPanelConstraints);

        matrixPanelConstraints.gridx = 0;
        matrixPanelConstraints.gridy = 0;
        matrixPanelConstraints.weighty = 0.0;
        rightPanel.add(matrixPanel, matrixPanelConstraints);

        container.add(mainPanel, BorderLayout.WEST);
        container.add(rightPanel, BorderLayout.EAST);
        this.add(container);

        addMouseListener(mouseAdapter);
        setUpEventListener();
        matchTheContent();
        setVisible(true);
    }

    private void matchTheContent() {
        pack();
        setLocationRelativeTo(null);
    }

    MouseAdapter mouseAdapter = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            Point point = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), mainPanel);
            if (mainPanel.contains(point)) {
                pointsPanel.addPoint(point);
                repaint();
            }
        }
    };

    private void setUpEventListener() {
        menu.drawLines.addActionListener(this);
        menu.drawCurve.addActionListener(this);
        menu.rotate.addActionListener(this);
        menu.scale.addActionListener(this);
        menu.move.addActionListener(this);
        menu.clear.addActionListener(this);
        menu.last.addActionListener(this);
        menu.refresh.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String label = e.getActionCommand();
        switch (label) {
            case FrameMenu.DRAW_LINES_TEXT -> allowLines();
            case FrameMenu.DRAW_CURVED_TEXT -> allowCurve();
            case FrameMenu.CLEAR_TEXT -> clear();
            case FrameMenu.SCALE_TEXT -> mainPanel.scale();
            case FrameMenu.MOVE_TEXT -> mainPanel.move();
            case FrameMenu.ROTATE_TEXT -> mainPanel.rotate();
            case FrameMenu.CLEAR_LAST -> pointsPanel.removePoint();
            case FrameMenu.REFRESH -> pointsPanel.refresh();
        }
    }

    private void allowLines() {
        if (pointsPanel.getPoints().size() > 1) {
            mainPanel.linesAllowed = !mainPanel.linesAllowed;
            repaint();
        }
        else {
            mainPanel.drawError("line");
        }
    }

    private void allowCurve() {
        if (pointsPanel.getTransformPoints().size() > 1) {
            if (mainPanel.curveAllowed) mainPanel.curveAllowed = false;
            else {
                if (mainPanel.setDelta()) {
                    mainPanel.curveAllowed = true;
                }
            }
            repaint();
        }
        else {
            mainPanel.drawError("curve");
        }
    }

    private void clear() {
        matrixPanel.clearPanel();
        pointsPanel.clearPanel();
        mainPanel.clearPanel();
    }
}
