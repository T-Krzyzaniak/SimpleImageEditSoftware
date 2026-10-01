package views;

import javax.swing.*;

/**
 * Pasek menu aplikacji, zawierający opcje zarządzania plikami, edycji oraz operacji na panelach.
 * Klasa rozszerza {@link JMenuBar} i definiuje strukturę menu dla aplikacji.
 */
public class MenuBar extends JMenuBar {

    private final JMenu fileMenu;
    private final JMenu leftPanelMenu;
    private final JMenu rightPanelMenu;
    private final JMenu editPanelMenu;
    private final JMenu curvesPanelMenu;

    private final JMenuItem openFileMenuItem;
    private final JMenuItem saveFileMenuItem;
    private final JMenuItem exitMenuItem;

    private final JMenuItem clearLeftPanelMenuItem;
    private final JMenuItem copyLeftPanelMenuItem;

    private final JMenuItem clearRightPanelMenuItem;
    private final JMenuItem copyRightPanelMenuItem;
    // TODO: Dodać opcję menu umożliwiającą skopiowanie obrazu z prawego panelu do lewego panelu.

    private final JMenuItem drawCircleMenuItem;
    // TODO: Dodać opcję menu umożliwiającą narysowanie prostokąta na obrazie.
    private final JMenuItem drawRectangleMenuItem;
    private final JMenuItem drawLineMenuItem;
    private final JMenuItem greyMenuItem;
    private final JMenuItem lightMenuItem;
    private final JMenuItem negationMenuItem;
    private final JMenuItem brightnessMenuItem;
    private final JMenuItem filterMenuItem;
    private final JMenuItem gradientMenuItem;
    private final JMenuItem curvesMenuItem;

    public MenuBar() {
        // Tworzenie głównych menu
        fileMenu = new JMenu("Plik");
        leftPanelMenu = new JMenu("Lewy panel");
        rightPanelMenu = new JMenu("Prawy panel");
        editPanelMenu = new JMenu("Edycja");
        curvesPanelMenu = new JMenu ("krzywe");


        // Menu plik
        openFileMenuItem = new JMenuItem("Otwórz");
        saveFileMenuItem = new JMenuItem("Zapisz");
        exitMenuItem = new JMenuItem("Zakończ");
        curvesMenuItem = new JMenuItem("Braizera");

        // Menu Panel lewy
        clearLeftPanelMenuItem = new JMenuItem("Wyczyść");
        copyLeftPanelMenuItem = new JMenuItem("Kopiuj obraz");

        // Menu Panel prawy
        clearRightPanelMenuItem = new JMenuItem("Wyczyść");
        copyRightPanelMenuItem = new JMenuItem("Kopiuj obraz");
        // TODO: Dodać nowy element menu kopiowania prawego panelu.

        // Menu Edycja
        drawCircleMenuItem = new JMenuItem("Narysuj koło");
        drawRectangleMenuItem = new JMenuItem("Narysuj Prostokat");
        drawLineMenuItem = new JMenuItem("Narysuj Linie");
        greyMenuItem = new JMenuItem("Szary obraz");
        lightMenuItem = new JMenuItem("Kontrast/Jasnosc");
        negationMenuItem = new JMenuItem("Negacja");
        brightnessMenuItem = new JMenuItem("Zmiana Jasnosci");
        filterMenuItem = new JMenuItem("Wczytaj maske");
        gradientMenuItem = new JMenuItem("Gradienty");
        // TODO: Dodać nowy element menu rysowania prostokąta.

        // Dodanie elementów do menu Plik
        fileMenu.add(openFileMenuItem);
        fileMenu.add(saveFileMenuItem);
        fileMenu.add(new JSeparator());
        fileMenu.add(exitMenuItem);

        // Dodanie elementów do menu Panel lewy
        leftPanelMenu.add(clearLeftPanelMenuItem);
        leftPanelMenu.add(copyLeftPanelMenuItem);

        // Dodanie elementów do menu Panel prawy
        rightPanelMenu.add(clearRightPanelMenuItem);
        rightPanelMenu.add(copyRightPanelMenuItem);
        // TODO: Dodać nowy element menu kopiowania prawego panelu.

        // Dodanie elementów do menu Edycja
        editPanelMenu.add(drawCircleMenuItem);
        editPanelMenu.add(drawRectangleMenuItem);
        editPanelMenu.add(drawLineMenuItem);
        editPanelMenu.add(greyMenuItem);
        editPanelMenu.add(lightMenuItem);
        editPanelMenu.add(negationMenuItem);
        editPanelMenu.add(brightnessMenuItem);
        editPanelMenu.add(filterMenuItem);
        editPanelMenu.add(gradientMenuItem);

        //Krzywa
        curvesPanelMenu.add(curvesMenuItem);

        // TODO: Dodać nowy element menu rysowania prostokąta.

        // Dodawanie wszystkich menu do paska menu
        add(fileMenu);
        add(leftPanelMenu);
        add(rightPanelMenu);
        add(editPanelMenu);
        add(curvesPanelMenu);
    }

    public JMenuItem getOpenFileMenuItem() {
        return openFileMenuItem;
    }

    public JMenuItem getSaveFileMenuItem() {
        return saveFileMenuItem;
    }

    public JMenuItem getExitMenuItem() {
        return exitMenuItem;
    }

    public JMenuItem getClearLeftPanelMenuItem() {
        return clearLeftPanelMenuItem;
    }

    public JMenuItem getCopyLeftPanelMenuItem() {
        return copyLeftPanelMenuItem;
    }

    public JMenuItem getCopyRightPanelMenuItem(){ return copyRightPanelMenuItem;}

    public JMenuItem getClearRightPanelMenuItem() {
        return clearRightPanelMenuItem;
    }

    public JMenuItem getDrawCircleMenuItem() {
        return drawCircleMenuItem;
    }

    public JMenuItem getDrawRectangleMenuItem() { return drawRectangleMenuItem;}
    public JMenuItem getDrawLineMenuItem() { return drawLineMenuItem;}
    public JMenuItem getBrightnessMenuItem() {return brightnessMenuItem;}

    public JMenuItem getGreyMenuItem() {return greyMenuItem;}
    public JMenuItem getLightMenuItem() {return lightMenuItem;}
    public JMenuItem getNegationMenuItem() {return negationMenuItem;}
    public JMenuItem getFilterMenuItem() {
        return filterMenuItem;
    }
    public JMenuItem getGradientMenuItem() {
        return gradientMenuItem;
    }

    public JMenuItem getCurvesMenuItem() { return curvesMenuItem; }



    // TODO: Dodać metody getter dla nowych elementów menu.
}
